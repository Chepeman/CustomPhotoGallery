package com.customphotogallery.jcarlos.customphotogallery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;


public class PhotoActivity extends Activity {
    private ImageView photoView;
    private TextView photoName;
    private RatingBar ratePhoto;
    private CheckBox favoriteCheck;
    private Spinner categorySpinner;
    public static PhotoDatabase db;
    private double rating;
    private static String photo_title;
    private static String section;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Bundle oExt = this.getIntent().getExtras();
        photoView = (ImageView) findViewById(R.id.photoView);
        ratePhoto = (RatingBar) findViewById(R.id.photoRate);
        favoriteCheck = (CheckBox) findViewById(R.id.favCheckBox);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        photoName = (TextView) findViewById(R.id.titleText);
        photoView.setImageBitmap((Bitmap)oExt.get("image"));
        photo_title = oExt.getString("name");
        String[] splited = photo_title.split("/");
        photoName.setText(splited[splited.length - 1]);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sections_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        ratePhoto.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = v;
                Log.d("RAT", String.valueOf(v));
            }
        });

        categorySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                section = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo, menu);
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

    public void onSaveClick(View v){
        db = new PhotoDatabase(this);
        db.insertPicture(photo_title,rating,section,favoriteCheck.isChecked());
        setResult(RESULT_OK);
        finish();
    }

    public void onCancelClick(View v){
        setResult(RESULT_CANCELED);
        finish();
    }

}
