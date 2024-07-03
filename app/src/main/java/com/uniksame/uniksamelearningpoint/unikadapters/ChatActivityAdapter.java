package com.uniksame.uniksamelearningpoint.unikadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ChatModelHelper;
import com.uniksame.uniksamelearningpoint.unikservicesutils.PreferenceUtils;

public class ChatActivityAdapter extends FirebaseRecyclerAdapter<ChatModelHelper, RecyclerView.ViewHolder> {

    private static final int ITEM_RECIEVED = 1;
    private static final int ITEM_SEND = 2;
    private final Context context;
    private FirebaseRecyclerOptions<ChatModelHelper> options;

    public ChatActivityAdapter(@NonNull FirebaseRecyclerOptions<ChatModelHelper> options, Context context) {
        super(options);
        this.context = context;
        this.options = options;
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull ChatModelHelper chatModelHelper) {

        if (holder.getClass() == SenderViewHolder.class) {

            SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
            senderViewHolder.chatTextView.setText(chatModelHelper.getChatText());
        } else {
            ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) holder;
            receiverViewHolder.chatTextView.setText(chatModelHelper.getChatText());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM_SEND) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_sender_layout, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_recieve_layout, parent, false);
            return new ReceiverViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        ChatModelHelper chatModelHelper = options.getSnapshots().get(position);
        if (chatModelHelper.getChatSenderId().equals(PreferenceUtils.getUsernamePref(context))) {
            return ITEM_SEND;
        } else {
            return ITEM_RECIEVED;
        }
    }

    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView chatTextView;
        ImageView usersProfileImageView;
        CardView cardView;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            chatTextView = itemView.findViewById(R.id.chat_text_view);

        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView chatTextView;
        ImageView usersProfileImageView;
        CardView cardView;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            chatTextView = itemView.findViewById(R.id.chat_text_view);


        }
    }
}