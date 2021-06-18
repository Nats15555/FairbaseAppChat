package com.example.firebaseadd.utility;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.firebaseadd.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FireBaseConnection{
    public final String MY_USERS = "MyUsers";
    public final String CHAT_LIST = "ChatList";
    public final String FRIENDS = "Friends";
    public final String IGNORE = "Ignore";
    public final String CHATS = "Chats";
    public final String BOOK = "Book";

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

    public DatabaseReference getBook() {
        return getTableReference(BOOK);
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

    public List<User> getAllUsers(List<User> listUser,List<String> listFind, DataSnapshot snapshot){
        for (DataSnapshot it : snapshot.getChildren()) {
            if (listFind.contains(it.getKey())) {
                User user = new User(it.getKey(), it.getValue(User.class).getUsername(), null);
                listUser.add(user);
            }
        }
        return listUser;
    }
}
