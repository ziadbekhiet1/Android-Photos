package com.example.ziadbekhiet.myandroidproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import main.R;

public class UserCustomAdapter extends ArrayAdapter<AlbumObject> {
    Context avail;
    int layoutResourceId;
    ArrayList<AlbumObject> data = new ArrayList<AlbumObject>();

    public UserCustomAdapter(Context context, int layoutResourceId,
                             ArrayList<AlbumObject> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.avail = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) avail).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.textName = (TextView) row.findViewById(R.id.textView1);
            holder.btnEdit = (Button) row.findViewById(R.id.button1);
            holder.btnDelete = (Button) row.findViewById(R.id.button2);
            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        AlbumObject user = data.get(position);
        holder.textName.setText(user.getAlbumName());
       /* UserHolder finalHolder = holder;
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("Edit Button Clicked", "**********");
                Toast.makeText(avail, "Edit button Clicked",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(avail, Modifications.class);
                ((Activity) avail).startActivityForResult(intent,1);
                System.out.println(finalHolder.textName.getText().toString());

            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("Delete Button Clicked", "**********");
                Toast.makeText(avail, "Delete button Clicked",
                        Toast.LENGTH_LONG).show();
            }
        });
        */

        return row;

    }

    static class UserHolder {
        TextView textName;
        Button btnEdit;
        Button btnDelete;
    }
}