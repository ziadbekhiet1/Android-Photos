package com.example.ziadbekhiet.myandroidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by mike allen on 12/10/17.
 */

public class PhotosView extends AppCompatActivity{

    AlbumObject currentAlbum;
    int currentPhoto = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);

        currentAlbum = getIntent().getParcelableExtra("currentAlbum");

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(PhotosView.this, "" + position,
                        Toast.LENGTH_SHORT).show();

                //GO TO VIEW PHOTO
                Intent intent = new Intent(PhotosView.this, ViewPhoto.class);
                intent.putExtra("photo", currentAlbum.getPhotos().get(currentPhoto));
                startActivity(intent);
            }
        });
    }

}
