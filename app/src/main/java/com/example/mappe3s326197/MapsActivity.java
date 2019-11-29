package com.example.mappe3s326197;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mappe3s326197.adapter.RoomAdapter;
import com.example.mappe3s326197.models.Building;
import com.example.mappe3s326197.models.JsonData;
import com.example.mappe3s326197.models.Reservation;
import com.example.mappe3s326197.models.Room;
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
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView textView;
    private JsonData JSONData = new JsonData();
    private List<LatLng> allPoints = new ArrayList<>();
    Marker selectedMarker = null;
    ListView roomList;
    Button showRoomsButton;
    Marker currentMarker = null;
    RoomAdapter roomAdapter;
    Building currentBuilding;

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


//        showRoomsButton = (Button) findViewById(R.id.viewRoomsButton);
//
//        showRoomsButton.setOnClickListener( new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                final Dialog dialog = new Dialog(MapsActivity.this);
//                dialog.setContentView(R.layout.custom_dialog);
//                dialog.setTitle("Title...");
//                roomList= (ListView) dialog.findViewById(R.id.List);
//                roomAdapter = new RoomAdapter(MapsActivity.this, 0, jsonData.getRooms());
//                roomList.setAdapter(roomAdapter);
//                dialog.show();
//
//            }
//        });
    }


    public void addMarker(View view){
        System.out.println("Inside addMarker");;
        for(Building building : JSONData.getBuildings()){
            Log.d("MapsActivity", "Building Address: " + building.getAddress());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(building.getGeoLat(), building.getGeoLng()))
                    .title(building.getAddress()));
            marker.setTag(building.getId());
        }

    }

    public void addRoom(View view){
        Intent intent = new Intent(getApplicationContext(),ReservationsActivity.class);
        Log.d("MapsActivity", "Inside addRoom");

        intent.putExtra("jsonData", JSONData);
        intent.putExtra("building", currentBuilding);
        startActivity(intent);
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

                    final Building building = JSONData.findBuildingByCoordinates(marker.getPosition());
                    final List<Room> roomsInBuilding = JSONData.findRoomsByBuildingId(building.getId());
                    currentBuilding = building;
                    marker.showInfoWindow();
                    selectedMarker = marker;
                    Log.d("MapsActivity", "This is a room");
                    final Dialog dialog = new Dialog(MapsActivity.this);
                    dialog.setContentView(R.layout.listview_dialog);
                    dialog.setTitle("Title...");

                    roomList= (ListView) dialog.findViewById(R.id.List);
                    Button addRoomButton = dialog.findViewById(R.id.add_room_button);
                    addRoomButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addRoom(view);
                        }
                    });

                    TextView buildingAddress = (TextView) dialog.findViewById(R.id.building_address);
                    buildingAddress.setText(building.getAddress());
                    roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, final int i, final long l) {

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                            builder1.setMessage("Hva vil du gjøre?");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton(
                                    "Reserver rom",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Log.d("MapsActivity", "Clicked Reserver.");
                                            dialog.cancel();
                                        }
                                    });

                            builder1.setNegativeButton(
                                    "Se reservasjoner",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Log.d("MapsActivity", "Clicked Se Reservasjoner");
                                            Intent intent = new Intent(getApplicationContext(),ReservationsActivity.class);
                                            Log.d("MapsActivity", "What I think is room id: " + l);
                                            Log.d("MapsActivity", "What might be the position in list: " + i);

                                            intent.putExtra("roomId", (int)l);
                                            intent.putExtra("building", building);
                                            intent.putExtra("jsonData", JSONData);
                                            startActivity(intent);
                                            dialog.cancel();

                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();




                        }
                    });


                    roomAdapter = new RoomAdapter(MapsActivity.this, 0, roomsInBuilding);
                    roomList.setAdapter(roomAdapter);
                    dialog.show();

//                    roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//
//                        }
//                    });


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

    public void showReservations(View view){

        Intent myIntent = new Intent(MapsActivity.this, ReservationsActivity.class);
        myIntent.putExtra("JsonData", JSONData);


        MapsActivity.this.startActivity(myIntent);
    }

    private void startNewActivity(Class myclass){
        Intent myIntent = new Intent(MapsActivity.this, myclass);
        myIntent.putExtra("JsonData", JSONData);
        MapsActivity.this.startActivity(myIntent);
    }

    public void viewRooms(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
        if(selectedMarker != null) {
            Building building = JSONData.findBuildingByCoordinates(selectedMarker.getPosition());
            builder1.setMessage(building.getAddress());

        }
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
    }

    public void addBuilding(View view){

        //Denne er brukbar man skal legge til rom på en eksisterende bygning!
        //Building building = jsonData.findBuildingByCoordinates(currentMarker.getPosition());
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);

        if(currentMarker == null){
            builder1.setMessage("Du må velge et punkt på kartet først.");
            builder1.setCancelable(true);

            builder1.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.d("MapsActivity", "Clicked Ok");
                    dialog.cancel();
                }
            });
        }else {
            builder1.setMessage("Vil du registrere en ny bygning her?");
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
                            currentMarker.remove();
                            currentMarker=null;
                            Log.d("MapsActivity", "Clicked No");
                            dialog.cancel();
                        }
                    });
        }
        AlertDialog alert11 = builder1.create();
        alert11.show();
        Log.d("MapsActivity", "This marker does not have a tag. It can become a room.");
    }

    public void onButtonShowPopupWindowClick(View view) {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
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

                            Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(reservationObject.getString("start"));
                            reservation.setStart(startDate);
                            Date finishedDate = (new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.US).parse(reservationObject.getString("finished")));
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

                        for(Room room : allRooms){
                            Log.d("MapsActivity", "doInBackground room name: " + room.getName());
                        }
                        for(Building building : allBuildings){
                            Log.d("MapsActivity", "doInBackground building address: " + building.getAddress());
                        }
                        for(Reservation reservation : allReservations){
                            Log.d("MapsActivity", "doInBackground reservations: " + reservation.getId());
                        }



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
        protected void onPostExecute(JsonData jsonData){

            jsonData.printAllData();
            JSONData = jsonData;
            //textView.setText(ss);
//            rooms.addAll(ss);
//            JSONData = ss;
//            JSONData.printAllData();

        }
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



