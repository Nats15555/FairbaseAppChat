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

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.ViewHolder> {

    private Context context;
    private List<User> mUsers;

    public AllUserAdapter(Context context, List<User> mUsers) {
        this.context = context;
        this.mUsers = mUsers;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllUserAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.all_user_item, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = mUsers.get(position);
        holder.username.setText(user.getUsername());

        if (user.getImageUrl() == null) {
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context)
                    .load(user.getImageUrl())
                    .into(holder.imageView);
        }


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("useriq", user.getId());
                context.startActivity(i);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.user_card);
            imageView = itemView.findViewById(R.id.user_image);
        }
    }

}
