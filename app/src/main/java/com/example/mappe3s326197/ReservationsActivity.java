package com.example.mappe3s326197;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mappe3s326197.adapter.ReservationAdapter;
import com.example.mappe3s326197.models.Building;
import com.example.mappe3s326197.models.JsonData;
import com.example.mappe3s326197.models.Reservation;
import com.example.mappe3s326197.models.Room;

import java.util.Date;
import java.util.List;

public class ReservationsActivity extends AppCompatActivity {

    private JsonData jsonData;
    TextView roomName;
    TextView reservationDate;
    private ListView reservationListView;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
        reservationListView = (ListView) findViewById(R.id.reservation_list_view);
        Intent i = getIntent();
        jsonData = (JsonData) i.getParcelableExtra("JsonData");
        //int buildingId = i.getIntExtra("buildingId", 0);
        int roomId = i.getIntExtra("roomId", 0);
        room = jsonData.findRoomById(roomId);
//        List<Room> roomsInBuilding = jsonData.findRoomsByBuildingId(buildingId);
//
//
//
//        room = roomsInBuilding.get(roomId);
//        if(room != null){
//            Log.d("ReservationsActivity", "room is not null!");
//        }else{
//            Log.d("ReservationsActivity", "room IS NULL!!!");
//        }
        roomName = (TextView)findViewById(R.id.roomName);
        reservationDate = (TextView)findViewById(R.id.reservation_date);

        List<Reservation> reservations = jsonData.findReservationsByRoomId(roomId);

        roomName.setText(room.getName());

        reservationDate.setText("DID IT");

        Log.d("ReservationsActivity", "Room ID: " + roomId);
        Log.d("ReservationsActivity", "Room Name: " + room.getName());
        if(reservations.size() == 0){
            Log.d("ReservationsActivity", "Reservations == 0");
        }
        for(Reservation reservation : reservations){
            Log.d("ReservationsActivity", "Room reservation: " + reservation.getId());
            Log.d("ReservationsActivity", "Room reservationStart: " + reservation.getStart());

        }
        ReservationAdapter reservationAdapter = new ReservationAdapter(ReservationsActivity.this, 0, reservations);
        reservationListView.setAdapter(reservationAdapter);


    }


}
