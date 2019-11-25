package com.example.mappe3s326197;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends ArrayAdapter<Room> {

    private Context context;
    private JsonData jsonData;
    private List<Room> rooms;
    //private DBHandler dbHandler;

    public RoomAdapter(Context context, int resource, List<Room> rooms){
        super(context, resource, rooms);
        this.context = context;
        this.rooms = rooms;
        //dbHandler = new DBHandler(context);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final Room room = rooms.get(position);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.room_list_item, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView description = (TextView) convertView.findViewById(R.id.description);

        Button button = (Button) convertView.findViewById(R.id.view_reservations_button);
        button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {


              }
          });
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
