package com.customphotogallery.jcarlos.customphotogallery;

import android.widget.GridView;

/**
 * Created by itexicodeveloper on 8/21/14.
 */
public class DataList {
   GridView section;
   GridAdapter adapter;
   String title;

    public DataList(GridView section, GridAdapter adapter, String title)
    {
        this.section = section;
        this.adapter = adapter;
        this.title = title;
    }


    public GridView getSection(){
        return this.section;
    }

    public GridAdapter getAdapter(){
        return this.adapter;
    }

    public String getTitle(){
        return this.title;
    }
}
