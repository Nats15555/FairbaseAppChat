package com.example.firebaseadd.utility;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseConnection {
    public final String MY_USERS = "MyUsers";
    public final String CHAT_LIST = "ChatList";
    public final String FRIENDS = "Friends";
    public final String IGNORE = "Ignore";
    public final String CHATS = "Chats";

    private DatabaseReference getTableReference(String myUsers) {
        return FirebaseDatabase.getInstance().getReference(myUsers);
    }

    public DatabaseReference getMyUsers() {
        return getTableReference(MY_USERS);
    }

    public DatabaseReference getChatList() {
        return getTableReference(CHAT_LIST);//public final String CHAT_LIST = "Chatlist"
    }

    public DatabaseReference getFriends() {
        return getTableReference(FRIENDS);
    }

    public DatabaseReference getIgnore() {
        return getTableReference(IGNORE);
    }

    public DatabaseReference getChats() {
        return getTableReference(CHATS);
    }

    public DatabaseReference getRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public FirebaseUser getLoginUser() {
        return getAuth().getCurrentUser();
    }
    public FirebaseAuth getAuth(){
      return FirebaseAuth.getInstance();
    };

}
