package com.example.mappe3s326197.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mappe3s326197.models.JsonData;
import com.example.mappe3s326197.R;
import com.example.mappe3s326197.models.Room;

import java.util.List;

public class RoomAdapter extends ArrayAdapter<Room> {

    private Context context;
    private JsonData jsonData;
    private List<Room> rooms;
    private int roomId;
    //private DBHandler dbHandler;

    public RoomAdapter(Context context, int resource, List<Room> rooms){
        super(context, resource, rooms);
        this.context = context;
        this.rooms = rooms;
        //dbHandler = new DBHandler(context);
    }

    @Override
    public long getItemId(int position) {
        Log.d("RoomAdapter", "Inside getItemId. Position: " + position + ", ID: " + getItem(position).getId());
        return getItem(position).getId();
    }

    @Nullable
    @Override
    public Room getItem(int position) {
        Log.d("RoomAdapter", "Inside getItem. Position: " + position);
        return rooms.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final Room room = rooms.get(position);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.room_list_item, null);
        }


        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView description = (TextView) convertView.findViewById(R.id.description);
//        ImageButton button = (ImageButton) convertView.findViewById(R.id.view_reservations_button);
//        button.setTag(position);
//        button.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View view) {
//                  roomId = room.getId();
//                  Log.d("RoomAdapter", "Room.getId() in RoomAdapter: " + room.getId());
//
//              }
//          });
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

        name.setText(room.getName());
        description.setText(room.getDesc());
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
