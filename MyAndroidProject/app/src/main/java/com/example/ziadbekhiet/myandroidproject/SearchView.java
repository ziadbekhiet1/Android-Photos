package com.example.ziadbekhiet.myandroidproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import main.R;

/**
 * Created by ziadbekhiet on 12/13/17.
 */

public class SearchView extends AppCompatActivity {
    private ArrayList<Photo> allPhotos = new ArrayList<Photo>();
    private ArrayList<Photo> searchResults;
    final Context context = this;
    private int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchphotos);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        counter = getIntent().getIntExtra("counter", 0);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        if (counter == 0) {

            createAllPhotosList();

            gridview.setAdapter(new ImageAdapter(this, allPhotos));
        }
        else {


            gridview.setAdapter(new ImageAdapter(this, PhotoAlbum.searchResults));
        }
    }



    public void searchPhoto() {

        final String[] tagName = {""};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Input a tag name or value");

        final EditText input = new EditText(context);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().isEmpty()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Please Enter a valid Input");
                    alert.setMessage("Try again");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            return;
                        }

                    });
                    alert.show();
                } else {

                    PhotoAlbum.searchResults.clear();
                    tagName[0] = input.getText().toString();

                    if(AlbumsMain.pa.albums.size() == 0) {


                    }
                    else {

                        for(int i = 0; i < allPhotos.size(); i++) {

                            for(int j = 0; j < allPhotos.get(i).getTags().size(); j++) {

                                if(allPhotos.get(i).getTags().get(j).getTagName().equalsIgnoreCase(tagName[0])
                                        || allPhotos.get(i).getTags().get(j).getTagValue().equalsIgnoreCase(tagName[0])) {

                                    PhotoAlbum.searchResults.add(allPhotos.get(i));
                                }
                            }
                        }
                    }

                    Intent intent = new Intent(context, SearchView.class);
                    intent.putExtra("counter", 1);
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void createAllPhotosList() {

        for(int i = 0; i < AlbumsMain.pa.albums.size(); i++) {

            if(AlbumsMain.pa.albums.get(i).getPhotos().size() > 0) {

                for(int j = 0; j < AlbumsMain.pa.albums.get(i).getPhotos().size(); j++) {

                    allPhotos.add(AlbumsMain.pa.albums.get(i).getPhotos().get(j));
                }
            }
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                searchPhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
