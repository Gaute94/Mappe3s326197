package com.example.mappe3s326197;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonData {

    private List<Building> buildings = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();


    public void printAllData(){
        for(Building building : buildings){
            Log.d(TAG, "printAllData: inside building");
            Log.d(TAG, "printAllData: Building address: " + building.getAddress());
        }

        for(Room room : rooms){
            Log.d(TAG, "printAllData: inside room");
            Log.d(TAG, "printAllData: Room name: " + room.getName());
        }

        for(Reservation reservation : reservations){
            Log.d(TAG, "printAllData: inside reservation");
            Log.d(TAG, "printAllData: Reservation Id");
        }
    }

    public Building findBuildingByCoordinates(LatLng latLng){
        for(Building building : buildings){
            if(building.getGeoLat() == latLng.latitude && building.getGeoLng() == latLng.longitude){
                return building;
            }
        }
        return null;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
