package com.customphotogallery.jcarlos.customphotogallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.ArrayList;

/**
 * Created by itexicodeveloper on 8/21/14.
 */
public class GridAdapter extends BaseAdapter {
    private ArrayList<DataGrid> listData;
    private LayoutInflater layoutInflater;

    public GridAdapter(Context context, ArrayList<DataGrid> listData){
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.photo_layout, null);
            holder = new ViewHolder();
            holder.photoImage = (ImageView) view.findViewById(R.id.photoImage);
            holder.photoRate = (RatingBar) view.findViewById(R.id.photoRate);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        holder.photoImage.setImageBitmap(listData.get(i).getPhotoImage());
        holder.photoRate.setRating(listData.get(i).getPhotoRate());
        return view;
    }

    static class ViewHolder{
        ImageView photoImage;
        RatingBar photoRate;
    }
}
