package com.customphotogallery.jcarlos.customphotogallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;


public class GalleryActivity extends Activity {

    private ScrollView scrollGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        scrollGallery = (ScrollView) findViewById(R.id.scrollGallery);
        addSection("First Section");
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void addSection(String message){
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.section_layout, null);
        TextView sectionMessage = (TextView) v.findViewById(R.id.sectionTitle);
        sectionMessage.setText(message);
        scrollGallery.addView(v);
    }
}
