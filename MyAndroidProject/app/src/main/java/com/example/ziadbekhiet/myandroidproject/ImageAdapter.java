package com.example.ziadbekhiet.myandroidproject;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by allen on 12/9/2017.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList data;

    public ImageAdapter(Context c, ArrayList data) {

        mContext = c;
        this.data = data;
    }



    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Photo photo = (Photo) data.get(position);
        imageView.setImageBitmap(photo.getPhoto());

        return imageView;
    }
    public long getItemId(int position) {
        return 0;
    }
    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return null;
    }
}