package com.customphotogallery.jcarlos.customphotogallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class PhotoFsActivity extends Activity {
    private int ID;
    private ImageView photoViewFS;
    private String[] picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_fs);
        Bundle oExt = this.getIntent().getExtras();
        ID = oExt.getInt("ID");
        picture =  GalleryActivity.db.getPicture(ID);
        photoViewFS = (ImageView) findViewById(R.id.photoViewFS);
        Log.d("IMAGE", Integer.toString(photoViewFS.getMeasuredWidth()));
        photoViewFS.setImageBitmap(PictureTools.decodeSampledBitmapFromUri(picture[1], 360, 440));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo_fs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEdit(View v){
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("photo", picture);
        startActivity(intent);
    }

    public void onCancel(View v){
        finish();
    }
}
