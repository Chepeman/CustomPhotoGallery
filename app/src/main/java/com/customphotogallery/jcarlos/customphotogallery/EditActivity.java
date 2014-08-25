package com.customphotogallery.jcarlos.customphotogallery;

import android.app.Activity;
import android.content.Intent;
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


public class EditActivity extends Activity {
    String[] picture;
    private ImageView photoView;
    private TextView photoName;
    private RatingBar ratePhoto;
    private CheckBox favoriteCheck;
    private Spinner categorySpinner;
    private String section;
    private float rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle oExt = this.getIntent().getExtras();
        picture = oExt.getStringArray("photo");
        photoView = (ImageView) findViewById(R.id.photoView);
        ratePhoto = (RatingBar) findViewById(R.id.photoRate);
        favoriteCheck = (CheckBox) findViewById(R.id.favCheckBox);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        photoName = (TextView) findViewById(R.id.titleText);
        rating = Float.parseFloat(picture[2]);
        ratePhoto.setRating(rating);
        if(Integer.parseInt(picture[3]) == 1)
            favoriteCheck.setChecked(true);
        else
            favoriteCheck.setChecked(false);
        ratePhoto.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = v;
            }
        });
        photoView.setImageBitmap(PictureTools.decodeSampledBitmapFromUri(picture[1], 270, 270));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        int positionSpinner = adapter.getPosition(picture[4]);
        categorySpinner.setSelection(positionSpinner);
        String[] splited = picture[1].split("/");
        photoName.setText(splited[splited.length - 1]);
        ratePhoto.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = v;
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
        getMenuInflater().inflate(R.menu.edit, menu);
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
        GalleryActivity.db.updatePhoto(Integer.parseInt(picture[0]), picture[1], rating, section, favoriteCheck.isChecked());
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    public void onCancelClick(View v){
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }
}
