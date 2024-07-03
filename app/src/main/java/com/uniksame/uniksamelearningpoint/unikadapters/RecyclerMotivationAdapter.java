package com.uniksame.uniksamelearningpoint.unikadapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.uniksame.uniksamelearningpoint.DetailTubeActivity;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ModelMotivation;
import com.uniksame.uniksamelearningpoint.unikservicesutils.UyouTubeApiConfig;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerMotivationAdapter extends FirebaseRecyclerAdapter<ModelMotivation,
        RecyclerView.ViewHolder> {

    private static final String DEVELOPER_KEY = "DEVELOPER_KEY";
    ArrayList<ModelMotivation> list;
    LayoutInflater mInflater;
    Context mContext;

    public RecyclerMotivationAdapter(@NonNull @NotNull FirebaseRecyclerOptions<ModelMotivation> options) {
        super(options);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        this.mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.recyclerview_motivation_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder,
                                    int position, @NonNull @NotNull ModelMotivation model) {

        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.you_h_title.setText(model.getmTitle());
        myViewHolder.video_duration_view.setText(model.getVideoDuration());

        myViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailTubeActivity.class);
                intent.putExtra("youTubeLink", model.getmLink());
                intent.putExtra("youTubeTitle", model.getmTitle());
                mContext.startActivity(intent);
            }
        });
        myViewHolder.youTubeThumbnailView.initialize(UyouTubeApiConfig.getApiKey(), new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(model.getmLink());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        Toast.makeText(mContext, "YouTube Thumbnail error, "+errorReason, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(mContext, "YouTube Initialization failure, "+youTubeInitializationResult, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView you_h_title, video_duration_view;
        YouTubeThumbnailView youTubeThumbnailView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            youTubeThumbnailView = itemView.findViewById(R.id.thumbnail_image_view);
            you_h_title = itemView.findViewById(R.id.you_h_title);
            video_duration_view = itemView.findViewById(R.id.video_duration_view);
            mView = itemView;
        }
    }

}


