package com.example.androidapplication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Main_home extends AppCompatActivity {

    private CardView firm1,firm2,firm3,firm4,firm5,firm6,firm7,firm8,firm9;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private SearchView searchView;
    ActionBarDrawerToggle drawerToggle;
    GridLayout gridLayout;
    Toolbar toolbar;
    private List<String> firmNames;
    ImageView facebook,twitter,instagram;
    private HashMap<String, CardView> firmCardViews = new HashMap<>();

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
        setContentView(R.layout.activity_main_home);
        initializeFirmCardViews();
        searchView = findViewById(R.id.searchView);
        gridLayout = findViewById(R.id.grid_layout);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the firms based on the search text
                List<String> filteredList = new ArrayList<>();
                for (String firm : firmNames) {
                    if (firm.toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(firm);
                    }
                }
                if (filteredList.isEmpty()) {
                    Toast.makeText(Main_home.this, "Nothing Found!", Toast.LENGTH_SHORT).show();
                }
                updateFirmVisibility(filteredList);
                return true;
            }
            private void updateFirmVisibility(List<String> filteredList) {
                hideAllFirms();
                for (String firm : filteredList) {
                    CardView cardView = firmCardViews.get(firm);
                    if (cardView != null) {
                        cardView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        firmNames = new ArrayList<>();
        firmNames.add("ALINCOR");
        firmNames.add("ALPHACONS");
        firmNames.add("ADDPLUS");
        firmNames.add("GRUPPO");
        firmNames.add("LASCON");
        firmNames.add("TOP-NOTCH");
        firmNames.add("ELLCAD");
        firmNames.add("MACKINTOSH");
        firmNames.add("A and D");
        firm1 = findViewById(R.id.firm1);
        firm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirm1("ALINCOR");
            }
        });
        firm2 = findViewById(R.id.firm2);
        firm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirm2("ALPHACONS");
            }
        });
        firm3 = findViewById(R.id.firm3);
        firm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirm3("ADDPLUS");
            }
        });
        firm4 = findViewById(R.id.firm4);
        firm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirm4("GRUPPO");
            }
        });
        firm5 = findViewById(R.id.firm5);
        firm5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirm5("LASCON");
            }
        });
        firm6 = findViewById(R.id.firm6);
        firm6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirm6("TOP-NOTCH");
            }
        });
        firm7 = findViewById(R.id.firm7);
        firm7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirm7("ELLCAD");
            }
        });
        firm8 = findViewById(R.id.firm8);
        firm8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirm8("MACKINTOSH");
            }
        });
        firm9 = findViewById(R.id.firm9);
        firm9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFirm9("A and D");
            }
        });
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        instagram = findViewById(R.id.instagram);
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
            public boolean onNavigationItemSelected(@NonNull MenuItem item ) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    String name = getIntent().getStringExtra("name");
                    String email = getIntent().getStringExtra("email");
                    String username = getIntent().getStringExtra("username");
                    String password = getIntent().getStringExtra("password");
                    Toast.makeText(Main_home.this, "Home Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Main_home.this, Main_home.class);
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
                    ProgressDialog progressDialog = new ProgressDialog(Main_home.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();

                    Toast.makeText(Main_home.this, "Account Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Main_home.this, profile.class);
                    intent1.putExtra("name", name);
                    intent1.putExtra("email", email);
                    intent1.putExtra("username", username);
                    intent1.putExtra("password", password);
                    startActivity(intent1);
                    finish();
                        }
                    }, 2000);
                    return true;

                } else if (itemId == R.id.help) {
                    String name = getIntent().getStringExtra("name");
                    String email = getIntent().getStringExtra("email");
                    String username = getIntent().getStringExtra("username");
                    String password = getIntent().getStringExtra("password");
                    Toast.makeText(Main_home.this, "Help Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Main_home.this, Help.class);
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
                    Toast.makeText(Main_home.this, "About Us Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Main_home.this, AboutUs.class);
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
                    Toast.makeText(Main_home.this, "Log out successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Main_home.this, login.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.share) {
                    Toast.makeText(Main_home.this, "Share Selected", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Main_home.this, "Rate Us Selected", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(Main_home.this, RateUs.class);
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

    private void initializeFirmCardViews() {

        firmCardViews.put("ALINCOR", findViewById(R.id.firm1));
        firmCardViews.put("ALPHACONS", findViewById(R.id.firm2));
        firmCardViews.put("ADDPLUS", findViewById(R.id.firm3));
        firmCardViews.put("GRUPPO", findViewById(R.id.firm4));
        firmCardViews.put("LASCON", findViewById(R.id.firm5));
        firmCardViews.put("TOP-NOTCH", findViewById(R.id.firm6));
        firmCardViews.put("ELLCAD", findViewById(R.id.firm7));
        firmCardViews.put("MACKINTOSH", findViewById(R.id.firm8));
        firmCardViews.put("A and D", findViewById(R.id.firm9));
        // Add more firms as needed
    }

    private void hideAllFirms() {
        for (CardView cardView : firmCardViews.values()) {
            cardView.setVisibility(View.GONE);
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
    public void openFirm1(String firmName){
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        Intent intent = new Intent(this, build1.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    public void openFirm2(String firmName){
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(this, build2.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    public void openFirm3(String firmName){
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(this, build3.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    public void openFirm4(String firmName){
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(this, build4.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    public void openFirm5(String firmName){
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(this, build5.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    public void openFirm6(String firmName){
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(this, build6.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    public void openFirm7(String firmName){
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(this, build7.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    public void openFirm8(String firmName){
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(this, build8.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    public void openFirm9(String firmName){
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(this, build9.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    public void openToolbar(){
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}