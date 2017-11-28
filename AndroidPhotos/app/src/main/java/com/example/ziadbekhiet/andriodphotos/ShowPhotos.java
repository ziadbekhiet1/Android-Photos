package com.example.ziadbekhiet.andriodphotos;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowPhotos extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_details);

        // get the name and detail from bundle
        Bundle bundle = getIntent().getExtras();
        String routeName = bundle.getString(MainActivity.ALBUM_NAME_KEY);
        String routeDetail = bundle.getString(MainActivity.ALBUM_DETAIL_KEY);

        // get the name and detail view objects
        TextView routeNameView = findViewById(R.id.album_name);
        TextView routeDetailView = findViewById(R.id.albums_details);


        // set name and detail on the views
        routeNameView.setText(routeName);
        routeDetailView.setText(routeDetail);

    }
}