package com.example.socify.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socify.Activities.ChatRoom;
import com.example.socify.Activities.Home;
import com.example.socify.Classes.ChatListUser;
import com.example.socify.Classes.Person;
import com.example.socify.R;
import com.example.socify.databinding.ChatlistlayoutBinding;
import com.google.android.exoplayer2.C;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {

    Context context;
    ArrayList<ChatListUser> chatListUsers;

    public ChatListAdapter(Context context, ArrayList<ChatListUser> chatListUsers) {
        this.context = context;
        this.chatListUsers = chatListUsers;
    }


    @NonNull
    @Override
    public ChatListAdapter.ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatlistlayout, parent, false);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ChatListViewHolder holder, int position) {
        ChatListUser chatListUser = chatListUsers.get(position);

        FirebaseDatabase.getInstance().getReference("College")
                        .child(Home.getUserData.college_name)
                                .child("Chats")
                                        .child(Home.getUserData.uid)
                                            .child(chatListUser.getUid())
                                                .addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.exists()) {
                                                            String lastMsg = snapshot.child("lastMsg").getValue(String.class);
                                                            long time = snapshot.child("lastMsgTime").getValue(Long.class);
                                                            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");

                                                            holder.binding.lastmsg.setText(lastMsg);
                                                            holder.binding.lasttime.setText(dateFormat.format(new Date(time)));
                                                        }
                                                        else {
                                                            holder.binding.lastmsg.setText("Tap to chat");
                                                        }
//                                                        notifyItemChanged(holder.getAdapterPosition());
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

        holder.binding.chatname.setText(chatListUser.getName());
        Glide.with(context).load(chatListUser.getImgUri()).placeholder(R.drawable.user).into(holder.binding.chatprofileimg);

        holder.binding.listchat.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatRoom.class);
            intent.putExtra("Name", chatListUser.getName());
            intent.putExtra("Img", chatListUser.getImgUri());
            intent.putExtra("UID", chatListUser.getUid());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return chatListUsers.size();
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder {

        ChatlistlayoutBinding binding;

        public ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ChatlistlayoutBinding.bind(itemView);
        }
    }
}
