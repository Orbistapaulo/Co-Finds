package com.example.androidapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chat_list extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private DatabaseReference root;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        listView = findViewById(R.id.listView);
        user_name = getIntent().getStringExtra("user_name");
        setTitle("Chat Rooms for User: " + user_name);
        arrayAdapter = new ChatListAdapter(this, R.layout.list_item_chat, list_of_rooms, user_name);
        listView.setAdapter(arrayAdapter);
        root = FirebaseDatabase.getInstance().getReference("chats");
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDeleteConfirmationDialog(i);
                return true;
            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_of_rooms.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String chatRoomName = snapshot.getKey();
                    list_of_rooms.add(chatRoomName);
                }
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String chatRoomName = list_of_rooms.get(i);
                Intent intent = new Intent(getApplicationContext(), Chat_room.class);
                intent.putExtra("room_name", chatRoomName);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
            }
        });

    }
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this chat room?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteChatRoom(position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create().show();
    }
    private void deleteChatRoom(int position) {
        String chatRoomName = list_of_rooms.get(position);
        DatabaseReference chatRoomRef = root.child(chatRoomName);
        chatRoomRef.removeValue();
    }
}