package com.example.placesofinetrest;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<PlacesData> list;
    private MainAdapter mainAdapter;
    ProgressDialog progressDialog;
    private String id, name, longitude, latitude, address, image;
    private boolean doubleBackToExitPressedOnce = false;
    private Toast backPressToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Places");

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        mainAdapter = new MainAdapter(MainActivity.this, list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mainAdapter);

        if(!isConnected(MainActivity.this)){
            buildDialog(MainActivity.this).show();
        }
        else
        fetchData();
    }

    private void fetchData() {
        ShowDialog(this);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://s3-ap-southeast-1.amazonaws.com/he-public-data/placesofinterest39c1c48.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            list.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                name = jsonObject.getString("name");
                                id = jsonObject.getString("id");
                                longitude = jsonObject.getString("longitude");
                                latitude = jsonObject.getString("latitude");
                                address = jsonObject.getString("address");
                                image = jsonObject.getString("image");

                                PlacesData placesData = new PlacesData(id, name, image, latitude, longitude, address);
                                list.add(placesData);
                            }
                            Handler makeDelay = new Handler();
                            makeDelay.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mainAdapter.notifyDataSetChanged();
                                    progressDialog.dismiss();
                                }
                            }, 1000);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void ShowDialog(Context context) {
        //setting up progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public boolean isConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null && info.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return mobile != null && mobile.isConnectedOrConnecting() || wifi != null && wifi.isConnectedOrConnecting();
        }else
            return false;
    }


    public AlertDialog.Builder buildDialog(Context context){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You are not connected to internet");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> finish());
        builder.setNegativeButton("Cancel", (dialog, which) -> builder.setCancelable(true));
        return builder;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            backPressToast.cancel();
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        backPressToast = Toast.makeText(this, "Please press back again to exit", Toast.LENGTH_SHORT);
        backPressToast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}