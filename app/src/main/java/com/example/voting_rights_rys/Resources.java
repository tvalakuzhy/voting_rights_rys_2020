package com.example.voting_rights_rys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Resources extends AppCompatActivity {
    private FrameLayout fragContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        //Initialize bottom nav bar and select "Resources"
        BottomNavigationView bottomNavView = findViewById(R.id.bot_nav);
        bottomNavView.setSelectedItemId(R.id.resources);

        //Initialize fragment container
        fragContainer = (FrameLayout) findViewById(R.id.fragment_container);

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
    //Called when button clicked
    public void iSideWith(View view){
        //Redirects to in app browser which displays website
    }
    //Called when button clicked
    public void howToRegisterToVote(View view){
        //Pull information from other sites and display in app using fragment (give credit to sources)
        openFragment("register");

    }

    // Opens Fragment
    public void openFragment(String button) {
        switch(button) {
            case "register":
                RegisterFragment frag = RegisterFragment.newInstance();
                FragmentManager fManager = getSupportFragmentManager();
                FragmentTransaction transaction = fManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
                transaction.addToBackStack(null);
                transaction.add(R.id.fragment_container, frag,"REGISTER_FRAGMENT").commit();
                break;
        }
    }



}