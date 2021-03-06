package com.example.firebaseadd.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseadd.BookActivity;
import com.example.firebaseadd.MessageActivity;
import com.example.firebaseadd.R;
import com.example.firebaseadd.model.Book;
import com.example.firebaseadd.utility.FireBaseConnection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context context;
    private List<Book> bookList;
    private FireBaseConnection fireBaseConnection = new FireBaseConnection();

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }


    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.book_item
                , parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        final Book book = bookList.get(position);
        holder.nameBook.setText(book.getName());
        holder.authorBook.setText(book.getAuthor());

        holder.imageView.setImageResource(R.mipmap.ic_launcher);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, BookActivity.class);
                i.putExtra("useriq", book.getName());//???????????????????? ???????????? ?????????? ????????????????????????
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameBook;
        public TextView authorBook;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameBook = itemView.findViewById(R.id.book_card);
            authorBook = itemView.findViewById(R.id.author_card);
            imageView = itemView.findViewById(R.id.book_image);
        }
    }

    private void addBook() {

    }

}
