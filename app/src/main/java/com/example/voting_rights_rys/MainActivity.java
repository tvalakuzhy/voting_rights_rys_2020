package com.example.voting_rights_rys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.gcm.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "homeChannelID";
    public static final int NOTIFICATION_ID1 = 12345678;
    private NotificationManagerCompat notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //Get the Intent which started this activity (address string from Settings)
        Intent intent = getIntent();
        //Using the key to get the bundle (address)
        String pollplace = intent.getStringExtra(Elections.pollingLocation);
        System.out.println("pollplace: "+ pollplace);
        if (pollplace != null) {
            //TEST THAT ADDRESS IS RECEIVED FROM SETTINGS
            TextView polltext = findViewById(R.id.pollingplace);
            polltext.setText(pollplace);
        }

        //Initialize bottom nav bar and select "Home"
        BottomNavigationView bottomNavView = findViewById(R.id.bot_nav);
        bottomNavView.setSelectedItemId(R.id.home);

        //Start a new activity when a nav bar item is selected
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
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
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }

        });
    }
}