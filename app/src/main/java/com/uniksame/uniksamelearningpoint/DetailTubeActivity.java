package com.uniksame.uniksamelearningpoint;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.uniksame.uniksamelearningpoint.unikadapters.ComputerDailyShortsAdapter;
import com.uniksame.uniksamelearningpoint.unikservicesutils.UyouTubeApiConfig;

public class DetailTubeActivity extends YouTubeBaseActivity {
    private String urlId;
    private TextView youtube_heading_text_view;
    private ViewPager2 viewPager2;
    ComputerDailyShortsAdapter adapter;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private ShimmerFrameLayout shimmerFrameLayout;
    private String youTubeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tube_detail);
        urlId = getIntent().getStringExtra("youTubeLink");
        youTubeTitle = getIntent().getStringExtra("youTubeTitle");
        intUiForYoutube();
    }

    private void intUiForYoutube() {
        youtube_heading_text_view = findViewById(R.id.youtube_heading_text_view);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_video);

        LinearLayout layout = findViewById(R.id.tube_page);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
//        youTubePlayerView.initialize(
//                initializedYouTubePlayer -> initializedYouTubePlayer.addListener(
//                        new AbstractYouTubePlayerListener() {
//                            @Override
//                            public void onReady() {
//                                youtube_heading_text_view.setText(urlId);
//                                initializedYouTubePlayer.loadVideo(urlId, 0);
//                            }
//                        }), true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayerView.initialize(UyouTubeApiConfig.getApiKey(), onInitializedListener);
                        youTubePlayer.loadVideo(urlId);
                        youtube_heading_text_view.setText(youTubeTitle);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);
                        Toast.makeText(DetailTubeActivity.this, "Video Loading !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(DetailTubeActivity.this, "Video Loading Failed !", Toast.LENGTH_SHORT).show();
                    }
                };

                youTubePlayerView.initialize(UyouTubeApiConfig.getApiKey(), onInitializedListener);

            }

        }, 2000);
        }
}
