package com.example.ziadbekhiet.myandroidproject;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

/**
 * Created by ziadbekhiet on 11/29/17.
 */

public class Modifications extends AppCompatActivity {

    public static final String ALBUM_INDEX = "albumIndex";
    public static final String ALBUM_NAME = "albumName";

    private int albumIndex;

    private EditText albumName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_delete);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);  setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();  ab.setDisplayHomeAsUpEnabled(true);
        // get the fields
        albumName = (EditText) findViewById(R.id.album_name);
        // see if info was passed in to populate fields
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            albumIndex = bundle.getInt(ALBUM_INDEX);
            albumName.setText(bundle.getString(ALBUM_NAME));
        }
    }

    public void save(View view) {
        // gather all data
        String name = albumName.getText().toString();

        // name and year are mandatory
        if (name == null || name.length() == 0) {

            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialogFragment.MESSAGE_KEY,
                    "Name is required");

            DialogFragment newFragment = new AlbumDialogFragment();
            newFragment.setArguments(bundle);
            newFragment.show(getFragmentManager(), "missingFields");

            return;   // does not quit activity, just returns from method
        }

        // make Bundle
        Bundle bundle = new Bundle();
        bundle.putInt(ALBUM_INDEX, albumIndex);
        bundle.putString(ALBUM_NAME, name);

        // send back to caller
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish(); // pops the activity from the call stack, returns to parent

    }
    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

}
