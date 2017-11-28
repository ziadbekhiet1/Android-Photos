package com.example.ziadbekhiet.andriodphotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MainActivity extends Activity {

    private ListView listview;
    private String[] albumNames;
    private String[] albumDetails;
    public static final String ALBUM_NAME_KEY = "album_name";
    public static final String ALBUM_DETAIL_KEY = "album_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ziadbekhiet.andriodphotos.R.layout.albums_list);

        listview = (ListView) findViewById(com.example.ziadbekhiet.andriodphotos.R.id.albums_list);
        albumNames = getResources().getStringArray(com.example.ziadbekhiet.andriodphotos.R.array.albums_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, com.example.ziadbekhiet.andriodphotos.R.layout.albums, albumNames);
        listview.setAdapter(adapter);

        InputStream is = getResources().openRawResource(com.example.ziadbekhiet.andriodphotos.R.raw.albums);
        Scanner sc = new Scanner(new InputStreamReader(is));
        albumDetails = new String[albumNames.length];


        for (int i = 0; i < albumNames.length; i++) {
            StringBuilder sb = new StringBuilder();
            String line = sc.nextLine();
            while (!line.startsWith("*")) {
                sb.append(line+"\n");
                line = sc.nextLine();
            }
            albumDetails[i] = sb.toString();
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showRoute(position);  }
        });
    }

    private void showRoute(int pos) {
        Bundle bundle = new Bundle();
        bundle.putString(ALBUM_NAME_KEY,albumNames[pos]);
        bundle.putString(ALBUM_DETAIL_KEY,albumDetails[pos]);
        Intent intent = new Intent(this, ShowPhotos.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
