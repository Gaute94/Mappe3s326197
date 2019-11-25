package com.example.mappe3s326197;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationsActivity extends AppCompatActivity {

    private JsonData jsonData;
    TextView roomName;
    TextView reservationDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        jsonData = (JsonData) i.getParcelableExtra("JsonData");



    }


}
