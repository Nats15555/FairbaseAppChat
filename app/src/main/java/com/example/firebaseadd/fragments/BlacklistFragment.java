package com.example.firebaseadd.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.firebaseadd.adapter.IgnoreAdapter;
import com.example.firebaseadd.model.IgnoreList;
import com.example.firebaseadd.model.User;
import com.example.firebaseadd.R;
import com.example.firebaseadd.utility.FireBaseConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlacklistFragment extends Fragment {

    private IgnoreAdapter userAdapter;
    private List<User> ignoreUsers = new ArrayList<>();
    private FireBaseConnection fireBaseConnection=new FireBaseConnection();
    private List<String> userInIgnore=new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blacklist,
                container, false);

        recyclerView = view.findViewById(R.id.recycler_view4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new IgnoreAdapter(getContext(), ignoreUsers);
        fireBaseConnection.getIgnore().child(fireBaseConnection.getLoginUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInIgnore.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    userInIgnore.add(it.getKey());

                }
                ignoreList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "The read failed: " + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void ignoreList() {
        fireBaseConnection.getMyUsers().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ignoreUsers.clear();
                ignoreUsers=fireBaseConnection.getAllUsers(ignoreUsers,userInIgnore, snapshot);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "The read failed: " + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}