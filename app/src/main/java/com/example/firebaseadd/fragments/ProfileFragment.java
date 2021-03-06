package com.example.firebaseadd.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebaseadd.model.User;
import com.example.firebaseadd.R;
import com.example.firebaseadd.utility.FireBaseConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment { ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView userName = view.findViewById(R.id.username_prof);
        TextView userId = view.findViewById(R.id.id_prof);
        FireBaseConnection fireBaseConnection=new FireBaseConnection();

        userName.setText(fireBaseConnection.getLoginUser().getEmail());
        userId.setText(fireBaseConnection.getLoginUser().getUid());

        return view;
    }
}