package com.example.placesofinetrest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> {

    Context context;
    ArrayList<PlacesData> list;

    public MainAdapter(Context context, ArrayList<PlacesData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_grid_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PlacesData placesData = list.get(position);
        String placeName = placesData.getName();;
        String placeAddress = placesData.getAddress();
        String placeImage = placesData.getImage();

        holder.name.setText(placeName);
        holder.address.setText(placeAddress);
        Glide.with(context).load(placeImage).into(holder.image);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", placesData.getId());
                intent.putExtra("name", placesData.getName());
                intent.putExtra("longitude", placesData.getLongitude());
                intent.putExtra("latitude", placesData.getLatitude());
                intent.putExtra("address", placesData.getAddress());
                intent.putExtra("image", placesData.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView address, name;
        private ImageView image;
        private MaterialCardView card;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            card = itemView.findViewById(R.id.card);
        }
    }
}
