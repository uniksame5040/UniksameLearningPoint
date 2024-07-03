package com.uniksame.uniksamelearningpoint.unikadapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.uniksame.uniksamelearningpoint.PagerDetailActivity;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikfragment.FragmentInterview;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ComputerShortsModel;

import org.jetbrains.annotations.NotNull;

public class ComputerDailyShortsAdapter extends FirebaseRecyclerAdapter<ComputerShortsModel, RecyclerView.ViewHolder> {
    private final int adapter_code;
    private Context context;
    private static final String DEVELOPER_KEY = "DEVELOPER_KEY";

    public ComputerDailyShortsAdapter(FirebaseRecyclerOptions<ComputerShortsModel> options, int adapter_code) {
        super(options);
        this.adapter_code = adapter_code;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (adapter_code == FragmentInterview.INTERVIEW_CODE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.interview_row, parent, false);
            context = parent.getContext();
            return new InterviewViewHolder(view);
        } else if (adapter_code == GkFragmentAdapter.GK_AF_CODE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.current_affairs_layout_row, parent, false);
            context = parent.getContext();
            return new CurrentAffairsHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.current_affairs_layout_row, parent, false);
            context = parent.getContext();
            return new CurrentAffairsHolder(view);
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position, @NonNull @NotNull ComputerShortsModel model) {
        if (holder.getClass() == InterviewViewHolder.class) {
            InterviewViewHolder interviewViewHolder = (InterviewViewHolder) holder;
            interviewViewHolder.inter_heading_text.setText(model.getCurrentTitle());
            interviewViewHolder.inter_desc_text.setText(model.getCurrentDesc() + "..Read more");

            if (model.getPageTipe().equals("video")){
                loadThumbnails(interviewViewHolder,model);
                interviewViewHolder.interview_image.setVisibility(View.GONE);
                interviewViewHolder.thumbnail_Interview_image.setVisibility(View.VISIBLE);
            }else {
                interviewViewHolder.interview_image.setVisibility(View.VISIBLE);
                Glide.with(context).load(model.getCurrentImgUrl()).into(interviewViewHolder.interview_image);
            }
//            Glide.with(context).load(model.getCurrentImgUrl()).into(interviewViewHolder.interview_image);
            interviewViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PagerDetailActivity.class);
                    intent.putExtra("intent_code", FragmentInterview.INTERVIEW_CODE);
                    intent.putExtra("position", holder.getAbsoluteAdapterPosition());
                    context.startActivity(intent);
                }
            });

        } else {
            CurrentAffairsHolder currentAffairsHolder = (CurrentAffairsHolder) holder;
            currentAffairsHolder.current_title_text.setText(model.getCurrentTitle());
            currentAffairsHolder.current_desc_text.setText(model.getCurrentDesc());

            if (model.getPageTipe().equals("video")){
                loadThumbnailsc(currentAffairsHolder,model);
                currentAffairsHolder.current_affairs_image.setVisibility(View.GONE);
                currentAffairsHolder.thumbnail_current_image.setVisibility(View.VISIBLE);
            }else {
                currentAffairsHolder.current_affairs_image.setVisibility(View.VISIBLE);
                Glide.with(context).load(model.getCurrentImgUrl()).into(currentAffairsHolder.current_affairs_image);
            }
//            Glide.with(context).load(model.getCurrentImgUrl()).into(currentAffairsHolder.current_affairs_image);
            currentAffairsHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PagerDetailActivity.class);
                    intent.putExtra("intent_code", RecyclerHomeAdapter.CURRENT_AF_CODE);
                    intent.putExtra("position", holder.getAbsoluteAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }
    private void loadThumbnails(InterviewViewHolder holder, ComputerShortsModel item) {
        holder.thumbnail_Interview_image.initialize(DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(item.getCurrentImgUrl());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        Toast.makeText(context, "YouTube Thumbnail error ! "+errorReason, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(context, "YouTube Initialization failure ! "+youTubeInitializationResult, Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void loadThumbnailsc(CurrentAffairsHolder holder, ComputerShortsModel item) {
        holder.thumbnail_current_image.initialize(DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(item.getCurrentImgUrl());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        Toast.makeText(context, "YouTube Thumbnail error ! "+errorReason, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(context, "YouTube Initialization failure ! "+youTubeInitializationResult, Toast.LENGTH_SHORT).show();
            }
        });

    }


    static class InterviewViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView inter_heading_text, inter_desc_text;
        ImageView interview_image;
        YouTubeThumbnailView thumbnail_Interview_image;

        public InterviewViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail_Interview_image =itemView.findViewById(R.id.thumbnail_cate_image);
            inter_heading_text = itemView.findViewById(R.id.inter_heading_text);
            inter_desc_text = itemView.findViewById(R.id.inter_desc_text);
            interview_image = itemView.findViewById(R.id.interview_image);
            mView = itemView;

        }
    }

    static class CurrentAffairsHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView current_title_text, current_desc_text;
        ImageView current_affairs_image;
        YouTubeThumbnailView thumbnail_current_image;

        public CurrentAffairsHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail_current_image = itemView.findViewById(R.id.thumbnail_current_image);
            current_title_text = itemView.findViewById(R.id.current_title_text);
            current_desc_text = itemView.findViewById(R.id.current_desc_text);
            current_affairs_image = itemView.findViewById(R.id.current_af_image);
            mView = itemView;

        }
    }
}



