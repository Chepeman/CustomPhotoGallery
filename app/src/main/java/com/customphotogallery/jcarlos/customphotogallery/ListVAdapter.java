package com.customphotogallery.jcarlos.customphotogallery;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

            holder.gridSection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   Intent intent = new Intent(context, PhotoFsActivity.class);
                    DataGrid gridItem = (DataGrid) adapterView.getItemAtPosition(i);
                    intent.putExtra("ID", gridItem.getID());
                    context.startActivity(intent);
                }
            });
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
        int densityMultiplier = getDeviceResolution();
        layoutParams.height = densityMultiplier * multiplier;
        holder.gridSection.setLayoutParams(layoutParams);
        holder.sectionText.setText(listData.get(i).getTitle());
        holder.gridSection.setAdapter(listData.get(i).getAdapter());
        return view;
    }

    static class ViewHolder{
        GridView gridSection;
        TextView sectionText;
    }

    private int getDeviceResolution() {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return 410;
            case DisplayMetrics.DENSITY_HIGH:
                return 410;
            case DisplayMetrics.DENSITY_LOW:
                return 410;
            case DisplayMetrics.DENSITY_XHIGH:
                return 410;
            case DisplayMetrics.DENSITY_TV:
                return 410;
            case DisplayMetrics.DENSITY_XXHIGH:
                return 610;
            case DisplayMetrics.DENSITY_XXXHIGH:
                return 810;
            default:
                return 400;
        }
    }
}
