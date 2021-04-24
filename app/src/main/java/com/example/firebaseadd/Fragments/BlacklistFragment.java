package com.example.firebaseadd.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.firebaseadd.Adapter.FriendsAdapter;
import com.example.firebaseadd.Adapter.IgnorAdapter;
import com.example.firebaseadd.Model.User;
import com.example.firebaseadd.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BlacklistFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<User> mUsers = new ArrayList<>();
    private Set<User> mUsersIgnore = new HashSet<>();
    private IgnorAdapter ignoreAdapter;
    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    Button dell_btm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blacklist, container, false);

        //dell_btm=view.findViewById(R.id.dell);
        //dell_btm.setVisibility(view.INVISIBLE);

        recyclerView = view.findViewById(R.id.recycler_view4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ignoreAdapter = new IgnorAdapter(getContext(), new ArrayList<>(mUsersIgnore));
        ReadUsers();
        ReadIgnore();
        return view;
    }

    private void ReadIgnore() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Ignore");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsersIgnore.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    Map key = (Map) it.getValue();
                    String value = (String) key.get(firebaseUser.getUid());
                    for (User s : mUsers) {
                        if (value.equals(s.getId())) {
                            mUsersIgnore.add(s);
                        }
                    }
                    recyclerView.setAdapter(ignoreAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void ReadUsers() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MyUsers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();

                for (DataSnapshot it : snapshot.getChildren()) {
                    User user = it.getValue(User.class);

                    assert user != null;
                    if (!user.getId().equals(firebaseUser.getUid())) {
                        mUsers.add(user);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}