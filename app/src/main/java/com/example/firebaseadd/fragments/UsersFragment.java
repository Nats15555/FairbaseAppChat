package com.example.firebaseadd.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebaseadd.adapter.AllUserAdapter;
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


public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<User> mUsers = new ArrayList<>();
    private List<User> sortUser = new ArrayList<>();
    private AllUserAdapter userAdapter;
    private AllUserAdapter findUserAdapter;
    private FireBaseConnection fireBaseConnection=new FireBaseConnection();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users, container, false);

        Button findBox=view.findViewById(R.id.find);
        EditText findUser=view.findViewById(R.id.find_user);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userAdapter = new AllUserAdapter(getContext(), mUsers);
        findUserAdapter = new AllUserAdapter(getContext(),sortUser);
        ReadUsers();
        findBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String findU=findUser.getText().toString();
                if(findU.equals("")){
                    ReadUsers();
                }else {
                    FindUser(findU);
                }
            }
        });

        return view;
    }

    private void ReadUsers() {
        fireBaseConnection.getMyUsers().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    User user = it.getValue(User.class);
                    assert user != null;
                    if (!user.getId().equals(fireBaseConnection.getLoginUser().getUid())) {
                        mUsers.add(user);
                    }
                    recyclerView.setAdapter(userAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "The read failed: " + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FindUser(String findU){
        sortUser.clear();
        for(User it:mUsers){
            if(it.getUsername().contains(findU)){
                sortUser.add(it);
            }
        }
        recyclerView.setAdapter(findUserAdapter);
    }
}