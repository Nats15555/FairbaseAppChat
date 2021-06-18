package com.example.firebaseadd.utility;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.firebaseadd.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Subscriber implements Observer{
    List<User> listAllUser;
    List<String> listKeyUsersInSomething;
    FireBaseConnection fireBaseConnection;

    public Subscriber(List<User> listAllUser, List<String> listKeyUsersInSomething, FireBaseConnection fireBaseConnection) {
        this.listAllUser = listAllUser;
        this.listKeyUsersInSomething = listKeyUsersInSomething;
        this.fireBaseConnection = fireBaseConnection;
    }

    @Override
    public void handleEvent(List<User> userList) {
        fireBaseConnection.getMyUsers().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAllUser.clear();
                listAllUser=fireBaseConnection.getAllUsers(listAllUser,listKeyUsersInSomething, snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }
