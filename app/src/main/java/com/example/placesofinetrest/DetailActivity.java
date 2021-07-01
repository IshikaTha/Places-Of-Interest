package com.example.placesofinetrest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    TextView pLongitude, pLatitude, pAddress, pId;
    ImageView map;

    private String id, name, longitude, latitude, image, address, mAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        longitude = intent.getStringExtra("longitude");
        latitude = intent.getStringExtra("latitude");
        image = intent.getStringExtra("image");
        address = intent.getStringExtra("address");
        mAdd = "geo:0, 0?q=" + address;

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pLongitude = findViewById(R.id.pLongitude);
        pLatitude = findViewById(R.id.pLatitude);
        pAddress = findViewById(R.id.pAddress);
        map = findViewById(R.id.map);
        pId = findViewById(R.id.id);

        pLatitude.setText(latitude);
        pLongitude.setText(longitude);
        pId.setText(id);
        pAddress.setText(address);
        Glide.with(this).load(image).into(map);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
    }

    private void openMap() {
        Uri uri = Uri.parse(mAdd);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}