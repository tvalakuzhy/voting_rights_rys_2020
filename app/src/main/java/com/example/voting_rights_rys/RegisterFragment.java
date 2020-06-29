package com.example.voting_rights_rys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RegisterFragment extends Fragment {
    RecyclerView rView;
    Button register;
    private String state;
    private String initials;
    private String [] requirements;
    private boolean regNeeded;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        TextView stateName = view.findViewById(R.id.state_name);
        TextView abbv = view.findViewById(R.id.require_tag);

        if (getArguments() != null) {
            state = getArguments().getString("stateName");
            initials = getArguments().getString("abbv");
            requirements = getArguments().getStringArray("info");
            regNeeded = getArguments().getBoolean("regNeeded");
        }

        stateName.setText(state);
        if (!regNeeded) {
            abbv.setText( "Voter Registration in " + initials +" is not required. However, to be eligible to vote you must:");
        } else {
            abbv.setText(String.format(getString(R.string.register_tag), initials));
        }


        // Recycler view initialization
        rView = view.findViewById(R.id.register_list);

        rView.setAdapter(new RegisterAdapter(requirements));
        RecyclerView.LayoutManager layManager = new LinearLayoutManager(getActivity());
        rView.setLayoutManager(layManager);

        // Initialize register button
        register = view.findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String url = "https://vote.gov/register/" + initials.toLowerCase();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }




}