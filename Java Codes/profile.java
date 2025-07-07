package com.example.androidapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
public class profile extends AppCompatActivity {
    TextView profileName, profileEmail, profileUsername, profilePassword;
    TextView titleName, titleUsername;
    Button editProfile;
    ImageView chat, profile_image, back_button;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    String user_role = "admin";
    String name;
    FirebaseDatabase database;
    NavigationView navigationView;
    DatabaseReference reference;

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
        setContentView(R.layout.activity_profile);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileUsername = findViewById(R.id.profileUsername);
        profilePassword = findViewById(R.id.profilePassword);
        titleName = findViewById(R.id.titleName);
        titleUsername = findViewById(R.id.titleUsername);
        profile_image = findViewById(R.id.profileImage);
        back_button = findViewById(R.id.back_button);
        editProfile = findViewById(R.id.editButton);
        navigationView = findViewById(R.id.nav_view);

        showUserData();
        String username = getIntent().getStringExtra("username");
        drawerLayout = findViewById(R.id.drawer_layout);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images").child(username).child("profileImageUrl");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String imageUrl = snapshot.getValue(String.class);
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Glide.with(profile.this)
                                .load(imageUrl)
                                .placeholder(R.drawable.placeholder_image)
                                .error(R.drawable.error_image)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .fitCenter()
                                .into(profile_image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getIntent().getStringExtra("name");
                String email = getIntent().getStringExtra("email");
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent1 = new Intent(profile.this, Main_home.class);
                intent1.putExtra("name", name);
                intent1.putExtra("email", email);
                intent1.putExtra("username", username);
                intent1.putExtra("password", password);
                startActivity(intent1);
                finish();
            }
        });
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
                    Toast.makeText(profile.this, "Home Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(profile.this, Main_home.class);
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
                    Toast.makeText(profile.this, "Account Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(profile.this, profile.class);
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
                    Toast.makeText(profile.this, "Help Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(profile.this, Help.class);
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
                    Toast.makeText(profile.this, "Help Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(profile.this, AboutUs.class);
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
                    Toast.makeText(profile.this, "Log out successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(profile.this, login.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.share) {
                    Toast.makeText(profile.this, "Share Selected", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(profile.this, "Rate Us Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(profile.this, RateUs.class);
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

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            String name = getIntent().getStringExtra("name");
            String email = getIntent().getStringExtra("email");
            String username = getIntent().getStringExtra("username");
            String password = getIntent().getStringExtra("password");
            Intent intent1 = new Intent(profile.this, Main_home.class);
            intent1.putExtra("name", name);
            intent1.putExtra("email", email);
            intent1.putExtra("username", username);
            intent1.putExtra("password", password);
            startActivity(intent1);
            finish();
        }
    }

    public void showUserData(){
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");
        String emailUser = intent.getStringExtra("email");
        String usernameUser = intent.getStringExtra("username");
        String imageUri = intent.getStringExtra("imageUri");
        if (imageUri != null && !imageUri.isEmpty()) {
            Glide.with(this)
                    .load(imageUri)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(profile_image);
        }

        if (nameUser != null) {
            titleName.setText(nameUser);
        }


        String passwordUser = intent.getStringExtra("password");

        titleName.setText(nameUser);
        titleUsername.setText(usernameUser);
        profileName.setText(nameUser);
        profileEmail.setText(emailUser);
        profileUsername.setText(usernameUser);
        profilePassword.setText(passwordUser);
    }

    public void passUserData() {
        String userUsername = profileUsername.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                    String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
                    Intent intent = new Intent(profile.this, EditProfile.class);
                    intent.putExtra("name", nameFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("username", usernameFromDB);
                    intent.putExtra("password", passwordFromDB);
                    intent.putExtra("imageUri", snapshot.child(userUsername).child("profileImageUrl").getValue(String.class) != null ?
                            snapshot.child(userUsername).child("profileImageUrl").getValue(String.class) : "");
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

