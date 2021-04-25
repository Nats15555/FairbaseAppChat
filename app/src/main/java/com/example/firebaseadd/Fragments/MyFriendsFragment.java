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
import com.example.firebaseadd.Adapter.MessageAdapter;
import com.example.firebaseadd.Adapter.UserAdapter;
import com.example.firebaseadd.Model.Chatlist;
import com.example.firebaseadd.Model.FriendsList;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MyFriendsFragment extends Fragment {

    private FriendsAdapter userAdapter;
    private List<User> mUsers = new ArrayList<>();

    private DatabaseReference reference;

    private Map<String, FriendsList> usersList = new HashMap<>();

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_friends,
                container, false);

        recyclerView = view.findViewById(R.id.recycler_view3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new FriendsAdapter(getContext(), mUsers);

        reference = FirebaseDatabase.getInstance().getReference("Friends");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    String id = it.getKey();
                    Iterable<DataSnapshot> ll = it.getChildren();
                    List<String> list = new ArrayList<>();
                    for (DataSnapshot test : ll) {
                        list.add(test.getKey());
                    }
                    usersList.put(id, new FriendsList(id, list));
                }
                friendsList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void friendsList() {
        reference = FirebaseDatabase.getInstance().getReference("MyUsers");
        final FirebaseUser logUser = FirebaseAuth.getInstance().getCurrentUser();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                if (usersList.containsKey(logUser.getUid())) {
                    FriendsList mt = usersList.get(logUser.getUid());
                    List io = mt.getValue();
                    for (DataSnapshot it : snapshot.getChildren()) {
                        if (io.contains(it.getKey())) {
                            User user = new User(it.getKey(), it.getValue(User.class).getUsername(), null);
                            mUsers.add(user);
                        }
                    }
                }
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}