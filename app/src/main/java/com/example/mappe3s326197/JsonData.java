package com.example.mappe3s326197;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonData implements Parcelable {

    private List<Building> buildings = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();


    public JsonData(){}

    protected JsonData(Parcel in) {
        buildings = in.createTypedArrayList(Building.CREATOR);
        rooms = in.createTypedArrayList(Room.CREATOR);
        reservations = in.createTypedArrayList(Reservation.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(buildings);
        dest.writeTypedList(rooms);
        dest.writeTypedList(reservations);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JsonData> CREATOR = new Creator<JsonData>() {
        @Override
        public JsonData createFromParcel(Parcel in) {
            return new JsonData(in);
        }

        @Override
        public JsonData[] newArray(int size) {
            return new JsonData[size];
        }
    };

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

    public List<Room> findRoomsByBuildingId(int id){
        List<Room> buildingRooms = new ArrayList<>();
        for(Room room : rooms){
            if(room.getBuilding().getId() == id){
                buildingRooms.add(room);
            }
        }
        return buildingRooms;
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
