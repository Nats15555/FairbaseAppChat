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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseadd.MessageActivity;
import com.example.firebaseadd.model.User;
import com.example.firebaseadd.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MassageUserAdapter extends RecyclerView.Adapter<MassageUserAdapter.ViewHolder> {

    private Context context;
    private List<User> mUsers;

    public MassageUserAdapter(Context context, List<User> mUsers) {
        this.context = context;
        this.mUsers = mUsers;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MassageUserAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item
                , parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = mUsers.get(position);
        holder.username.setText(user.getUsername());

        holder.imageView.setImageResource(R.mipmap.ic_launcher);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("useriq", user.getId());
                context.startActivity(i);
            }
        });

        holder.dell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mUsers.size());
                FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase.getInstance().getReference("ChatList")
                        .child(fuser.getUid())
                        .child(user.getId())
                        .removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView imageView;
        public Button dell;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.book_card);
            imageView = itemView.findViewById(R.id.book_image);
            dell = itemView.findViewById(R.id.add);
        }
    }

}
