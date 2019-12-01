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
import com.example.mappe3s326197.models.JsonData;

import java.net.HttpURLConnection;
import java.net.URL;

public class AddRoomActivity extends AppCompatActivity {
    TextView buildingAddress;
    EditText roomName;
    EditText description;
    String name;
    String desc;
    int buildingId;
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        mainLayout = (LinearLayout)findViewById(R.id.room_layout);
        buildingAddress = findViewById(R.id.building_address);
        Intent i = getIntent();
        String address = i.getStringExtra("buildingAddress");
        buildingId = i.getIntExtra("buildingId", 1);
        roomName = findViewById(R.id.room_name);
        description = findViewById(R.id.description);
        buildingAddress.setText(address);

    }

    public void addRoom(View view){
        String nameStr = roomName.getText().toString();
        name = nameStr.replaceAll(" ", "%20");
        desc = description.getText().toString().replaceAll(" ", "%20");
        AddRoomJSON task = new AddRoomJSON();
        task.execute("http://student.cs.hioa.no/~s326197/createRoom.php?name=" + name + "&description=" + desc + "&buildingId=" + buildingId);

        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }

        roomName.setText("");
        description.setText("");
        Toast.makeText(this, "Rom " + nameStr + " lagt til.",
        Toast.LENGTH_LONG).show();
        //finish();
    }

    private class AddRoomJSON extends AsyncTask<String, Void, String> {

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
