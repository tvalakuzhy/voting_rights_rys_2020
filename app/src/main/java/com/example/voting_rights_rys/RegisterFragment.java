package com.example.voting_rights_rys;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RegisterFragment extends Fragment {
    RecyclerView rView;
    private String state;
    private String initials;
    private String [] requirements;

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
        }
        stateName.setText(state);
        abbv.setText(String.format(getString(R.string.register_tag), initials));


        // Recycler view initialization
        rView = view.findViewById(R.id.register_list);

        rView.setAdapter(new RegisterAdapter(requirements));
        RecyclerView.LayoutManager layManager = new LinearLayoutManager(getActivity());
        rView.setLayoutManager(layManager);

        // Inflate the layout for this fragment
        return view;
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }
}