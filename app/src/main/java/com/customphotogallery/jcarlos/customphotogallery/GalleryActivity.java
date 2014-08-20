package com.customphotogallery.jcarlos.customphotogallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GalleryActivity extends Activity {
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int EDIT_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    private static final int MEDIA_TYPE_VIDEO = 2;
    public PhotoDatabase db;
    private LinearLayout scrollGallery;
    private Uri fileUri;
    private ArrayList<View> sections;

    public GalleryActivity(){
        db = new PhotoDatabase(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        scrollGallery = (LinearLayout) findViewById(R.id.linearGallery);
        sections = new ArrayList<View>();
        addSections();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_camera:
                openCamera();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void loadSection(String section, int sec){
        String[][] pictures = db.getSectionPictures(section);
        if(pictures != null){
            View v = sections.get(sec);
            GridLayout grid = (GridLayout)v.findViewById(R.id.gridLayout);

            for(int i=0; i < pictures.length; i++){
                LayoutInflater inflater = LayoutInflater.from(this);
                View photo = inflater.inflate(R.layout.photo_section, null);
                ImageView image = (ImageView) photo.findViewById(R.id.imgPhoto);
                RatingBar ratingb = (RatingBar) photo.findViewById(R.id.ratPhoto);
                Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(pictures[i][1], 188, 150);
                image.setImageBitmap(bit_map);
                ratingb.setRating(Float.parseFloat(pictures[i][2]));
                grid.addView(photo);
            }
        }
        else{
            Log.d("PIC" , "No pos null");
        }
    }
    void addSections() {
        String[] message = getResources().getStringArray(R.array.sections_array);
        int len = message.length;
        for(int i = 0; i < len; i++){
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.section_layout, null);
            TextView sectionMessage = (TextView) v.findViewById(R.id.sectionTitle);
            sectionMessage.setText(message[i]);
            sections.add(v);
            scrollGallery.addView(v);

        }
        loadSection(message[1], 1);

    }

    void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d("TAG", fileUri.toString());
                Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(), 200, 200);
                Intent intent = new Intent(this,PhotoActivity.class);
                intent.putExtra("name", fileUri.getPath().toString());
                intent.putExtra("image", bit_map);
                startActivityForResult(intent,EDIT_IMAGE_ACTIVITY_REQUEST_CODE);
                //addImageToTheGrid(bit_map);

            } else if (resultCode == RESULT_CANCELED) {
                //El usuario cancela la imagen a capturar
            } else {
                //La captura de la imagen fallo.
            }
        }
        else if(requestCode == EDIT_IMAGE_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                Log.d("FINISHED", "After the Insert!");

            } else if (resultCode == RESULT_CANCELED) {
                Log.d("FINISHED", "Canceled the Insert!");
            } else {
                //La captura de la imagen fallo.
            }
        }
    }

    /*private void addImageToTheGrid(Bitmap bit_map) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.section_layout, null);
        TextView sectionMessage = (TextView) v.findViewById(R.id.sectionTitle);
        sectionMessage.setText(message);
        scrollGallery.addView(v);
    }*/

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CustomPhotoGallery");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

}


