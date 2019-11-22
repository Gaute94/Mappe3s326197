package com.example.mappe3s326197;

import androidx.fragment.app.FragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //textView = (TextView) findViewById(R.id.jsontext);
        GetJSON task = new GetJSON();
        task.execute(new String[] {"https://www.student.cs.hioa.no/~s326197/getRooms.php"});
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


    }


    private class GetJSON extends AsyncTask<String, Void, String> {

        JSONObject jsonObject;

        @Override
        protected String doInBackground(String... urls){
            String retur = "";
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
                        JSONArray mat = new JSONArray(output);
                        for(int i = 0; i < mat.length(); i++){
                            JSONObject jsonObject = mat.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            retur = retur + name + "\n";
                        }
                        return retur;
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    return retur;
                } catch (Exception e){
                    return "Noe gikk feil";
                }
            }
            return retur;
        }

        @Override
        protected void onPostExecute(String ss){
            //textView.setText(ss);
            System.out.println("Get JSON: " + ss);
        }
    }

}



