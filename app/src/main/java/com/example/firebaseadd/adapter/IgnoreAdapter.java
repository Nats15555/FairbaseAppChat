package com.example.firebaseadd.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.firebaseadd.model.User;
import com.example.firebaseadd.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class IgnoreAdapter extends UserAdapter {
    private Context context;
    private List<User> mUsers;

    public IgnoreAdapter(Context context, List<User> mUsers) {
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

        holder.dell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mUsers.size());
                FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase.getInstance().getReference("Ignore")
                        .child(fuser.getUid())
                        .child(user.getId())
                        .removeValue();
            }
        });
    }
}
