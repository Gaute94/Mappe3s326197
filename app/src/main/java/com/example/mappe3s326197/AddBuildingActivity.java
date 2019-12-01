package com.example.mappe3s326197;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mappe3s326197.models.Building;

import java.net.HttpURLConnection;
import java.net.URL;

public class AddBuildingActivity extends AppCompatActivity {
    TextView geoLat;
    TextView geoLng;
    EditText address;
    String geoLatStr;
    String geoLngStr;
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_building);
        mainLayout = findViewById(R.id.building_layout);
        geoLat = findViewById(R.id.geoLat);
        geoLng = findViewById(R.id.geoLng);
        address = findViewById(R.id.address);
        Intent i = getIntent();
        geoLatStr = String.valueOf(i.getDoubleExtra("geoLat", 0));
        geoLngStr = String.valueOf(i.getDoubleExtra("geoLng", 0));
        String geoLatTxt = "Lat: " + geoLngStr;
        String geoLngTxt = "Lng: " + geoLngStr;
        geoLat.setText(geoLatTxt);
        geoLng.setText(geoLngTxt);

    }

    public void addBuilding(View view){
        AddBuildingJSON task = new AddBuildingJSON();
        Log.d("AddBuildingActivity", "Address: " + address.getText());
        Log.d("AddBuildingActivity", "Lat: " + geoLat.getText());
        Log.d("AddBuildingActivity", "Lng: " + geoLng.getText());

        String addressStr = address.getText().toString().replaceAll(" ", "%20");



        task.execute("http://student.cs.hioa.no/~s326197/createBuilding.php?address=" + addressStr + "&geoLat=" + geoLatStr + "&geoLng=" + geoLngStr);

        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }


        Toast.makeText(this, "Bygning lagt til.",
                Toast.LENGTH_LONG).show();

        address.setText("");
        //finish();
    }

    private class AddBuildingJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String query = urls[0];

            Log.d("AddRoomActivity", "Query: " + query);

            try {
                URL urlen = new URL(query);
                HttpURLConnection conn = (HttpURLConnection)
                        urlen.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code: " + conn.getResponseCode());
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return "OK";
        }
        @Override
        protected void onPostExecute(String ss){

        }

    }



}
