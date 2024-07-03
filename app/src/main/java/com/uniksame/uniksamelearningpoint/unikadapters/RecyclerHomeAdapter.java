package com.uniksame.uniksamelearningpoint.unikadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ComputerShortsModel;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ModelSoluttion;
import com.uniksame.uniksamelearningpoint.unikservicesutils.PreferenceUtils;
import com.uniksame.uniksamelearningpoint.unikservicesutils.UnikConstants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecyclerHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int DASHBOARD_ID = 0;
    private static final int RECENT_ID = 1;
    public static final int CURRENT_AF_CODE = 101;
    public static final int TH_CODE_ONE = 22;
    ArrayList<Integer> list;
    LayoutInflater mInflater;
    Context mContext;
    ThoughtShareFirebaseAdapter adapter;

    public RecyclerHomeAdapter(Context context, ArrayList<Integer> list) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(view,mClickListener);
        if (viewType == DASHBOARD_ID) {
            View view = mInflater.inflate(R.layout.dashboard_row, parent, false);
            return new DashBoardViewHolder(view);
        } else if (viewType == RECENT_ID) {
            View view = mInflater.inflate(R.layout.common_recycler_view, parent, false);
            return new RecentTopicHolder(view);
        } else if (viewType == 2) {
            View view = mInflater.inflate(R.layout.common_recycler_view, parent, false);
            return new ComputerDailyShortsViewHolder(view);
        } else if (viewType == 3) {
            View view = mInflater.inflate(R.layout.corner_image_slider, parent, false);
            return new CornerViewHolder(view);
        } else if (viewType == 4) {
            View view = mInflater.inflate(R.layout.uniksame_ads_layout, parent, false);
            return new UniksameAdsHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.common_recycler_view, parent, false);
            return new PostHolder(view);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        adapter.stopListening();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getClass() == DashBoardViewHolder.class) {

            FirebaseDatabase rootNode;
            DatabaseReference reference;
            DashBoardViewHolder dashBoardViewHolder = (DashBoardViewHolder) holder;

            dashBoardViewHolder.progress_post.setProgress(PreferenceUtils.getPostProgressPref(mContext));
            dashBoardViewHolder.progress_all.setProgress(PreferenceUtils.getProgressPref(mContext));
            dashBoardViewHolder.dashBoardTitleView.setVisibility(View.VISIBLE);
            dashBoardViewHolder.dashBoardTitleView.setText("Dashboard ");
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("UniksameHost");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        dashBoardViewHolder.cotesTextView.setText(Objects.requireNonNull(dataSnapshot.child("MotivationalCotes")
                                .child("cotes").getValue()).toString());
                    }
                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {

                }
            });
            reference = rootNode.getReference("UniksameUsers");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        dashBoardViewHolder.erarningTextView.setText(Objects.requireNonNull(dataSnapshot.child(PreferenceUtils.getUsernamePref(mContext)).child("work")
                                .child("earning").getValue()).toString());

                        String todayProgress = Objects.requireNonNull(dataSnapshot.child(PreferenceUtils.getUsernamePref(mContext))
                                .child("work")
                                .child("refers").getValue()).toString();

                        int totalProgress = Integer.parseInt(todayProgress);
                        dashBoardViewHolder.noOfApproachText.setText(totalProgress + "");
                        dashBoardViewHolder.todayTextViewPercent.setText("Today:" + totalProgress + "%");
                        dashBoardViewHolder.progressBarToday.setProgress(totalProgress);
                    }
                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {
                    Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (holder.getClass() == RecentTopicHolder.class) {
            RecentTopicHolder recentTopicHolder = (RecentTopicHolder) holder;
            FirebaseRecyclerOptions<ComputerShortsModel> crrentEducationAdapter =
                    new FirebaseRecyclerOptions.Builder<ComputerShortsModel>().setQuery(FirebaseDatabase
                                    .getInstance().getReference().child("UniksameHost").child("FreeCourse").child("AllCourses").limitToLast(3),
                            ComputerShortsModel.class).build();
            RecyclerEducationTopicAdapter recyclerEducationTopicAdapter = new RecyclerEducationTopicAdapter(crrentEducationAdapter,"Recently added courses");
            recentTopicHolder.recentTopicRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recentTopicHolder.recentTopicRecyclerView.setAdapter(recyclerEducationTopicAdapter);
            recyclerEducationTopicAdapter.startListening();

        } else if (holder.getClass() == CornerViewHolder.class) {
            CornerViewHolder cornerViewHolder = (CornerViewHolder) holder;
            cornerViewHolder.uniksameCornerView.setVisibility(View.VISIBLE);
            cornerViewHolder.uniksameCornerView.setText("Corner");

            List<SlideModel> slideModelList = new ArrayList<>();
            slideModelList.add(new SlideModel(UnikConstants.PERSONALITY, "Personality Development", ScaleTypes.CENTER_CROP));
            slideModelList.add(new SlideModel(UnikConstants.INTERVIEW_TIPS, "Interview Tips", ScaleTypes.CENTER_CROP));
            slideModelList.add(new SlideModel(UnikConstants.CURRENT_AF, "Current Affairs", ScaleTypes.CENTER_CROP));
            slideModelList.add(new SlideModel(UnikConstants.TECHNOLOGY, "Technology and science", ScaleTypes.CENTER_CROP));
            slideModelList.add(new SlideModel(UnikConstants.FACTS, "All Facts", ScaleTypes.CENTER_CROP));
            slideModelList.add(new SlideModel(UnikConstants.REAL_MOTIVATION, "Real Motivation", ScaleTypes.CENTER_CROP));
            cornerViewHolder.cornerImageSlider.setImageList(slideModelList, ScaleTypes.CENTER_CROP);

        } else if (holder.getClass() == ComputerDailyShortsViewHolder.class) {
            ComputerDailyShortsViewHolder computerDailyShortsViewHolder = (ComputerDailyShortsViewHolder) holder;

            computerDailyShortsViewHolder.dashboard_image_view.setVisibility(View.VISIBLE);
            computerDailyShortsViewHolder.dashboard_image_view.setImageResource(R.drawable.ic_baseline_model_training_24);
            computerDailyShortsViewHolder.dashboard_title_view.setVisibility(View.VISIBLE);
            computerDailyShortsViewHolder.dashboard_title_view.setText("Computer Daily shorts");

            FirebaseRecyclerOptions<ComputerShortsModel> currentAfInterModelOptions =
                    new FirebaseRecyclerOptions.Builder<ComputerShortsModel>().setQuery(FirebaseDatabase
                            .getInstance().getReference().child("UniksameHost").child("ComputerDailyShorts").limitToFirst(10), ComputerShortsModel.class).build();
            ComputerDailyShortsAdapter adapter = new ComputerDailyShortsAdapter(currentAfInterModelOptions, CURRENT_AF_CODE);
            adapter.startListening();
            LinearLayoutManager linearLayoutManager = new
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            computerDailyShortsViewHolder.recyclerView.setLayoutManager(linearLayoutManager);
            computerDailyShortsViewHolder.recyclerView.setAdapter(adapter);

//            currentAIAdapter = new CurrentAffairsInterviewAdapter(mContext, list, ONE_ZERO_ONE);
        } else if (holder.getClass() == UniksameAdsHolder.class) {

            UniksameAdsHolder uniksameAdsHolder = (UniksameAdsHolder) holder;
//            CardView adContainer = uniksameAdsHolder.cardAdView;
            AdView mAdView = new AdView(mContext);
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
            uniksameAdsHolder.cardAdView.addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        } else {
            PostHolder postHolder = (PostHolder) holder;
            FirebaseRecyclerOptions<ModelSoluttion> modelSolutionOptions =
                    new FirebaseRecyclerOptions.Builder<ModelSoluttion>().setQuery(FirebaseDatabase
                            .getInstance().getReference().child("UserPosts").limitToLast(5), ModelSoluttion.class).build();

            adapter = new ThoughtShareFirebaseAdapter(modelSolutionOptions,TH_CODE_ONE);
            adapter.startListening();
            postHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            postHolder.recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return position;
        } else if (position == 1) {
            return position;
        } else if (position == 2) {
            return position;
        } else if (position == 3) {
            return position;
        } else if (position == 4) {
            return position;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecentTopicHolder extends RecyclerView.ViewHolder {
        View mView;
        RecyclerView recentTopicRecyclerView;

        public RecentTopicHolder(@NonNull View itemView) {
            super(itemView);
            recentTopicRecyclerView = itemView.findViewById(R.id.recycler_top_of_day);
            mView = itemView;

        }
    }

    public static class DashBoardViewHolder extends RecyclerView.ViewHolder {
        TextView dashBoardTitleView;
        ImageView usersProfileImageView;
        CardView cardView;
        RecyclerView recyclerView;
        TextView cotesTextView;
        TextView erarningTextView;
        TextView noOfApproachText;
        TextView todayTextViewPercent;
        ProgressBar progressBarToday, progress_all,progress_post;

        public DashBoardViewHolder(View view) {
            super(view);
            todayTextViewPercent = view.findViewById(R.id.today_text_percent);
            noOfApproachText = view.findViewById(R.id.no_of_approach_view);
            erarningTextView = view.findViewById(R.id.earning_view);
            cotesTextView = view.findViewById(R.id.cotes_view);
            recyclerView = view.findViewById(R.id.recycler_top_of_day);
            dashBoardTitleView = view.findViewById(R.id.dashboard_title_view);
            progressBarToday = view.findViewById(R.id.progcircle_bar);
            progress_all = view.findViewById(R.id.progress_all);
            progress_post = view.findViewById(R.id.progress_post);
        }
    }

    public static class CornerViewHolder extends RecyclerView.ViewHolder {
        TextView uniksameCornerView;
        ImageView usersProfileImageView;
        CardView cardView;
        RecyclerView recyclerView;
        ImageSlider cornerImageSlider;

        public CornerViewHolder(View view) {
            super(view);
//            recyclerView = view.findViewById(R.id.recycler_top_of_day);
            uniksameCornerView = view.findViewById(R.id.uniksame_corner_view);
            cornerImageSlider = view.findViewById(R.id.corner_image_slider_view);
        }
    }

    public static class ComputerDailyShortsViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        ImageView dashboard_image_view;
        TextView dashboard_title_view;

        public ComputerDailyShortsViewHolder(View view) {
            super(view);
            recyclerView = view.findViewById(R.id.recycler_top_of_day);
            dashboard_image_view = view.findViewById(R.id.dashboard_image_view);
            dashboard_title_view = view.findViewById(R.id.dashboard_title_view);
        }
    }

    public static class PostHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public PostHolder(View view) {
            super(view);
            recyclerView = itemView.findViewById(R.id.recycler_top_of_day);
        }
    }

    public static class UniksameAdsHolder extends RecyclerView.ViewHolder {
        CardView cardAdView;

        public UniksameAdsHolder(View view) {
            super(view);
            cardAdView = itemView.findViewById(R.id.ads_card_view);
        }
    }

}
