package com.example.voting_rights_rys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import org.json.*;
import java.io.BufferedReader;

class Election{
    static LinkedList elections = new LinkedList();
    private String name;
    private String date;
    private String level;
    private boolean earlyVotingAllowed;
    private boolean going;

     /** Constructor: a new Election with name Name, date electionDay, level electionLevel, and <br>
      * early voting earlyVoting. going is initialized to false.<br>
      * Adds the newly created object to the linked list elections.
     * Precondition: name has at least one character in it, electionDay is of the form YYYY-MM-DD.*/
    Election(String Name, String electionDay, String electionLevel, boolean earlyVoting){
        name = Name;
        date = formatDate(electionDay);
        level = electionLevel;
        earlyVotingAllowed = earlyVoting;
        going = false;
        elections.add(this);
    }

    /** Returns the date of the election in the format Month Day, Year. <br>
     * Example: "June 23, 2020" <br>
     * Precondition: electionDay is of the form "YYYY-MM-DD" */
    String formatDate(String electionDay){
        String year = electionDay.substring(0, 4);
        String month = electionDay.substring(5,7);
        String day = electionDay.substring(8,10);
        String formatted_date  =  month + " " + day + ", " + year;
        return formatted_date;
    }

    /* Getters and Setters */
    /** = election name **/
    String getName(){
        return name;
    }

    /** = election date **/
    String getDate(){
        return date;
    }

    /** = election level (Federal, State, Local) **/
    String getLevel(){
        return level;
    }

    /** = true if early voting is allowed for this election **/
    boolean isEarlyVotingAllowed(){
        return earlyVotingAllowed;
    }

    /** = true if the user is going to vote in this election **/
    boolean isGoing(){
        return going;
    }

    /** update the election date to newDate <br>
     * Precondition: newDate is of the form YYYY-MM-DD. **/
    void setDate(String newDate){
        date = formatDate(newDate);
    }

    /** update going **/
    void setGoing(boolean isGoing){
        going = isGoing;
    }
}
class getData {
    final TextView textView = (TextView) findViewById(R.id.text);
// ...

    // Instantiate the RequestQueue.
    RequestQueue queue = Volley.newRequestQueue(this);
    String API_URL = "https://www.googleapis.com/civicinfo/v2/voterinfo?key=";
    String address = "148%20Stony%20Brook%20Rd%20Fishkill%20NY%2012524";
    String url =API_URL + R.string.YOUR_API_KEY + "&address=" + address;

    // Request a string response from the provided URL.
    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    textView.setText(response);
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            textView.setText("That didn't work!");
        }
    });

// Add the request to the RequestQueue.
    queue.add(stringRequest);
}
/**abstract class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

    private Exception exception;


    protected String gatherJSON(Void... urls) {
        String API_URL;
        API_URL = "https://www.googleapis.com/civicinfo/v2/voterinfo?key=";
        String address = "148%20Stony%20Brook%20Rd%20Fishkill%20NY%2012524";
        // Do some validation here

        try {
            URL url = new URL(API_URL + R.string.YOUR_API_KEY + "&address=" + address);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                //Log.d(stringBuilder.toString());
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    /** Creates an election object (which is added to the linked list of elections) **/
    protected void getInfo(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        try {
            else{
                JSONObject obj = new JSONObject(response);
                String name = obj.getJSONObject("election").getString("name");
                String date = obj.getJSONObject("election").getString("electionDay");
                String ocdDivisionId = obj.getJSONObject("election").getString("ocdDivisionId");
                String level;
                if (ocdDivisionId.contains("state")) {
                    level = "state";
                } else {
                    level = "federal";
                }
                boolean earlyVoting;
                try {
                    JSONObject earlyPollingLocations = obj.getJSONObject("earlyVoteSites");
                    earlyVoting = true;
                } catch (ClassCastException cce) {
                    earlyVoting = false;
                }
                new Election(name, date, level, earlyVoting);
                //System.out.println(Election.name);
            }
        }
        catch (ClassCastException cce){
            response = "No elections"
        }
    }
}
**/
public class Elections extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elections);

        //Initialize bottom nav bar and select "Elections"
        BottomNavigationView bottomNavView = findViewById(R.id.bot_nav);
        bottomNavView.setSelectedItemId(R.id.elections);

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