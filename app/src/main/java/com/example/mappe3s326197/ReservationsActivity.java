package com.example.mappe3s326197;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

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
        Building building = i.getParcelableExtra("building");
        int roomId = i.getIntExtra("roomId", 0);

        List<Room> roomsInBuilding = jsonData.findRoomsByBuildingId(building.getId());



        room = roomsInBuilding.get(roomId);
        if(room != null){
            Log.d("ReservationsActivity", "room is not null!");
        }else{
            Log.d("ReservationsActivity", "room IS NULL!!!");
        }
        roomName = (TextView)findViewById(R.id.roomName);
        reservationDate = (TextView)findViewById(R.id.reservation_date);

        List<Reservation> reservations = jsonData.findReservationsByRoomId(roomId);

        roomName.setText(room.getName());
        reservationDate.setText("DID IT");

        ReservationAdapter reservationAdapter = new ReservationAdapter(this, 0, reservations);
        reservationListView.setAdapter(reservationAdapter);


    }


}
