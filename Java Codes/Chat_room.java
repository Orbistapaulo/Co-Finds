package com.example.androidapplication;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chat_room extends AppCompatActivity {
    private Button btn_send_msg;
    private EditText input_msg;
    private TextView chat_conversation, receive_message;
    private String user_name, room_name;
    private DatabaseReference root;
    private String temp_key;
    private String senderName;
    DrawerLayout drawerLayout;
    private LinearLayout chatMessagesContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        btn_send_msg = findViewById(R.id.btn_send);
        chatMessagesContainer = findViewById(R.id.chat_messages_container);
        input_msg = findViewById(R.id.msg_input);
        room_name = getIntent().getStringExtra("room_name");
        setTitle("Room - " + room_name);
        user_name = getIntent().getStringExtra("user_name");
        setTitle("Received User Name: " + user_name);
        root = FirebaseDatabase.getInstance().getReference().child("chats").child(room_name);
        senderName = user_name;
        InputFilter noLeadingWhiteSpaceFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (dstart == 0 && source.length() > 0 && source.charAt(0) == ' ') {
                    return source.subSequence(1, source.length());
                }
                return null;
            }
        };
        input_msg.setFilters(new InputFilter[]{noLeadingWhiteSpaceFilter});
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = input_msg.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    sendMessage(user_name, messageText);
                    input_msg.setText("");
                } else {
                    Toast.makeText(Chat_room.this, "Please input a message", Toast.LENGTH_SHORT).show();
                }
            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                appendChatConversation(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void sendMessage(String userName, String messageText) {
        Map<String, Object> map = new HashMap<>();
        temp_key = root.push().getKey();
        root.updateChildren(map);
        DatabaseReference message_root = root.child(temp_key);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("user", userName);
        map2.put("msg", messageText);
        message_root.updateChildren(map2);
    }
    private void appendChatConversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            String chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            String chat_user = (String) ((DataSnapshot) i.next()).getValue();
            TextView messageTextView = new TextView(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            if (chat_user.equals(senderName)) {
                addSenderMessage("You" + ": " + chat_msg);
            } else {
                addReceiverMessage(chat_user + ": " + chat_msg);
            }
            messageTextView.setLayoutParams(layoutParams);
            chatMessagesContainer.addView(messageTextView);
        }
    }
    private void addSenderMessage(String messageText) {
        View senderMessageView = getLayoutInflater().inflate(R.layout.fragment_sender_chat, null);
        TextView senderMessageTextView = senderMessageView.findViewById(R.id.sender_message_textview);
        senderMessageTextView.setText(messageText);
        LinearLayout.LayoutParams senderLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        senderLayoutParams.gravity = Gravity.END;
        senderMessageTextView.setLayoutParams(senderLayoutParams);
        chatMessagesContainer.addView(senderMessageView);
    }
    private void addReceiverMessage(String messageText) {
        View receiverMessageView = getLayoutInflater().inflate(R.layout.fragment_receiver_chat, null);
        TextView receiverMessageTextView = receiverMessageView.findViewById(R.id.receiver_message_textview);
        receiverMessageTextView.setText(messageText);
        chatMessagesContainer.addView(receiverMessageView);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }
        else {
            super.onBackPressed();
            String name = getIntent().getStringExtra("name");
            String email = getIntent().getStringExtra("email");
            String username = getIntent().getStringExtra("username");
            String password = getIntent().getStringExtra("password");
            Intent intent1 = new Intent(Chat_room.this, Main_home.class);
            intent1.putExtra("name", name);
            intent1.putExtra("email", email);
            intent1.putExtra("username", username);
            intent1.putExtra("password", password);
            startActivity(intent1);
            finish();
        }
    }

}