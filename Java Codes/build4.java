package com.example.androidapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class build4 extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    String user_role = "admin";
    ImageView facebook,twitter,instagram,chat;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build4);


        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        instagram = findViewById(R.id.instagram);
        chat = findViewById(R.id.chat);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String profileUsername = intent.getStringExtra("username");


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUserDatabase = reference.orderByChild("username").equalTo(profileUsername);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String roleFromDB = snapshot.child(profileUsername).child("user_role").getValue(String.class).toString();
                            if (roleFromDB.equals(user_role)) {
                                Intent intent = new Intent(build4.this, chat_list.class);
                                intent.putExtra("user_name", profileUsername);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(build4.this, Chat_room.class);
                                intent.putExtra("room_name", profileUsername);
                                intent.putExtra("user_name", profileUsername);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTwitter("https://www.twitter.com");
            }

            private void openTwitter(String s) {
                Uri webpage = Uri.parse(s);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }


        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacebook("https://www.facebook.com/i.rishieeeeeee");
            }

            private void openFacebook(String s) {
                Uri webpage = Uri.parse(s);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstagram("https://www.instagram.com/irishieeeee_");
            }

            private void openInstagram(String s) {
                Uri webpage = Uri.parse(s);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });





        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    String name = getIntent().getStringExtra("name");
                    String email = getIntent().getStringExtra("email");
                    String username = getIntent().getStringExtra("username");
                    String password = getIntent().getStringExtra("password");
                    Toast.makeText(build4.this, "Home Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(build4.this, Main_home.class);
                    intent1.putExtra("name", name);
                    intent1.putExtra("email", email);
                    intent1.putExtra("username", username);
                    intent1.putExtra("password", password);
                    startActivity(intent1);
                    finish();
                    return true;
                } else if (itemId == R.id.account) {
                    String name = getIntent().getStringExtra("name");
                    String email = getIntent().getStringExtra("email");
                    String username = getIntent().getStringExtra("username");
                    String password = getIntent().getStringExtra("password");
                    Toast.makeText(build4.this, "Account Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(build4.this, profile.class);
                    intent1.putExtra("name", name);
                    intent1.putExtra("email", email);
                    intent1.putExtra("username", username);
                    intent1.putExtra("password", password);
                    startActivity(intent1);
                    finish();
                    return true;
                } else if (itemId == R.id.help) {
                    String name = getIntent().getStringExtra("name");
                    String email = getIntent().getStringExtra("email");
                    String username = getIntent().getStringExtra("username");
                    String password = getIntent().getStringExtra("password");
                    Toast.makeText(build4.this, "Help Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(build4.this, Help.class);
                    intent1.putExtra("name", name);
                    intent1.putExtra("email", email);
                    intent1.putExtra("username", username);
                    intent1.putExtra("password", password);
                    startActivity(intent1);
                    finish();
                    return true;

                } else if (itemId == R.id.about) {
                    String name = getIntent().getStringExtra("name");
                    String email = getIntent().getStringExtra("email");
                    String username = getIntent().getStringExtra("username");
                    String password = getIntent().getStringExtra("password");
                    Toast.makeText(build4.this, "About Us Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(build4.this, AboutUs.class);
                    intent1.putExtra("name", name);
                    intent1.putExtra("email", email);
                    intent1.putExtra("username", username);
                    intent1.putExtra("password", password);
                    startActivity(intent1);
                    finish();
                    return true;

                } else if (itemId == R.id.logout) {
                    SharedPreferences preferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("username");
                    editor.remove("password");
                    editor.putBoolean("autoLogin", false);
                    editor.apply();
                    Toast.makeText(build4.this, "Log out successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(build4.this, login.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.share) {
                    Toast.makeText(build4.this, "Share Selected", Toast.LENGTH_SHORT).show();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String shareText = "Promote RRD Builders on various platforms";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                    Intent chooser = Intent.createChooser(shareIntent, "Share via");
                    startActivity(chooser);
                    return true;

                } else if (itemId == R.id.rate) {
                    String name = getIntent().getStringExtra("name");
                    String email = getIntent().getStringExtra("email");
                    String username = getIntent().getStringExtra("username");
                    String password = getIntent().getStringExtra("password");
                    Toast.makeText(build4.this, "Rate Us Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(build4.this, RateUs.class);
                    intent1.putExtra("name", name);
                    intent1.putExtra("email", email);
                    intent1.putExtra("username", username);
                    intent1.putExtra("password", password);
                    startActivity(intent1);
                    finish();
                    return true;
                }
                return false;
            }
        });

    }





    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }
        else {
            super.onBackPressed();
        }
    }
}
