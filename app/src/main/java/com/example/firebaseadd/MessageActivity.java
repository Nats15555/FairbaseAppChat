package com.example.firebaseadd;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.firebaseadd.adapter.MessageAdapter;
import com.example.firebaseadd.model.Chat;
import com.example.firebaseadd.model.User;
import com.example.firebaseadd.model.UserChat;
import com.example.firebaseadd.utility.FireBaseConnection;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private FirebaseUser loginUser;
    private FireBaseConnection fireBaseConnection = new FireBaseConnection();
    private Intent intent;

    private MessageAdapter messageAdapter;
    private UserChat userChats;
    private List<String> listIgn = new ArrayList<>();
    private String userId;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ImageView imageView = findViewById(R.id.imageview_profile);
        TextView userName = findViewById(R.id.nameBook);

        Button send = findViewById(R.id.btn_send);
        EditText msgEditTest = findViewById(R.id.test_send);
        loginUser = fireBaseConnection.getLoginUser();
        userChats = new UserChat(new ArrayList<>(), new User(loginUser.getUid(), loginUser.getDisplayName(), null));

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        Button addFriend = findViewById(R.id.add_friend);
        Button ignore = findViewById(R.id.ignore);
        messageAdapter = new MessageAdapter(MessageActivity.this, userChats.getChatList(), null);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        intent = getIntent();
        userId = intent.getStringExtra("useriq");

        fireBaseConnection.getMyUsers().child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userName.setText(user.getUsername());

                if (user.getImageUrl() == null) {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(MessageActivity.this)
                            .load(user.getImageUrl())
                            .into(imageView);
                }

                fireBaseConnection.getIgnore()
                        .child(loginUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listIgn.clear();
                        for (DataSnapshot it : snapshot.getChildren()) {
                            listIgn.add(it.getKey());
                        }
                        if (!listIgn.contains(userId)) {
                            readMessages(loginUser.getUid(), userId, user.getImageUrl());
                        } else {
                            Toast.makeText(MessageActivity.this
                                    , "Вы добавили пользователя в черный список", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msgEditTest.getText().toString();
                if (!msg.equals("")) {
                    sendMassage(loginUser.getUid(), userId, msg);
                } else {
                    Toast.makeText(MessageActivity.this
                            , "Please send a non empty msg", Toast.LENGTH_SHORT).show();
                }

            }
        });
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });
        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIgnore();
            }
        });
    }

    private void sendMassage(String sender, String receiver, String massage) {

        Map<String, Object> map = new HashMap<>();
        map.put("sender", sender);
        map.put("receiver", receiver);
        map.put("massage", massage);

        fireBaseConnection.getRef().child("Chats").push().setValue(map);
        DatabaseReference chatRef = fireBaseConnection.getChatList()
                .child(loginUser.getUid()).child(userId);
        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    chatRef.child("id").setValue("" + userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void readMessages(String myid, String userid, String imageurl) {
        fireBaseConnection.getChats().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userChats.getChatList().clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    Chat chat = it.getValue(Chat.class);
                    Iterable<DataSnapshot> myUser = it.getChildren();
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                        String message = "";
                        for (DataSnapshot inChat : myUser) {
                            message = inChat.toString().split("=")[2]
                                    .replaceFirst(".$", "")
                                    .replaceFirst(".$", "");
                            break;
                        }
                        chat.setMessage(message);
                        userChats.getChatList().add(chat);
                    }
                }
                recyclerView.setAdapter(messageAdapter);//
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessageActivity.this, "The read failed: " + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addFriend() {
        DatabaseReference refFriends = fireBaseConnection.getFriends()
                .child(loginUser.getUid()).child(userId);
        refFriends.child("id").setValue("" + userId);
    }

    private void addIgnore() {
        DatabaseReference refIgnore = fireBaseConnection.getIgnore().child(loginUser.getUid()).child(userId);
        refIgnore.child("id").setValue("" + userId);
    }
}