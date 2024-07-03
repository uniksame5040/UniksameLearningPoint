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
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.uniksame.uniksamelearningpoint.PagerDetailActivity;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ComputerShortsModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EducationCategoryAdapter extends RecyclerView.Adapter<EducationCategoryAdapter.ViewHolder> {
    private static final int POSITION_ID = 0;
    private static final int AD_SHOW_POSITION = 101;
    private ArrayList<ComputerShortsModel> list;
    private LayoutInflater mInflater;
    private static final String DEVELOPER_KEY = "DEVELOPER_KEY";
    private Context mContext;
    public static final int PAGE_DETAIL_CODE = 567;
    private String pageType;

    public EducationCategoryAdapter(Context context, ArrayList<ComputerShortsModel> list, String pageType) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.list = list;
        this.pageType = pageType;
    }

    @NonNull
    @Override
    public EducationCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.interview_row, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ComputerShortsModel item = list.get(holder.getAbsoluteAdapterPosition());
//        CurrentAffairsHolder currentAffairsHolder = (CurrentAffairsHolder) holder;
        holder.inter_heading_text.setText(item.getCurrentTitle());
        holder.inter_desc_text.setText(item.getCurrentDesc());

        if (item.getPageTipe().equals("video")){
            loadThumbnails(holder,item);
            holder.interview_image.setVisibility(View.GONE);
            holder.youTubeThumbnailView.setVisibility(View.VISIBLE);

        }else {
            holder.youTubeThumbnailView.setVisibility(View.GONE);
            holder.interview_image.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getCurrentImgUrl()).into(holder.interview_image);
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PagerDetailActivity.class);
                intent.putExtra("intent_code", pageType);
                intent.putExtra("pageCode", PAGE_DETAIL_CODE);
                intent.putExtra("position", holder.getAbsoluteAdapterPosition());
                mContext.startActivity(intent);
            }
        });
    }

    private void loadThumbnails(ViewHolder holder, ComputerShortsModel item) {
        holder.youTubeThumbnailView.initialize(DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        View mView;
        TextView inter_heading_text, inter_desc_text;
        ImageView interview_image;
        YouTubeThumbnailView youTubeThumbnailView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            youTubeThumbnailView = itemView.findViewById(R.id.thumbnail_cate_image);
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

        public CurrentAffairsHolder(@NonNull View itemView) {
            super(itemView);
            current_title_text = itemView.findViewById(R.id.current_title_text);
            current_desc_text = itemView.findViewById(R.id.current_desc_text);
            current_affairs_image = itemView.findViewById(R.id.current_af_image);
            mView = itemView;

        }
    }

}
