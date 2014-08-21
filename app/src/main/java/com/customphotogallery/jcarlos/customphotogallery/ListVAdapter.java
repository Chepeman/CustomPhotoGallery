package com.customphotogallery.jcarlos.customphotogallery;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by itexicodeveloper on 8/21/14.
 */
public class ListVAdapter extends BaseAdapter {
    private ArrayList<DataList> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListVAdapter(Context context, ArrayList<DataList> listData){
        this.listData = listData;
        this.context = context;
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
            view = layoutInflater.inflate(R.layout.gridv_layout, null);
            holder = new ViewHolder();
            holder.gridSection = (GridView) view.findViewById(R.id.sectionGrid);
            holder.sectionText = (TextView) view.findViewById(R.id.sectionText);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }



        int multiplier;
        int len = listData.get(i).getAdapter().getCount();
        if(len%2 == 0)
            multiplier = (len)/2;
        else
            multiplier = (len + 1)/2;
        ViewGroup.LayoutParams layoutParams =  holder.gridSection.getLayoutParams();
        layoutParams.height = 400 * multiplier;
        holder.gridSection.setLayoutParams(layoutParams);
        holder.sectionText.setText(listData.get(i).getTitle());
        holder.gridSection.setAdapter(listData.get(i).getAdapter());
        return view;
    }

    static class ViewHolder{
        GridView gridSection;
        TextView sectionText;
    }
}
