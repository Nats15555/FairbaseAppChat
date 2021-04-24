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


public class MyFriendsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<User> mUsers = new ArrayList<>();
    private Set<User> mUsersFrend = new HashSet<>();
    private FriendsAdapter friendsAdapter;
    Button dell_btm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_friends, container, false);

        //dell_btm=view.findViewById(R.id.dell);
        //dell_btm.setVisibility(view.INVISIBLE);

        recyclerView = view.findViewById(R.id.recycler_view3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        friendsAdapter = new FriendsAdapter(getContext(), new ArrayList<>(mUsersFrend));
        ReadUsers();
        ReadFriend();
        return view;
    }

    private void ReadFriend() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Friends");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsersFrend.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    Map key = (Map) it.getValue();
                    String value = (String) key.get(firebaseUser.getUid());
                    for (User s : mUsers) {
                        if (value.equals(s.getId())) {
                            mUsersFrend.add(s);
                        }
                    }
                    recyclerView.setAdapter(friendsAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ReadUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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