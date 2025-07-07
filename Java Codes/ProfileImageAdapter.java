package com.example.androidapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProfileImageAdapter {

    private Context context;
    private String imageUrl; // The URL of the profile image

    public ProfileImageAdapter(Context context, String imageUrl) {
        this.context = context;
        this.imageUrl = imageUrl;
    }

    public View createProfileImageView(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_edit_profile, parent, false);
        ImageView imageView = view.findViewById(R.id.profileImg);
        Glide.with(context).load(imageUrl).into(imageView);
        return view;
    }
}