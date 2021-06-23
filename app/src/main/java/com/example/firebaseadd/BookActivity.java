package com.example.firebaseadd;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.BreakIterator;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebaseadd.model.Book;
import com.example.firebaseadd.model.Chat;
import com.example.firebaseadd.utility.FireBaseConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookActivity extends AppCompatActivity {


    private final FireBaseConnection fireBaseConnection = new FireBaseConnection();
    private Intent intent;
    private String userBook;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        TextView textBook = findViewById(R.id.textBook);
        textBook.setMovementMethod(new ScrollingMovementMethod());
        intent = getIntent();
        userBook = intent.getStringExtra("useriq");
        fireBaseConnection.getBook().addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot it : snapshot.getChildren()) {
                    String nameBook = it.getValue().toString().trim().split("=")[0].replaceFirst("\\{","");
                    Map text= (HashMap) it.getValue();
                    if(nameBook.contains(userBook)){
                        String definition=text.get(nameBook).toString().trim();
                        TextView definitionView = (TextView) findViewById(R.id.textBook);
                        definitionView.setMovementMethod(LinkMovementMethod.getInstance());
                        definitionView.setText(definition, TextView.BufferType.SPANNABLE);
                        Spannable spans = (Spannable) definitionView.getText();
                        BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
                        iterator.setText(definition);
                        int start = iterator.first();
                        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator
                                .next()) {
                            String possibleWord = definition.substring(start, end);
                            if (Character.isLetterOrDigit(possibleWord.charAt(0))) {
                                ClickableSpan clickSpan = getClickableSpan(possibleWord);
                                spans.setSpan(clickSpan, start, end,
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

   private ClickableSpan getClickableSpan(final String word) {
        return new ClickableSpan() {
            final String mWord;
            {
                mWord = word;
            }

            @Override
            public void onClick(View widget) {
                Log.d("tapped on:", mWord);
                fireBaseConnection.getVocabulary().child(""+mWord.toUpperCase().charAt(0)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean findWordOrNot=false;
                        for(DataSnapshot it: snapshot.getChildren()){
                            String wordInEng= it.getKey().toLowerCase();
                            if(wordInEng.trim().equals(mWord.toLowerCase().trim())){
                                String wordInRu=it.getValue().toString();
                                Toast.makeText(widget.getContext(), wordInRu, Toast.LENGTH_SHORT)
                                        .show();
                                findWordOrNot=true;
                            }
                        }
                        if(!findWordOrNot){
                            Toast.makeText(widget.getContext(), "Этого слова нет в словаре", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
            }
        };
    }



    /*private void addWordInDataBase(){
        DatabaseReference refAddVocabulary = fireBaseConnection.getVocabulary().child(" Буква на которую добавлять");
            refAddVocabulary.child(engWord).setValue(it.replaceFirst(engWord,"").trim());
     }*/

}