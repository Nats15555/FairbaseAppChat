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
import com.example.firebaseadd.Adapter.FriendsAdapter;
import com.example.firebaseadd.Adapter.MessageAdapter;
import com.example.firebaseadd.Model.Chat;
import com.example.firebaseadd.Model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    TextView username;
    ImageView imageView;

    RecyclerView recyclerView;
    EditText msg_editTest;
    Button send;

    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;

    MessageAdapter messageAdapter;
    private List<Chat> mchat = new ArrayList<>();
    String userid;
    Button addFriend;
    Button ignore;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        imageView = findViewById(R.id.imageview_profile);
        username = findViewById(R.id.username);

        send = findViewById(R.id.btn_send);
        msg_editTest = findViewById(R.id.test_send);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        addFriend = findViewById(R.id.add_friend);
        ignore = findViewById(R.id.ignore);
        messageAdapter = new MessageAdapter(MessageActivity.this, mchat, null);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().hide();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        intent = getIntent();
        userid = intent.getStringExtra("useriq");

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                username.setText(user.getUsername());

                if (user.getImageUrl() == null) {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(MessageActivity.this)
                            .load(user.getImageUrl())
                            .into(imageView);
                }


                readMessages(fuser.getUid(), userid, user.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msg_editTest.getText().toString();
                if (!msg.equals("")) {
                    sendMassage(fuser.getUid(), userid, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "Please send a non empty msg", Toast.LENGTH_SHORT).show();
                }

                msg_editTest.setText("");
            }
        });
//доделать добавление в друзья
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });
//доделать добавление в игнор
        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIgnore();
            }
        });
    }

    private void sendMassage(String sender, String receiver, String massage) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> map = new HashMap<>();
        map.put("sender", sender);
        map.put("receiver", receiver);
        map.put("massage", massage);

        reference.child("Chats").push().setValue(map);

        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("ChatList")
                .child(fuser.getUid()).child(userid);
        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    chatRef.child("id").setValue("" + userid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void readMessages(String myid, String userid, String imageurl) {

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mchat.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    Chat chat = it.getValue(Chat.class);
                    Iterable<DataSnapshot> myUser = it.getChildren();
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                        String message = "";
                        for (DataSnapshot inChat : myUser) {
                            message = inChat.toString().split("=")[2].replaceFirst(".$", "")
                                    .replaceFirst(".$", "");
                            break;
                        }
                        chat.setMessage(message);
                        mchat.add(chat);
                    }
                }
                recyclerView.setAdapter(messageAdapter);//
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addFriend() {
        Map<String, Object> map = new HashMap<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        map.put(fuser.getUid(), userid);
        reference.child("Friends").push().setValue(map);
    }

    private void addIgnore() {
        Map<String, Object> map = new HashMap<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        map.put(fuser.getUid(), userid);
        reference.child("Ignore").push().setValue(map);
    }
}