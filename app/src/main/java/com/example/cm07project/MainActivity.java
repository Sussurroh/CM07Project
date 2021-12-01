package com.example.cm07project;

import android.os.Bundle;
import android.view.MenuItem;

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

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnItemSelectedListener(item -> {
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
        bottomNavigationView.setSelectedItemId(R.id.navigation_map);
    }

}