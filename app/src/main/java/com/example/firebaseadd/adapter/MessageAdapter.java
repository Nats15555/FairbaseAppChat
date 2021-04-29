package com.example.firebaseadd.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseadd.model.Chat;
import com.example.firebaseadd.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private List<Chat> mChat;
    private String imgURL;

    private FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    public MessageAdapter(Context context, List<Chat> mChat, String imgURL) {
        this.context = context;
        this.mChat = mChat;
        this.imgURL = imgURL;
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item_right,
                    parent,
                    false));
        } else {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_item_left,
                    parent,
                    false));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = mChat.get(position);
        holder.showMassage.setText(chat.getMessage());

        holder.imageProf.setImageResource(R.mipmap.ic_launcher);

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView showMassage;
        public ImageView imageProf;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            showMassage = itemView.findViewById(R.id.show_massage);
            imageProf = itemView.findViewById(R.id.imageview_profile);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mChat.get(position).getSender().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
