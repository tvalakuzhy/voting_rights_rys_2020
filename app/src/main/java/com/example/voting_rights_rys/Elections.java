package com.example.voting_rights_rys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import static java.lang.String.join;

/** This class gets the JSON through the ElectionQuery and VoterInfoQuery <br>
and then parses it. **/
class GetFromAPI{
    static String address;
    static String API_KEY = "AIzaSyCdkZV6pD8BXJByaz5CCYdqURAZKAawvlY";
    public GetFromAPI(String address){
        this.address = address;
    }
    /** Gets the JSON of elections **/
    String getElectionJSON(){
        String API_URL =  "https://www.googleapis.com/civicinfo/v2/elections?key=";
        try {
            URL url = new URL(API_URL + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                //System.out.println(stringBuilder.toString());
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
    /** Gets the JSON from VoterInfoQuery for a particular election. **/
    String getVoterInfoJSON(Election election){

        String API_URL =  "https://civicinfo.googleapis.com/civicinfo/v2/voterinfo?address=";
        String electionID = election.getID();

        try {
            URL url = new URL(API_URL + address + "&electionID=" + electionID + "&key=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
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

    /** Parses the elction JSON, creates Election objects for each election and <br>
     * returns the last election object.
     * Precondition: response is the JSON string of elections. userState is the <br>
     * lowercase abbrevation of the state name.
     *  Ex. "ny" for New York
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    Election parseElectionJSON(String response, String userState) {
        String id;
        String name;
        String date;
        String division;
        try {
            JSONObject resp = new JSONObject(join(response, ""));
            JSONArray elections = resp.getJSONArray("elections");
            Election x = null;
            for (int i = 0; i < elections.length(); ++i) {
                JSONObject elec = elections.getJSONObject(i);
                id = elec.getString("id");
                name = elec.getString("name");
                date = elec.getString("electionDay");
                division = elec.getString("ocdDivisionId");
                x = new Election(name, date, id, division, userState);
            }
            return x;
        }
        catch (JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    void parseVoterJSON(String response, Election election){
        if(response.contains("earlyVotingSites")){
            election.isEarlyVotingAllowed = true;
        }

    }
}
/** Elections class -- an election object has fields name, date, id, level <br>
isEarlyVotingAllowed, going, and myElection **/
class Election{
    static LinkedList<Election> all_elections = new LinkedList<>();
    static LinkedList<Election> my_elections = new LinkedList<>();

    String name;
    String date;
    String id;
    String level;
    boolean isEarlyVotingAllowed = false;
    boolean going = false;
    boolean myElection = false;

    /** Returns the date in the following format: Month Day, Year. <br>
     Example: "July 2, 2020" or "November 8, 2021" <br>
     Precondition: date is the format YYYY-MM-DD. **/
    public String formattedDate(String date){
        String year = date.substring(0,4);
        String month = date.substring(5, 7);
        String day = date.substring(8,10);
        return month + " " + day + ", " + year;
    }
    /** Finds the level of the election. If the election is federal or in the same state as <br>
    userState, then myElection is set to true.
    Precondition: userState is the lowercase abbreviation of the state name <br>
    division is in the form of the following examples:
    "ocd-division/country:us" or "ocd-division/country:us/state:ny"**/
    public String findLevelandState(String division, String userState){
        if (division.contains("/state:")){
            level = "State";
            if(division.contains(userState)){
                myElection = true;
            }
        }
        else {
            level = "Federal";
            myElection = true; }

        return level;
    }

    /** Creates an election object with name n, date electionDay, id electionID, <br>
     and level indicated by division.<br>
     Federal elections and state elections occuring the same state as userState <br>
     are added to my_elections. All elections are added to all_elections. <br>
     Preconditions: electionDay is the date of the election in the format <br>
     YYYY-MM-DD; state is the lowercase abbreviation of the state (Ex: "ny" for <br>
     New York or "la" for Louisiana) **/
    public Election(String n, String electionDay, String electionID, String division, String userState){
        this.name = n;
        this.date = formattedDate(electionDay);
        this.id = electionID;
        this.level = findLevelandState(division, userState);
        all_elections.add(this);
        if (this.level.equals("Federal")){
            my_elections.add(this);
        }
        else{
            if (this.myElection){
                my_elections.add(this);
            }

        }
    }

    /* Getters and Setters */
    public LinkedList<Election> getAllElections() {return all_elections;}
    public LinkedList<Election> getMyElections() {return my_elections; }
    public String getName() {return this.name;}
    public String getDate() {return this.date;}
    public String getID() {return this.id;}
    public String getLevel(){return this.level;}
    public boolean getEarlyVoting() {return this.isEarlyVotingAllowed; }
    public boolean getGoing() {return going;}
    public void setGoing(boolean goingAns) {this.going = goingAns;}
    public void setIsEarlyVotingAllowed(boolean earlyVoting) {this.isEarlyVotingAllowed = earlyVoting ;}
    public boolean isMyElection() {return myElection; }

}

@RequiresApi(api = Build.VERSION_CODES.O)
public class Elections extends AppCompatActivity {
    static String userState;
    static String userAddress;
    static LinkedList allElections;
    static LinkedList myElections;
    //static LinearLayout allElectionsLayout = new LinearLayout();
    //static LinearLayout myElectionsLayout = new LinearLayout();
    static TextView[] allTextViews;
    static TextView[] myTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elections);

        //Get the Intent which started this activity (address string from Settings)
        Intent intent = getIntent();
        //Using the key to get the bundle (address)
        userAddress = intent.getStringExtra(Settings.EXTRA_ADDRESS);
        /**TEST THAT ADDRESS IS RECEIVED FROM SETTINGS*/
        TextView textView = findViewById(R.id.addressTest);
        textView.setText(userAddress);
        int comma_index = userAddress.indexOf("%2C");
        userState = userAddress.substring(comma_index +6, comma_index + 8).toLowerCase();


        //Initialize bottom nav bar and select "Elections"
        BottomNavigationView bottomNavView = findViewById(R.id.bot_nav);
        bottomNavView.setSelectedItemId(R.id.elections);

        //Start a new activity when a nav bar item is selected
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.elections:
                        return true;
                    case R.id.resources:
                        startActivity(new Intent(getApplicationContext(), Resources.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        GetFromAPI get = new GetFromAPI(userAddress);
        String electionJSON = get.getElectionJSON();
        Election e = get.parseElectionJSON(electionJSON, userState);

        LinkedList<TextView> all_election_views = new LinkedList<>();
        allElections = e.getAllElections();
        myElections = e.getMyElections();
        int ae_size = allElections.size();
        int mye_size = myElections.size();
        allTextViews = new TextView[ae_size];
        myTextViews = new TextView[mye_size];


        Election node; /// = e.getAllElections().head();
        String viewText;
        int my_elec_index = 0;
        for (int i = 0; i < ae_size; i++) {
            node = e.getAllElections().get(i);
            String voterJSON = get.getVoterInfoJSON(node);
            get.parseVoterJSON(voterJSON, node);
            viewText = "Name: " + node.getName() + "\n" + "Date: " + node.getDate() + "\n";

            // create a new textview
            final TextView rowTextView = new TextView(this);

            // set text for the textview -- Election Name and Date
            rowTextView.setText("Election name: " + node.getName() + "\n" + "Election date: " + node.getDate());

            // add the textview to the linearlayout
            //allElectionsLayout.addView(rowTextView);

            // save a reference to the textview for later
            allTextViews[i] = rowTextView;
            //rowTextView.setVisibility(view.INVISIBLE);

            if (node.isMyElection()) {
                //myElectionsLayout.addView(rowTextView);
                myTextViews[my_elec_index] = rowTextView;
                my_elec_index += 1;
                //rowTextView.setVisibility(view.VISIBLE);

            }
        }

    }

    /**
     * Called when the user taps the "all elections" button
     **/
    public void displayAllElections(View view) {
        for (int i = 0; i < myElections.size(); i++){
            myTextViews[i].setVisibility(view.INVISIBLE);
        }
        for (int j = 0; j < allElections.size(); j ++){
            allTextViews[j].setVisibility(view.VISIBLE);
        }
    }
    /** Called when the user taps the "My elections" button **/
    public void displayMyElections(View view) {
        for (int i = 0; i < allElections.size(); i++){
            allTextViews[i].setVisibility(view.INVISIBLE);
        }
        for (int j = 0; j < myElections.size(); j ++){
            myTextViews[j].setVisibility(view.VISIBLE);
        }
    }
}
