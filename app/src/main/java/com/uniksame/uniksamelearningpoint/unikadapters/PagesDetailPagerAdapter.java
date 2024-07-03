package com.uniksame.uniksamelearningpoint.unikadapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import com.uniksame.uniksamelearningpoint.BuildConfig;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ComputerShortsModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PagesDetailPagerAdapter extends RecyclerView.Adapter<PagesDetailPagerAdapter.InterviewViewHolder> {
    private Context context;
    private String pageType;
    private final int pageCode;
    private final ArrayList<ComputerShortsModel> list;

    public PagesDetailPagerAdapter(ArrayList<ComputerShortsModel> list, Context context, int pageCode) {
        this.list = list;
        this.context = context;
        this.pageCode = pageCode;
    }

    @NotNull
    @Override
    public PagesDetailPagerAdapter.InterviewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.short_interview_row, parent, false);
        return new InterviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InterviewViewHolder holder, int position) {
        ComputerShortsModel computerShortsModel = list.get(position);
        holder.textViewHeading.setText(computerShortsModel.getCurrentTitle());
        holder.textViewDesc.setText(computerShortsModel.getCurrentDesc());
        holder.textViewPageType.setText(computerShortsModel.getPageTipe());

        if (computerShortsModel.getPageTipe().equals("video")) {
            holder.card_tda.setVisibility(View.GONE);
            holder.interview_image.setVisibility(View.GONE);
            holder.youTubePlayerView.setVisibility(View.VISIBLE);
            holder.youTubePlayerView.initialize(
                    initializedYouTubePlayer -> initializedYouTubePlayer.addListener(
                            new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady() {
                                    initializedYouTubePlayer.cueVideo(computerShortsModel.getCurrentImgUrl(), 0);
                                }
                            }), true);
        } else {
            holder.card_tda.setVisibility(View.VISIBLE);
            holder.youTubePlayerView.setVisibility(View.GONE);
            holder.interview_image.setVisibility(View.VISIBLE);
            Glide.with(context).load(computerShortsModel.getCurrentImgUrl()).into(holder.interview_image);
        }

        holder.progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Swipe to get mor knowledge !", Toast.LENGTH_SHORT).show();
            }
        });

        position = position + 1;
        holder.today_text_percent.setText(position + "/" + list.size());
        holder.progressBar.setProgress((int) (100.0 * position / list.size()));

        if (pageCode != EducationCategoryAdapter.PAGE_DETAIL_CODE) {
            holder.share_to_other_app_image.setOnClickListener(new View.OnClickListener() {
                private long TIME_OUT = 200;

                @Override
                public void onClick(View view) {
                    if (!computerShortsModel.getPageTipe().equals("video")){
                        holder.textViewPageType.setVisibility(View.GONE);
                        holder.uns_interview_logo.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                shareImageIntent();
                            }
                        }, TIME_OUT);
                    }else {
                        Toast.makeText(context, "Share only image stories !", Toast.LENGTH_SHORT).show();
                    }

                }

                private void shareImageIntent() {
                    Bitmap bitmap = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        bitmap = Bitmap.createBitmap(holder.relative_layout_convert_image
                                .getWidth(), holder.relative_layout_convert_image.getHeight(), Bitmap.Config.ARGB_8888);
                    }
                    Canvas canvas = new Canvas(bitmap);
                    holder.relative_layout_convert_image.draw(canvas);
                    try {
                        File file = new File(context.getExternalCacheDir(), File.separator + "getty.jpg");
                        FileOutputStream fOut = new FileOutputStream(file);
                        if (bitmap != null) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                        }
                        fOut.flush();
                        fOut.close();
//                        file.setReadable(true, false);
                        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = null;
                        uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);

                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setType("image/jpg");

                        context.startActivity(Intent.createChooser(intent, "Share image via"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            holder.share_to_other_app_image.setImageResource(R.drawable.ic_baseline_swipe_24);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class InterviewViewHolder extends RecyclerView.ViewHolder {
        View mView;
        RelativeLayout relative_layout_convert_image;
        YouTubePlayerView youTubePlayerView;
        ImageView interview_image;
        ImageView share_to_other_app_image;
        ImageView uns_interview_logo;
        TextView textViewHeading;
        TextView textViewDesc;
        TextView textViewPageType;
        TextView today_text_percent;
        ProgressBar progressBar;
        CardView card_tda;

        public InterviewViewHolder(@NonNull View itemView) {
            super(itemView);
            card_tda = itemView.findViewById(R.id.card_tda);
            relative_layout_convert_image = itemView.findViewById(R.id.relative_layout_convert_image);
            youTubePlayerView = itemView.findViewById(R.id.youtube_view);
            interview_image = itemView.findViewById(R.id.interview_image);
            share_to_other_app_image = itemView.findViewById(R.id.share_to_other_app_image);
            uns_interview_logo = itemView.findViewById(R.id.uns_interview_logo);
            textViewHeading = itemView.findViewById(R.id.heading);
            textViewDesc = itemView.findViewById(R.id.desc_text_view);
            textViewPageType = itemView.findViewById(R.id.page_type_view);
            today_text_percent = itemView.findViewById(R.id.today_text_percent);
            progressBar = itemView.findViewById(R.id.progcircle_bar);
            mView = itemView;
        }
    }
}
