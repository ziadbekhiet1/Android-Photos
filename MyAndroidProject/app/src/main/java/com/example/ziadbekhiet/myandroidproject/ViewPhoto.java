package com.example.ziadbekhiet.myandroidproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by mike allen on 12/10/17.
 */

public class ViewPhoto extends AppCompatActivity{

    Photo currentPhoto;
    TextView photoName;
    TextView tag;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_photo);

        //currentPhoto = getIntent().getParcelableExtra("currentAlbum");
        //currentPhoto = new Photo();

        //currentPhoto.setPhotoName("test photo.jpg");
        //populatePhoto();
    }

    public void populatePhoto() {

        //imageView.setImageResource(R.drawable.sample_0);
        //photoName.setText("" + currentPhoto.getPhotoName());

    }

    public void renamePhoto() {

    }

    public void nextPhoto() {

    }

    public void prevPhoto() {

    }

}
