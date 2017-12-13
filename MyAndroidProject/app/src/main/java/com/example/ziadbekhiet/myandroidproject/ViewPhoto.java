package com.example.ziadbekhiet.myandroidproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import java.lang.reflect.Array;
import java.util.ArrayList;

import main.R;

/**
 * Created by mike allen on 12/10/17.
 */

public class ViewPhoto extends AppCompatActivity{
    AlbumObject currentAlbum;
    Photo currentPhoto;
    TextView photoName;
    TextView tag;
    Context context = this;
    private int photoposition;
    private int albumposition;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(main.R.layout.view_photo);
        photoposition = getIntent().getIntExtra("photoposition", 0);
        albumposition = getIntent().getIntExtra("albumposition", 0);
        currentPhoto = AlbumsMain.pa.albums.get(albumposition).getPhotos().get(photoposition);
        imageView = (ImageView) findViewById(R.id.imageView);
        photoName = (TextView) findViewById(R.id.photoName);
        populatePhoto();
    }

    public void populatePhoto() {

        imageView.setImageBitmap(currentPhoto.getPhoto());
        photoName.setText("" + currentPhoto.getPhotoName());

    }

    public void renamePhoto(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Photo Name " + currentPhoto.getPhotoName());

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String userResponse = input.getText().toString();
                currentPhoto.setPhotoName(userResponse);
                populatePhoto();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }


    public void nextPhoto(View view) {
        Intent intent = new Intent(this, ViewPhoto.class);
        if (this.photoposition + 1 ==  AlbumsMain.pa.albums.get(albumposition).getPhotos().size()){
            photoposition = 0;
        }
        else
            photoposition++;
        intent.putExtra("photoposition", photoposition);
        intent.putExtra("albumposition", albumposition);
        startActivity(intent);


    }

    public void prevPhoto(View view) {
        Intent intent = new Intent(this, ViewPhoto.class);
        if (this.photoposition - 1 ==  -1){
            photoposition = AlbumsMain.pa.albums.get(albumposition).getPhotos().size()-1;
        }
        else
            photoposition--;
        intent.putExtra("photoposition", photoposition);
        intent.putExtra("albumposition", albumposition);
        startActivity(intent);
    }

    public void movePhoto(View view) {
        final ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < AlbumsMain.pa.albums.size(); i++) {
            if (i == albumposition) {
                continue;
            }
            items.add(AlbumsMain.pa.albums.get(i).getAlbumName());
        }

        final String[] itemsString = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemsString[i] = items.get(i);
        }
// arraylist to keep the selected items
        final ArrayList<Integer> selectedItems = new ArrayList<Integer>();

        int x = 0;

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select where you want to move the photo (you can move it to more than one album)")
                .setMultiChoiceItems(itemsString, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(indexSelected);
                        } else if (selectedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        for (int i = 0; i < selectedItems.size() - 1 ; i++) {
                            AlbumsMain.pa.albums.get(selectedItems.get(i)).addPhoto(currentPhoto);
                        }
                        AlbumsMain.pa.albums.get(albumposition).removePhoto(currentPhoto.getPhotoName());
                        Intent intent = new Intent(context, PhotosView.class);
                        startActivity(intent);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

}
