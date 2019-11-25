package com.example.mappe3s326197;

import android.os.Parcel;
import android.os.Parcelable;

public class Building implements Parcelable {
    private int id;
    private String address;
    private float geoLat;
    private float geoLng;

    public Building(){}

    protected Building(Parcel in) {
        id = in.readInt();
        address = in.readString();
        geoLat = in.readFloat();
        geoLng = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(address);
        dest.writeFloat(geoLat);
        dest.writeFloat(geoLng);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Building> CREATOR = new Creator<Building>() {
        @Override
        public Building createFromParcel(Parcel in) {
            return new Building(in);
        }

        @Override
        public Building[] newArray(int size) {
            return new Building[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(float geoLat) {
        this.geoLat = geoLat;
    }

    public float getGeoLng() {
        return geoLng;
    }

    public void setGeoLng(float geoLng) {
        this.geoLng = geoLng;
    }
}
