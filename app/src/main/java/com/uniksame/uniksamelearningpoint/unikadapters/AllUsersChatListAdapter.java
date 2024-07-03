package com.uniksame.uniksamelearningpoint.unikadapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.uniksame.uniksamelearningpoint.UpdateInfoNotification;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.UserChatList;

public class AllUsersChatListAdapter extends FirebaseRecyclerAdapter<UserChatList, AllUsersChatListAdapter.ViewHolder> {

    Context context;

    public AllUsersChatListAdapter(@NonNull FirebaseRecyclerOptions<UserChatList> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AllUsersChatListAdapter.ViewHolder holder, int position, @NonNull UserChatList userList) {

        if (userList.getUserImageUrl() != null && !userList.getUserImageUrl().equals("")) {
            Glide.with(context)
                    .load(userList.getUserImageUrl())
                    .into(holder.usersProfileImageView);
        } else {
            holder.usersProfileImageView.setImageResource(R.drawable.ic_baseline_person_24);
        }

//        holder.textUsernameViewList.setText("User id: "+userList.getUserName());
        holder.textNameViewList.setText("Name: "+userList.getUserFullName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendChatIntent = new Intent(context, UpdateInfoNotification.class);
                sendChatIntent.putExtra("user_id",userList.getUserName());
                sendChatIntent.putExtra("user_full_name",userList.getUserFullName());
                sendChatIntent.putExtra("chatId",userList.getUserChatId());
                context.startActivity(sendChatIntent);

            }
        });
    }

    @NonNull
    @Override
    public AllUsersChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_layout_row, parent, false);
        context = parent.getContext();
        return new AllUsersChatListAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate, textNameViewList, textUsernameViewList;
        ImageView usersProfileImageView;
        CardView cardView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNameViewList = itemView.findViewById(R.id.name_solution_view);
            usersProfileImageView = itemView.findViewById(R.id.mo_image);
            cardView = itemView.findViewById(R.id.card_all_users);
            textUsernameViewList = itemView.findViewById(R.id.username_solution_view);
            textViewDate = itemView.findViewById(R.id.solution_date);

        }
    }
}
