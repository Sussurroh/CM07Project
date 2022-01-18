package com.example.cm07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cm07project.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        FragmentManager fm = getSupportFragmentManager();

        mAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            for(int i = 0; i < fm.getBackStackEntryCount(); i++) {
                fm.popBackStack();
            }
            int id = item.getItemId();
            switch(id){
                case(R.id.navigation_messages):
                    MessagesFragment messagesFragment = new MessagesFragment();
                    fm.beginTransaction().replace(R.id.container, messagesFragment).commit();
                    break;

                case(R.id.navigation_map):
                    MapsFragment mapsFragment = new MapsFragment();
                    fm.beginTransaction().replace(R.id.container, mapsFragment).commit();
                    break;

                case(R.id.navigation_events):
                    EventsFragment eventsFragment = new EventsFragment();
                    fm.beginTransaction().replace(R.id.container, eventsFragment).commit();
                    break;

                case(R.id.navigation_stock):
                    StockFragment stockFragment = new StockFragment();
                    fm.beginTransaction().replace(R.id.container, stockFragment).commit();
                    break;

                case(R.id.navigation_profile):
                    ProfileFragment profileFragment = new ProfileFragment();
                    fm.beginTransaction().replace(R.id.container, profileFragment).commit();
                    break;
            }
            return true;
        });
       // bottomNavigationView.setSelectedItemId(R.id.navigation_profile);



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

}