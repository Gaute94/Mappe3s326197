package com.example.mappe3s326197.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Reservation implements Parcelable {

    private int id;
    private Date start;
    private Date finished;
    private Room room;

    public Reservation(){}

    protected Reservation(Parcel in) {
        id = in.readInt();
        room = in.readParcelable(Room.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(room, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}