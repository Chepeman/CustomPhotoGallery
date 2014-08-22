package com.customphotogallery.jcarlos.customphotogallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GalleryActivity extends Activity {
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int EDIT_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    private static final int MEDIA_TYPE_VIDEO = 2;

    private LinearLayout scrollGallery;
    private Uri fileUri;
    private ArrayList<View> sections;
    private ListView galleryListView;
    public PhotoDatabase db;
    private ArrayList<DataList> listDataV;

    public GalleryActivity(){
        db = new PhotoDatabase(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        galleryListView = (ListView) findViewById(R.id.listView2);
        loadSections();


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
            case R.id.action_all:
               loadSections();
                return true;
            case R.id.action_filter_static:
                filterSection("Static");
                return true;
            case R.id.action_filter_camera:
                filterSection("Camera");
                return true;
            case R.id.action_filter_gallery:
                filterSection("Gallery");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    GridAdapter loadSection(String section){
        String[][] pictures = db.getSectionPictures(section);
        ArrayList<DataGrid> listData;
        listData = new ArrayList<DataGrid>();
        GridAdapter adapter = null;
        if(pictures != null){
            adapter = new GridAdapter(this, listData);
            int len = pictures.length;
            for(int i = 0; i< len; i++){
                DataGrid data = new DataGrid(
                        PictureTools.decodeSampledBitmapFromUri(pictures[i][1], 188, 150),
                        Float.parseFloat(pictures[i][2]));
                listData.add(data);
            }

        }
        else{
            Log.d("PIC", "No pos null");
        }

        return adapter;
    }

    void loadSections(){
        String[] message = getResources().getStringArray(R.array.sections_array);
        listDataV = new ArrayList<DataList>();
        for(int i = 0; i < message.length; i++) {
            GridAdapter adapter = loadSection(message[i]);
            if (adapter != null && adapter.getCount() > 0) {
                GridView gridV = new GridView(this);
                DataList listV = new DataList(gridV, adapter, message[i]);
                listDataV.add(listV);
                galleryListView.setAdapter(new ListVAdapter(this, listDataV));
            }
        }
    }

    void filterSection(String section){
        listDataV = new ArrayList<DataList>();
        GridAdapter adapter = loadSection(section);
        if (adapter != null) {
            GridView gridV = new GridView(this);
            DataList listV = new DataList(gridV, adapter, section);
            listDataV.add(listV);
            galleryListView.setAdapter(new ListVAdapter(this, listDataV));
        }
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
                loadSections();

            } else if (resultCode == RESULT_CANCELED) {
                Log.d("FINISHED", "Canceled the Insert!");
            } else {
                //La captura de la imagen fallo.
            }
        }
    }



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


