package com.example.ziadbekhiet.myandroidproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AlbumsMain extends AppCompatActivity {

    private ListView listview;
    private ArrayList<AlbumObject> albums;
    UserCustomAdapter adapter;
    private int index;
    public static final int EDIT_MOVIE_CODE = 1;
    public static final int ADD_MOVIE_CODE = 2;
    public static final int DELETE_MOVIE_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        String string = "movies.dat";
        setSupportActionBar(myToolbar);

        String[] albumsList = getResources().getStringArray(R.array.albums_array);
        albums = new ArrayList<AlbumObject>(albumsList.length);
        for (int i = 0; i < albumsList.length; i++) {
            String tokens = albumsList[i];
            albums.add(new AlbumObject(tokens));


        }
        try {
            FileOutputStream fos = openFileOutput(string, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            for (int i = 0; i < albums.size(); i++) {
                os.writeObject(albums.get(i));
            }
            os.close();
            fos.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


        adapter = new UserCustomAdapter(AlbumsMain.this, R.layout.row, albums);
        listview = (ListView) findViewById(R.id.albums_list);
        listview.setItemsCanFocus(false);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                //GO TO PHOTOS
                startActivity(new Intent(AlbumsMain.this, PhotosView.class));
            }
        });



    /*

        listview = findViewById(R.id.albums_list);


        listview.setAdapter(new ArrayAdapter<String>(this, R.layout.albums, albumsList));

        listview.setOnItemClickListener((parent, view, position, id) -> showAlbum(position));
        */


    }

    private void showAlbum(int pos) {
        Bundle bundle = new Bundle();
        AlbumObject album = albums.get(pos);
        bundle.putInt(Modifications.ALBUM_INDEX, pos);
        bundle.putString(Modifications.ALBUM_NAME, album.getAlbumName());

        Intent intent = new Intent(this, Modifications.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_MOVIE_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (resultCode != RESULT_OK) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }

        String name = bundle.getString(Modifications.ALBUM_NAME);
        index = bundle.getInt(Modifications.ALBUM_INDEX);

        if (requestCode == EDIT_MOVIE_CODE) {
            AlbumObject album = albums.get(index);
            album.setAlbumName(name);
        } else if (requestCode == ADD_MOVIE_CODE) {
            albums.add(new AlbumObject(name));
        } else if (requestCode == DELETE_MOVIE_CODE) {
            albums.remove(albums.get(index));
        }


        // redo the adapter to reflect change
        try {
            FileOutputStream fos = openFileOutput("movies.dat", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            for (int i = 0; i < albums.size(); i++) {
                os.writeObject(albums.get(i));
            }
            os.close();
            fos.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


        adapter = new UserCustomAdapter(AlbumsMain.this, R.layout.row, albums);
        listview = (ListView) findViewById(R.id.albums_list);
        listview.setItemsCanFocus(false);
        listview.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu, menu);
        //getMenuInflater().inflate(R.menu.) ADD DELETE AND EDIT ICONS
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addAlbum();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addAlbum() {
        Intent intent = new Intent(this, Modifications.class);
        startActivityForResult(intent, ADD_MOVIE_CODE);
    }

    public void editAlbum(View view) {
        Intent intent = new Intent(this, Modifications.class);
        startActivityForResult(intent, EDIT_MOVIE_CODE);
    }

    public void deleteAlbum(View view) {


        AlertDialog.Builder alert = new AlertDialog.Builder(AlbumsMain.this);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                albums.remove(index);

                try {
                    FileOutputStream fos = openFileOutput("movies.dat", Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    for (int i = 0; i < albums.size(); i++) {
                        os.writeObject(albums.get(i));
                    }
                    os.close();
                    fos.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


                adapter = new UserCustomAdapter(AlbumsMain.this, R.layout.row, albums);
                listview = (ListView) findViewById(R.id.albums_list);
                listview.setItemsCanFocus(false);
                listview.setAdapter(adapter);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }
}

