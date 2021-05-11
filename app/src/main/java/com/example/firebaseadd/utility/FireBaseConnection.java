package com.example.firebaseadd.utility;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseConnection {


    public DatabaseReference getMyUsers(){
        return FirebaseDatabase.getInstance().getReference("MyUsers");
    }

    public DatabaseReference getChatList(){
        return FirebaseDatabase.getInstance().getReference("ChatList");
    }

    public DatabaseReference getFriends(){
        return FirebaseDatabase.getInstance().getReference("Friends");
    }
    public DatabaseReference getIgnore(){
        return FirebaseDatabase.getInstance().getReference("Ignore");
    }
    public DatabaseReference getChats(){
        return FirebaseDatabase.getInstance().getReference("Chats");
    }
    public DatabaseReference getRef(){
        return FirebaseDatabase.getInstance().getReference();
    }

}
