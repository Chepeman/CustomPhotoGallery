package com.customphotogallery.jcarlos.customphotogallery;

import android.graphics.Bitmap;

/**
 * Created by itexicodeveloper on 8/21/14.
 */
public class DataGrid {
    int ID;
    Bitmap photoImage;
    Float photoRate;

    public DataGrid(int ID, Bitmap photoImage, Float photoRate)
    {
        this.photoImage = photoImage;
        this.photoRate = photoRate;
        this.ID = ID;
    }

    public int getID(){
        return this.ID;
    }

    public Bitmap getPhotoImage(){
        return this.photoImage;
    }

    public Float getPhotoRate(){
        return this.photoRate;
    }
}
