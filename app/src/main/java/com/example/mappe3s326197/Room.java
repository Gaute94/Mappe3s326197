package com.example.mappe3s326197;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Room implements Parcelable {
    private int id;
    private String name;
    private String desc;
    private Building building;

    public Room(){}

    protected Room(Parcel in) {
        id = in.readInt();
        name = in.readString();
        desc = in.readString();
        building = in.readParcelable(Building.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeParcelable(building, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public Building getBuilding(){
        return building;
    }

    public void setBuilding(Building building){
        this.building = building;
    }



    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
