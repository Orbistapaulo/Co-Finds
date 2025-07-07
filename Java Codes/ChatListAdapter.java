package com.example.androidapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ChatListAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> list_of_rooms;
    private String user_name;
    private DatabaseReference imagesReference;
    public ChatListAdapter(Context context, int resource, ArrayList<String> list_of_rooms, String user_name) {
        super(context, resource, list_of_rooms);
        this.context = context;
        this.list_of_rooms = list_of_rooms;
        this.user_name = user_name;
        this.imagesReference = FirebaseDatabase.getInstance().getReference("Images");
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_chat, parent, false);
        TextView textView = rowView.findViewById(R.id.usernameTextView);
        ImageView imageView = rowView.findViewById(R.id.profileImageView);
        String chatRoomName = list_of_rooms.get(position);
        textView.setText(chatRoomName);
        loadUserProfileImage(chatRoomName, imageView);
        return rowView;
    }

    private void loadUserProfileImage(String username, ImageView imageView) {
        DatabaseReference databaseReference = imagesReference.child(username).child("profileImageUrl");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String imageUrl = snapshot.getValue(String.class);
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Glide.with(context)
                                .load(imageUrl)
                                .placeholder(R.drawable.placeholder_image)
                                .error(R.drawable.error_image)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .fitCenter()
                                .transform(new RoundedCornersTransformation(20, 0))
                                .into(imageView);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}