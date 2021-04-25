package com.example.firebaseadd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebaseadd.MessageActivity;
import com.example.firebaseadd.Model.User;
import com.example.firebaseadd.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FriendsAdapter extends UserAdapter {
    private Context context;
    private List<User> mUsers;

    public FriendsAdapter(Context context, List<User> mUsers) {
        super(context, mUsers);
        this.context = context;
        this.mUsers = mUsers;
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
                FirebaseDatabase.getInstance().getReference("Friends")
                        .child(fuser.getUid())
                        .child(user.getId())
                        .removeValue();
            }
        });
    }
}
