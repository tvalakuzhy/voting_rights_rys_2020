package com.example.voting_rights_rys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {
    public static final String EXTRA_ADDRESS = "com.example.voting_rights_rys.ADDRESS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Initialize bottom nav bar and select "Settings"
        BottomNavigationView bottomNavView = findViewById(R.id.bot_nav);
        bottomNavView.setSelectedItemId(R.id.settings);

        //Start a new activity when a nav bar item is selected
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.elections:
                        startActivity(new Intent(getApplicationContext(), Elections.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.resources:
                        startActivity(new Intent(getApplicationContext(), Resources.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });
    }
    //Called when submit button is clicked
    public void sendAddress(View view){
        //Create bind between Settings and Elections activities
        Intent intent = new Intent(this, Elections.class);
        //Extract address from EditText object
        EditText address = (EditText) findViewById(R.id.editTextTextAddress);
        EditText city = (EditText) findViewById(R.id.editTextTextCity);
        EditText state = (EditText) findViewById(R.id.editTextTextState);
        EditText zip = (EditText) findViewById(R.id.editTextTextZip);
        //Convert EditText objects to String objects
        String sAddress = address.getText().toString();
        String sCity = city.getText().toString();
        String sState = state.getText().toString();
        String sZip = zip.getText().toString();
        //Concatenate strings

        String fullAddress = sAddress + " " + sCity + "%2C" + " " + sState + " " + sZip;
        //Replace spaces with %20
        String fullAddress20 = fullAddress.replaceAll("\\s", "%20");
        //Add value of EditText to intent
        intent.putExtra(EXTRA_ADDRESS, fullAddress20);
        //Starts the Elections activity and sends intent
        startActivity(intent);
    }
}