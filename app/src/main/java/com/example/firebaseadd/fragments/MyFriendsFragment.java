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

import com.example.firebaseadd.adapter.FriendsAdapter;
import com.example.firebaseadd.model.FriendsList;
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

public class MyFriendsFragment extends Fragment {

    private FriendsAdapter userAdapter;
    private List<User> friendUsers = new ArrayList<>();
    private DatabaseReference reference;
    private FireBaseConnection fireBaseConnection=new FireBaseConnection();
    private List<String> userInFriends=new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_friends,
                container, false);

        recyclerView = view.findViewById(R.id.recycler_view3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new FriendsAdapter(getContext(), friendUsers);
        final FirebaseUser logUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = fireBaseConnection.getFriends().child(logUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInFriends.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    userInFriends.add(it.getKey());

                }
                FriendList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "The read failed: " + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void FriendList() {
        reference = fireBaseConnection.getMyUsers();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friendUsers.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    if (userInFriends.contains(it.getKey())) {
                        User user = new User(it.getKey(), it.getValue(User.class).getUsername(), null);
                        friendUsers.add(user);
                    }
                }
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "The read failed: " + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}