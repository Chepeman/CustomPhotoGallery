package com.customphotogallery.jcarlos.customphotogallery;

import android.graphics.Bitmap;

/**
 * Created by itexicodeveloper on 8/21/14.
 */
public class DataGrid {
    Bitmap photoImage;
    Float photoRate;

    public DataGrid(Bitmap photoImage, Float photoRate)
    {
        this.photoImage = photoImage;
        this.photoRate = photoRate;
    }

    public Bitmap getPhotoImage(){
        return this.photoImage;
    }

    public Float getPhotoRate(){
        return this.photoRate;
    }
}
