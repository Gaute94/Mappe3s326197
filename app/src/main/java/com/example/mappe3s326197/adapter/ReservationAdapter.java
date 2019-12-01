package com.example.mappe3s326197.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mappe3s326197.models.JsonData;
import com.example.mappe3s326197.R;
import com.example.mappe3s326197.models.Reservation;

import java.text.DateFormat;
import java.util.List;

public class ReservationAdapter extends ArrayAdapter<Reservation> {

    private Context context;
    private JsonData jsonData;
    private List<Reservation> reservations;
    private DateFormat dateFormat;

    //private DBHandler dbHandler;

    public ReservationAdapter(Context context, int resource, List<Reservation> reservations){
        super(context, resource, reservations);
        this.context = context;
        this.reservations = reservations;
        dateFormat = android.text.format.DateFormat.getTimeFormat(context);
        //dbHandler = new DBHandler(context);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final Reservation reservation = reservations.get(position);
        for(Reservation reservation1 : reservations){
            Log.d("ReservationAdapter", "ReservationList, reservationID: " + reservation1.getId());
        }

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reservation_list_item, null);
        }

//        TextView roomName = (TextView) convertView.findViewById(R.id.roomName);
//        TextView reservationDate = (TextView) convertView.findViewById(R.id.reservation_date);
        TextView start = (TextView)convertView.findViewById(R.id.start);
        TextView finished = (TextView)convertView.findViewById(R.id.finished);

        if(start == null){
            Log.d("ReservationAdapter", "Start == null");

        }else{
            Log.d("ReservationAdapter", "reservation.getId(): " + reservation.getId());
            Log.d("ReservationAdapter", "reservation.getStart: " + reservation.getStart());
            Log.d("ReservationAdapter", "reservation.getStart.toString(): " + reservation.getStart().toString());
        }

       String startText = "Reservert fra: " +  dateFormat.format(reservation.getStart());
       String finishedText = "Reservert til: " +  dateFormat.format(reservation.getFinished());

       start.setText(startText);
       finished.setText(finishedText);

        return convertView;
    }

//    private void deleteFriend(Friend friend){
//        dbHandler.delete(friend.getId());
//        loadLists();
//    }
//
//    private void editFriend(Friend friend){
//        Intent intent = new Intent(context, AddFriendActivity.class);
//        intent.putExtra("FriendID", Long.toString(friend.getId()));
//        context.startActivity(intent);
//    }

//    public void loadLists(){
//        ArrayList<Room> friendList = new ArrayList<>(dbHandler.findAllFriends());
//        clear();
//        addAll(friendList);
//        notifyDataSetChanged();
//    }


}
