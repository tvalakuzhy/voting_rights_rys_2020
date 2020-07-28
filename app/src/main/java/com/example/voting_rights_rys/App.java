package com.example.voting_rights_rys;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import javax.xml.namespace.NamespaceContext;

public class App extends Application {
    public static final String CHANNEL_1_ID = "Channel_1_ID";

    @Override
    public void onCreate(){
        super.onCreate();
        //Create the app channel
        createNotificationChannel();
    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            //Create app channel
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            //set importance to determine how to interrupt user with notifications from this channel
            NotificationChannel channel_1 = new NotificationChannel(CHANNEL_1_ID, name, importance);
            channel_1.setDescription(description);

            //Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel_1);
        }
    }
}
