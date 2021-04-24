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

import java.util.List;

public class IgnorAdapter extends RecyclerView.Adapter<IgnorAdapter.ViewHolder> {
    private Context context;
    private List<User> mUsers;

    public IgnorAdapter(Context context, List<User> mUsers) {
        this.context = context;
        this.mUsers = mUsers;
    }


    @NonNull
    @Override
    public IgnorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IgnorAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull IgnorAdapter.ViewHolder holder, int position) {
        final User user = mUsers.get(position);
        holder.username.setText(user.getUsername());
        holder.imageView.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.user_card);
            imageView = itemView.findViewById(R.id.user_image);
        }
    }
}
