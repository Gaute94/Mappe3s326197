package com.example.mappe3s326197;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mappe3s326197.models.Building;

public class AddBuildingActivity extends AppCompatActivity {
    TextView geoLat;
    TextView geoLng;
    EditText address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        address = findViewById(R.id.address);
        Intent i = getIntent();
        Building building = i.getParcelableExtra("building");
        address.setText(building.getAddress());
    }
}
