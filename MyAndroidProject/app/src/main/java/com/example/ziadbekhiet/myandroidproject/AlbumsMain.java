package com.example.ziadbekhiet.myandroidproject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.SyncStateContract;
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
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import main.R;


public class AlbumsMain extends AppCompatActivity {

    private ListView listview;
    public static PhotoAlbum pa;
    final Context context = this;
    ArrayAdapter<AlbumObject> adapter;
    private int index;
    String userResponse;
    public static final int EDIT_MOVIE_CODE = 1;
    public static final int ADD_MOVIE_CODE = 2;
    public static final int DELETE_MOVIE_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        /*super.onCreate(savedInstanceState);
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

        adapter = new ArrayAdapter<AlbumObject>(this, R.layout.albums, albums);
        listview = (ListView) findViewById(R.id.albums_list);
        listview.setItemsCanFocus(false);
        listview.setAdapter(adapter);
        */

        pa = PhotoAlbum.loadFromDisk(this);
        if(pa == null) {
            pa = new PhotoAlbum();
        }

        if (pa.albums == null) {
            pa.albums = new ArrayList<AlbumObject>();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        String string = "movies.dat";
        setSupportActionBar(myToolbar);
        String[] albumsList = getResources().getStringArray(R.array.albums_array);
        for (int i = 0; i < albumsList.length; i++) {
            String tokens = albumsList[i];
            pa.albums.add(new AlbumObject(tokens));
        }

        populateImages();


        listview = (ListView)findViewById(R.id.photo_list);
        //listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //listview.setSelection(0);
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
                        builder.setTitle("Edit album name "+adapter.getItem(position2).getAlbumName());

                        final EditText input = new EditText(context);
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(input);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(findAlbumName(input.getText().toString().toLowerCase()) != -1){
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
                                }
                                else {
                                    userResponse = input.getText().toString();
                                    pa.albums.get(position2).setAlbumName(userResponse);
                                    pa.saveToDisk(context);
                                    listview.setAdapter(adapter);
                                    edit.setVisibility(View.INVISIBLE);
                                    delete.setVisibility(View.INVISIBLE);
                                    search.setVisibility(View.VISIBLE);
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
                delete.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Are you sure you want to delete album "+adapter.getItem(position2).getAlbumName()+"?");
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
        /*
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ThumbnailViewActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }

        });

        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.searchphotos_dialog);
                dialog.setTitle("Find Photos by Tag");

                final AutoCompleteTextView textView = (AutoCompleteTextView) dialog.findViewById(R.id.tagValue);
                Button searchTag = (Button) dialog.findViewById(R.id.dialogSearch);
                Button cancel = (Button) dialog.findViewById(R.id.dialogCancel);
                Spinner tagType = (Spinner) dialog.findViewById(R.id.dialog_spinner);
                //Grab all possible location tags
                final ArrayList<String> allLocationTags = new ArrayList<String>();
                final ArrayList<String> allPersonTags = new ArrayList<String>();
                for(int i = 0; i < pa.albums.size(); i++){
                    for(int x = 0; x < pa.albums.get(i).getPhotos().size(); x++){
                        if(pa.albums.get(i).getPhotos().get(x).locationTags() == null) continue;
                        allLocationTags.addAll(pa.albums.get(i).getPhotos().get(x).locationTags());
                    }
                }
                for(int i = 0; i < pa.albums.size(); i++){
                    for(int x = 0; x < pa.albums.get(i).getPhotos().size(); x++){
                        if(pa.albums.get(i).getPhotos().get(x).personTags() == null) continue;
                        allPersonTags.addAll(pa.albums.get(i).getPhotos().get(x).personTags());
                    }
                }
                if(tagType.getSelectedItem().toString().trim().equals("location")) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, allLocationTags);
                    textView.setAdapter(adapter);
                }
                else {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, allPersonTags);
                    textView.setAdapter(adapter);
                }
                searchTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoAlbum.searchResults.clear();
                        EditText tagValue = (EditText) dialog.findViewById(R.id.tagValue);
                        Spinner tagType = (Spinner) dialog.findViewById(R.id.dialog_spinner);
                        if(tagValue.getText().toString().trim().isEmpty()){
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("Invalid");
                            alert.setMessage("Must input at least one character");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    return;
                                }
                            });
                            alert.show();
                        }
                        else {
                            String type = tagType.getSelectedItem().toString();
                            String value = tagValue.getText().toString().toLowerCase();


                            if(type.equals("location")){

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, allLocationTags);
                                textView.setAdapter(adapter);
                                for(int i = 0; i < pa.albums.size(); i++){
                                    for(int x = 0; x < pa.albums.get(i).getPhotos().size(); x++){
                                        ArrayList<String> location = pa.albums.get(i).getPhotos().get(x).locationTags();
                                        if(location == null) break;
                                        if(location.isEmpty()) break;
                                        if(location.contains(value)){
                                            PhotoAlbum.searchResults.addOnePhoto(pa.albums.get(i).getPhotos().get(x));
                                        }
                                        else {
                                            for(int r = 0; r < location.size(); r++){
                                                if(location.get(r).startsWith(value)){
                                                    PhotoAlbum.searchResults.addOnePhoto(pa.albums.get(i).getPhotos().get(x));
                                                    break;
                                                }
                                            }

                                        }
                                        //create new method for tags with location key under photo
                                    }
                                }
                            }
                            else {
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, allPersonTags);
                                textView.setAdapter(adapter);
                                for(int i = 0; i < pa.albums.size(); i++){
                                    for(int x = 0; x < pa.albums.get(i).getPhotos().size(); x++){
                                        ArrayList<String> person = pa.albums.get(i).getPhotos().get(x).personTags();
                                        if(person == null) break;
                                        if(person.isEmpty()) break;
                                        String[] PERSON  = new String[person.size()];
                                        PERSON  = person.toArray(PERSON);
                                        if(person.contains(value)){
                                            PhotoAlbum.searchResults.addPhoto(pa.albums.get(i).getPhotos().get(x));
                                        }
                                        else {
                                            for(int r = 0; r < person.size(); r++){
                                                if(person.get(r).startsWith(value)){
                                                    PhotoAlbum.searchResults.addPhoto(pa.albums.get(i).getPhotos().get(x));
                                                    break;
                                                }
                                            }

                                        }
                                        //create new method for tags with person key under photo
                                    }
                                }
                            }

                        }

                        if(PhotoAlbum.searchResults.getNumOfPhotos() == 0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("Search empty");
                            alert.setMessage("No matches found");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    return;
                                }
                            });
                            alert.show();
                        }
                        else {
                            Intent intent = new Intent(MainActivity.this, ResultsViewActivity.class);
                            intent.putExtra("index", 0);
                            startActivity(intent);
                        }


                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Enter album name.");
                FloatingActionButton edit = (FloatingActionButton) findViewById(R.id.edit);
                FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.delete);
                edit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);

                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString().trim().isEmpty()){
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("Invalid");
                            alert.setMessage("Must input at least one character");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    return;
                                }
                            });
                            alert.show();

                        }
                        else if(findAlbumName(input.getText().toString().toLowerCase()) != -1){
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
                        }
                        else {
                            userResponse = input.getText().toString();
                            Album albumToAdd = new Album(userResponse);
                            pa.albums.add(albumToAdd);
                            pa.saveToDisk(context);
                            arrayAdapter = new ArrayAdapter<Album>(context, R.layout.album, pa.albums);
                            listview.setAdapter(arrayAdapter);
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
        });
    }
    */
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






    /*

        listview = findViewById(R.id.albums_list);


        listview.setAdapter(new ArrayAdapter<String>(this, R.layout.albums, albumsList));

        listview.setOnItemClickListener((parent, view, position, id) -> showAlbum(position));
        */


    }

    private void showAlbum(int pos) {
        Bundle bundle = new Bundle();



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

      if (requestCode == ADD_MOVIE_CODE) {
          pa.albums.add(new AlbumObject(name));
      }


        // redo the adapter to reflect change



        listview = (ListView)findViewById(R.id.photo_list);
        //listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //listview.setSelection(0);
        adapter = new ArrayAdapter<AlbumObject>(context, R.layout.albums, pa.albums);
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
        this.pa.albums.get(0).addPhoto(photo);
        Photo photo1 = new Photo();
        photo1.setPhotoName("dock");
        photo1.setPhoto((BitmapFactory.decodeResource(getResources(),R.drawable.dock)));
        this.pa.albums.get(0).addPhoto(photo1);
    }

    public static AlbumObject getAlbumByName(String string) {
        for (int i = 0; i < pa.albums.size() - 1; i++) {
            if (string == pa.albums.get(i).getAlbumName()) {
                return pa.albums.get(i);
            }

        }

        return null;
    }
}

