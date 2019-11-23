package com.example.mappe3s326197;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView textView;
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private List<LatLng> allPoints = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();
    private JsonData jsonData = new JsonData();
    Marker currentMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        //textView = (TextView) findViewById(R.id.jsontext);
        GetJSON task = new GetJSON();
        task.execute("http://student.cs.hioa.no/~s326197/getJson.php");



    }


    public void addMarker(View view){
        for(Building building : buildings){
            Log.d("MapsActivity", "Building Address: " + building.getAddress());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(building.getGeoLat(), building.getGeoLng()))
                    .title(building.getAddress()));
            marker.setTag(building.getId());
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10, 10))
                .title("Hello world"));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                Log.d("MapsActivity", "inside onMapClick");

                allPoints.add(point);
                //mMap.clear();
                if (currentMarker!=null) {
                    currentMarker.remove();
                    currentMarker=null;
                }
                if(currentMarker == null) {
                    currentMarker = mMap.addMarker(new MarkerOptions().position(point));
                }
            }

        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                if(marker.getTag() != null){
                    marker.showInfoWindow();
                    Log.d("MapsActivity", "This is a room");
                }else{

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                    builder1.setMessage("Vil du registrere et nytt rom her?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Ja",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.d("MapsActivity", "Clicked Yes");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "Nei",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.d("MapsActivity", "Clicked No");
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    Log.d("MapsActivity", "This marker does not have a tag. It can become a room.");
                }
                return true;

            }
        });
    }


    private class GetJSON extends AsyncTask<String, Void, JsonData> {

        JSONObject jsonObject;

        @Override
        protected JsonData doInBackground(String... urls){
            String retur = "";
            JsonData jsonData = new JsonData();

            String s = "";
            String output = "";
            for (String url : urls){
                try {
                    URL urlen = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection)
                            urlen.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");
                    if(conn.getResponseCode() != 200){
                        throw new RuntimeException("Failed : HTTP error code: " + conn.getResponseCode());
                    }
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            conn.getInputStream()));
                    System.out.println("Output from Server ..... \n");
                    while ((s = br.readLine()) != null){
                        output = output + s;
                    }

                    conn.disconnect();
                    try {
                        Log.d("MapsActivity", "doInBackground: " + output);
                        JSONObject jsonObject = new JSONObject(output);

                        List<Building> allBuildings = new ArrayList<>();

                        JSONArray buildingArray = jsonObject.getJSONArray("buildings");

                        for(int i = 0; i < buildingArray.length(); i++) {

                            JSONObject buildingObject = buildingArray.getJSONObject(i);
                            Building building = new Building();
                            building.setId(buildingObject.getInt("id"));
                            building.setAddress(buildingObject.getString("address"));
                            building.setGeoLat((float)buildingObject.getDouble("geoLat"));
                            building.setGeoLng((float)buildingObject.getDouble("geoLng"));

                            allBuildings.add(building);
                        }

                        List<Room> allRooms = new ArrayList<>();

                        JSONArray roomArray = jsonObject.getJSONArray("rooms");

                        for(int i = 0; i < roomArray.length(); i++){
                            JSONObject roomObject = roomArray.getJSONObject(i);
                            Room room = new Room();
                            room.setId(roomObject.getInt("id"));
                            room.setName(roomObject.getString("name"));
                            room.setDesc(roomObject.getString("description"));

                            for(Building building : allBuildings){
                                if(building.getId() == roomObject.getInt("buildingId")){
                                    room.setBuilding(building);
                                }
                            }
                            allRooms.add(room);
                        }



                        List<Reservation> allReservations = new ArrayList<>();
                        JSONArray reservationArray = jsonObject.getJSONArray("reservations");

                        for(int i = 0; i < reservationArray.length(); i++){
                            JSONObject reservationObject = reservationArray.getJSONObject(i);
                            Reservation reservation = new Reservation();
                            reservation.setId(reservationObject.getInt("id"));

                            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(reservationObject.getString("start"));
                            reservation.setStart(startDate);
                            Date finishedDate = (new SimpleDateFormat("dd/MM/yyyy").parse(reservationObject.getString("finished")));
                            reservation.setFinished(finishedDate);

                            for(Room room : allRooms){
                                if(room.getId() == reservationObject.getInt("roomId")){
                                    reservation.setRoom(room);
                                }
                            }
                        }

                        jsonData.setBuildings(allBuildings);
                        jsonData.setRooms(allRooms);
                        jsonData.setReservations(allReservations);





//                        JSONArray mat = new JSONArray(output);
//                        for(int i = 0; i < mat.length(); i++){
//                            JSONObject jsonObject = mat.getJSONObject(i);
//                            int id = jsonObject.getInt("id");
//                            String name = jsonObject.getString("name");
//                            String desc = jsonObject.getString("description");
//                            Float geoLat = (float)jsonObject.getDouble("geoLat");
//                            Float geoLng = (float)jsonObject.getDouble("geoLng");
//
//
//                            Log.d("MapsActivity", "name in Background: " + name);
//                            Log.d("MapsActivity", "desc in Background: " + desc);
//                            Log.d("MapsActivity","geoLat in Background: " + geoLat);
//                            Log.d("MapsActivity","geoLng in Background: " + geoLng);
//
//                            Room room = new Room();
//                            room.setId(id);
//                            room.setName(name);
//                            room.setDesc(desc);
//
//
//                            rooms.add(room);
//                            //retur = retur + name + "\n";
//                        }
////                        return rooms;
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    return jsonData;
                } catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
            return jsonData;
        }

        @Override
        protected void onPostExecute(JsonData ss){
            //textView.setText(ss);
//            rooms.addAll(ss);
            jsonData = ss;
            jsonData.printAllData();

        }
    }

    public Room findRoomById(int id){
        for(Room room : rooms){
            if (room.getId() == id){
                return room;
            }
        }
        Log.d("MapsActivity", "No room with that ID");
        return null;
    }




    /*
    public List<Reservation> getReservationsByRoomId(int id){
        List<Reservation> roomReservations = new ArrayList<>();
        for(Reservation reservation : reservations ) {
            if(reservation.getRoom().getId() == id){
                roomReservations.add(reservation);
            }
        }
        return roomReservations;
    }
    */
}



