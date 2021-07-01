package com.example.placesofinetrest;

public class PlacesData {
    private String id, name, image, latitude, longitude, address;

    public PlacesData(String id, String name, String image, String latitude, String longitude, String address) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
}
