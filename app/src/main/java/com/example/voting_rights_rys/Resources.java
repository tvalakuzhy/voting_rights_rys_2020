package com.example.voting_rights_rys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Resources extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        //Initialize bottom nav bar and select "Resources"
        BottomNavigationView bottomNavView = findViewById(R.id.bot_nav);
        bottomNavView.setSelectedItemId(R.id.resources);

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
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    //Called when button clicked
    public void validID(View view){
        /**Note: Still give option to see valid forms of id for other states as it will vary
         * using a drop down menu*/
        //Show the user the valid forms of ID for his/her state
    }
    //Called when button clicked
    public void voterRegistrationStatus(View view){
        //Use an API to automatically check user's registration status if possible
        //Alternative: provide user with directions on how to check voter registration status
    }
    //Called when button clicked
    public void absenteeBallot(View view){
        //Pull information from other sites and display in app using fragment (give credit to sources)
    }
    //Called when button clicked
    public void earlyVoting(View view){
        //Pull information from other sites and display in app using fragment (give credit to sources)
    }
    //Called when button clicked - Redirects to in app browser which displays website
    public void iSideWith(View view){
        //Website url
        String url = "https://www.isidewith.com/";
        //Create a builder to create the customTabsIntent
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
    //Called when button clicked
    public void howToRegisterToVote(View view){
        //Pull information from other sites and display in app using fragment (give credit to sources)
    }
}