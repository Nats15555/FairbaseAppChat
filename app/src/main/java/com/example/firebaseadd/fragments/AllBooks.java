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
import android.widget.Toast;

import com.example.firebaseadd.R;
import com.example.firebaseadd.adapter.BookAdapter;
import com.example.firebaseadd.adapter.IgnoreAdapter;
import com.example.firebaseadd.model.Book;
import com.example.firebaseadd.model.User;
import com.example.firebaseadd.utility.FireBaseConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllBooks extends Fragment {

    private BookAdapter userAdapter;
    private List<Book> bookList = new ArrayList<>();
    private FireBaseConnection fireBaseConnection=new FireBaseConnection();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blacklist,
                container, false);

        recyclerView = view.findViewById(R.id.recycler_view4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new BookAdapter(getContext(), bookList);
        fireBaseConnection.getBook().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList.clear();
                for (DataSnapshot it : snapshot.getChildren()) {
                    String nameBook = it.getValue().toString().trim().split("=")[0].replaceFirst("\\{","");
                    Map text= (HashMap) it.getValue();
                    //it.getKey(), nameBook, text.get(nameBook).toString()
                    bookList.add(new Book(it.getKey(),nameBook,""));
                    System.out.println(text.get(nameBook).toString().length());
                }
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "The read failed: " + error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    
}