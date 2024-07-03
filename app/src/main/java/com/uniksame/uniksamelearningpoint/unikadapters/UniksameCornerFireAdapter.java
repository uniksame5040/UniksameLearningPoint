package com.uniksame.uniksamelearningpoint.unikadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.CornerHelper;

public class UniksameCornerFireAdapter extends FirebaseRecyclerAdapter<CornerHelper, UniksameCornerFireAdapter.ViewHolder> {

    Context context;
    public UniksameCornerFireAdapter(@NonNull FirebaseRecyclerOptions<CornerHelper> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UniksameCornerFireAdapter.ViewHolder holder, int position, @NonNull CornerHelper model) {

//        holder.textViewSolution.setText(model.getCornerImageUrls());

    }

    @NonNull
    @Override
    public UniksameCornerFireAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.corner_layout_row, parent, false);
        context = parent.getContext();
        return new UniksameCornerFireAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewSolution, likeTextView, dislikeTextView, usernameSolutionView;
        ImageView likeImageView, dislikeImageView, userSolutionProfileImageView;
        LinearLayout likeLinearLayout, dislikeLinearLayout;
        CardView cardViewSolutionHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            userSolutionProfileImageView = itemView.findViewById(R.id.mo_image);
//            cardViewSolutionHolder = itemView.findViewById(R.id.pro_card_holder);
//            likeLinearLayout = itemView.findViewById(R.id.like_Linear_l);
//            dislikeLinearLayout = itemView.findViewById(R.id.dislike_linear_d);
//            usernameSolutionView = itemView.findViewById(R.id.username_solution_view);
//            textViewDate = itemView.findViewById(R.id.solution_date);
            textViewSolution = itemView.findViewById(R.id.solution_text);
//            likeTextView = itemView.findViewById(R.id.like_text_view);
//            dislikeTextView = itemView.findViewById(R.id.dislike_text_view);
//            likeImageView = itemView.findViewById(R.id.like_image_view);
//            dislikeImageView = itemView.findViewById(R.id.dislike_image_view);
        }
    }
}
