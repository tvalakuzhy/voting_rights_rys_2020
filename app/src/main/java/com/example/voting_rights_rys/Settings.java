package com.example.voting_rights_rys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

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
        //Initiate the Calendar switch button
        final Switch UpcomingElectionsSwitch = (Switch) findViewById(R.id.UpcomingElectionsSwitch);
        //Set listener for Calender switch button
        UpcomingElectionsSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(UpcomingElectionsSwitch.isChecked()){ //Add Elections to Google calendar
                    System.out.println("Upcoming Elections switch is on");
                    /**
                     * 1. Get AllElections linked list from Elections? / Send notifications logic in Elections page?
                     * 2. Send user notifications for upcoming elections in AllElections linked list
                     */

                }else{
                    System.out.println("Upcoming Elections switch is off");
                }
            }
        });


    }

    /**
     * Uses an address verification API to check address
     * Using Unirest client
     */
    /*public boolean verifyAddress(String url){
        HttpResponse<String> response = Unirest.get("https://snapcx-avs-v1.p.rapidapi.com/validateAddress?request_id=unique_string_request_id&street=118%2520Birch%2520Drive&state=%7BPA%7D&zipcode=%7B19335%7D&city=%7BDowningtown%7D")
                .header("x-rapidapi-host", "snapcx-avs-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "506d9d5d0dmsh298094af98acb14p12ee0djsn56c594734c66")
                .asString();
        if(response.getStatus()==200){
            //Check for good response
            System.out.println("Response status is 200");


            //Condition when response returns AVS01 - perfect match
            //Condition when response returns AVS02 - partial match
            //Condition when response returns AVS03 - no matching address found
        }
        return true;
    }*/
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


        //Validate address using address verification API
        //boolean validAddress = verifyAddress(url);

        //Format address
        String fullAddress = sAddress + " " + sCity + "%20" + " " + sState + " " + sZip;
        String fullAddress20 = fullAddress.replaceAll("\\s", "%20");

        //Put the value of EditText into the Intent
        intent.putExtra(EXTRA_ADDRESS, fullAddress20);
        //Send Intent to the Elections Activity and start the Elections Activity
        startActivity(intent);
    }
}