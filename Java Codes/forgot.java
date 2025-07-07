package com.example.androidapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class forgot extends AppCompatActivity {
    EditText email;
    Button reset;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        email = findViewById(R.id.email);
        reset = findViewById(R.id.reset_button);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userEmail = email.getText().toString();
                if (!userEmail.isEmpty()) {
                    databaseReference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String newPassword = generateRandomPassword(8);
                                Toast.makeText(forgot.this, "Password Successfully Reset!", Toast.LENGTH_LONG).show();
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    databaseReference.child(childSnapshot.getKey()).child("password").setValue(newPassword);
                                }
                                showPasswordDialog(newPassword);
                            } else {
                                Toast.makeText(forgot.this, "Invalid Email!", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        });
    }
    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }
    private void showPasswordDialog(String password) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.randompass, null);
        TextView passwordTextView = dialogView.findViewById(R.id.random_pass);
        passwordTextView.setText(password);
        Intent intent = new Intent(forgot.this, EditProfile.class);
        intent.putExtra("newPassword", password);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(dialogView);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialog.show();
        dialogView.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgot.this, login.class);
                startActivity(intent);
            }
        });
    }
}