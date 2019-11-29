package com.example.mappe3s326197.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mappe3s326197.models.JsonData;
import com.example.mappe3s326197.R;
import com.example.mappe3s326197.models.Reservation;

import java.util.List;

public class ReservationAdapter extends ArrayAdapter<Reservation> {

    private Context context;
    private JsonData jsonData;
    private List<Reservation> reservations;

    //private DBHandler dbHandler;

    public ReservationAdapter(Context context, int resource, List<Reservation> reservations){
        super(context, resource, reservations);
        this.context = context;
        this.reservations = reservations;
        //dbHandler = new DBHandler(context);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final Reservation reservation = reservations.get(position);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.room_list_item, null);
        }

        TextView roomName = (TextView) convertView.findViewById(R.id.roomName);
        TextView reservationDate = (TextView) convertView.findViewById(R.id.reservation_date);
        TextView start = (TextView)convertView.findViewById(R.id.start);
        TextView finished = (TextView)convertView.findViewById(R.id.finished);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {

//                PopupMenu menu = new PopupMenu (context, view);
//                menu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener ()
//                {
//                    @Override
//                    public boolean onMenuItemClick (MenuItem item)
//                    {
//                        int id = item.getItemId();
//                        switch (id)
//                        {
//                            case R.id.item_edit: editFriend(friend); break;
//                            case R.id.item_delete: deleteFriend(friend);
//
//                                break;
//                        }
//                        return true;
//                    }
//                });
//                menu.inflate (R.menu.pop_up_menu);
//                menu.show();
//            }
//        });

       start.setText(reservation.getStart().toString());
       finished.setText(reservation.getFinished().toString());

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
