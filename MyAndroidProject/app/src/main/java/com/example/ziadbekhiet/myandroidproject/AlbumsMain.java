package com.example.ziadbekhiet.myandroidproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import main.R;


public class AlbumsMain extends AppCompatActivity {

    private ListView listview;
    public static PhotoAlbum pa;
    final Context context = this;
    ArrayAdapter<AlbumObject> adapter;
    String userResponse;
    public static final int ADD_MOVIE_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        pa = PhotoAlbum.loadFromDisk(this);
        if (pa == null) {
            pa = new PhotoAlbum();
            pa.albums = new ArrayList<AlbumObject>();

            String[] albumsList = getResources().getStringArray(R.array.albums_array);
            for (int i = 0; i < albumsList.length; i++) {
                String tokens = albumsList[i];
                pa.albums.add(new AlbumObject(tokens));
            }
            populateImages();
            pa.saveToDisk(context);
        }


        listview = (ListView) findViewById(R.id.photo_list);
        adapter = new ArrayAdapter<AlbumObject>(context, R.layout.albums, pa.albums);
        listview.setAdapter(adapter);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            FloatingActionButton edit = (FloatingActionButton) findViewById(R.id.edit);
            FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.delete);
            FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search);

            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int position2 = position;
                listview.requestFocusFromTouch();
                listview.setSelection(position);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                search.setVisibility(View.INVISIBLE);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Edit album name" + adapter.getItem(position2).getAlbumName());

                        final EditText input = new EditText(context);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (findAlbumName(input.getText().toString().toLowerCase()) != -1) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                    alert.setTitle("Duplicate");
                                    alert.setMessage("Album name already exists. Try again");
                                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            return;
                                        }
                                    });
                                    alert.show();
                                } else {
                                    userResponse = input.getText().toString();
                                    pa.albums.get(position2).setAlbumName(userResponse);
                                    listview.setAdapter(adapter);
                                    edit.setVisibility(View.INVISIBLE);
                                    delete.setVisibility(View.INVISIBLE);
                                    search.setVisibility(View.VISIBLE);
                                    pa.saveToDisk(context);
                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                edit.setVisibility(View.INVISIBLE);
                                delete.setVisibility(View.INVISIBLE);
                                search.setVisibility(View.VISIBLE);
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Are you sure you want to delete album " + adapter.getItem(position2).getAlbumName() + "?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                pa.albums.remove(position2);
                                pa.saveToDisk(context);
                                listview.setAdapter(adapter);
                                edit.setVisibility(View.INVISIBLE);
                                delete.setVisibility(View.INVISIBLE);
                                search.setVisibility(View.VISIBLE);

                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                edit.setVisibility(View.INVISIBLE);
                                delete.setVisibility(View.INVISIBLE);
                                search.setVisibility(View.VISIBLE);
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                });
                return true;
            }
        });


        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                Intent intent = new Intent(context, SearchView.class);
                intent.putExtra("counter", 0);
                startActivity(intent);

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                //GO TO PHOTOS
                Intent intent = new Intent(AlbumsMain.this, PhotosView.class);

                intent.putExtra("albumposition", position);
                startActivity(intent);

            }
        });

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

      if (requestCode == ADD_MOVIE_CODE) {
          pa.albums.add(new AlbumObject(name));
      }
        listview = (ListView)findViewById(R.id.photo_list);
        adapter = new ArrayAdapter<AlbumObject>(context, R.layout.albums, pa.albums);
        listview.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu, menu);
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


    public int findAlbumName(String name){

        for(int i = 0; i < pa.albums.size(); i++) {
            if(pa.albums.get(i).getAlbumName().equalsIgnoreCase(name)){
                return i;
            }
        }
        return -1;
    }

    public void populateImages() {
        Photo photo = new Photo();
        photo.setPhotoName("beach");
        photo.setPhoto((BitmapFactory.decodeResource(getResources(),R.drawable.beach)));
        photo.addTag("Beach", "beautiful");
        photo.addTag("Cool", "fun");

        this.pa.albums.get(0).addPhoto(photo);
        Photo photo1 = new Photo();
        photo1.setPhotoName("dock");
        photo1.setPhoto((BitmapFactory.decodeResource(getResources(),R.drawable.dock)));
        photo1.addTag("Fishing", "fish");
        photo1.addTag("Cool", "fun");
        this.pa.albums.get(0).addPhoto(photo1);
    }



}

