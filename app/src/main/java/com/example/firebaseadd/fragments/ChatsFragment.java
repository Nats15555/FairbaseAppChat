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

import com.example.firebaseadd.adapter.MassageUserAdapter;
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
import java.util.List;


public class ChatsFragment extends Fragment {

    private MassageUserAdapter userAdapter;
    private List<User> chatUsers = new ArrayList<>();
    private FireBaseConnection fireBaseConnection=new FireBaseConnection();
    private List<String> userInChats =new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats,
                container, false);

        recyclerView = view.findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new MassageUserAdapter(getContext(), chatUsers);
        fireBaseConnection.getChatList().child(fireBaseConnection.getLoginUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInChats.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    userInChats.add(it.getKey());

                }
                chatsList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "The read failed: " + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void chatsList() {
        fireBaseConnection.getMyUsers().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatUsers.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    if (userInChats.contains(it.getKey())) {
                        User user = new User(it.getKey(), it.getValue(User.class).getUsername(), null);
                        chatUsers.add(user);
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