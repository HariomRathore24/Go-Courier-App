package com.example.courierserviceapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find your logout LinearLayout
        LinearLayout logoutLayout = view.findViewById(R.id.logout);

        // Set OnClickListener for logout LinearLayout
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call Firebase sign out method
                mAuth.signOut();
                // Redirect user to login screen, you can use Intent to navigate to your login activity
                Intent intent = new Intent(getActivity(), LoginPage.class);
                startActivity(intent);
                getActivity().finish(); // Close the current activity
            }
        });

        return view;
    }
}