package com.uniksame.uniksamelearningpoint.unikadapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.FirebaseDatabase;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ComputerShortsModel;
import com.uniksame.uniksamelearningpoint.unikservicesutils.UnikConstants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GkFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int POSITION_ID = 0;
    public static final int GK_AF_CODE = 404;
    ArrayList<Integer> list;
    LayoutInflater mInflater;
    Context mContext;

    public GkFragmentAdapter(Context context, ArrayList<Integer> list) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.recyclerview_education_row, parent, false);

        if (viewType == POSITION_ID) {
            View view = mInflater.inflate(R.layout.common_recycler_view, parent, false);
            return new GeneralKnowledgeHolder(view);
        } else if (viewType == 1) {
            View view = mInflater.inflate(R.layout.corner_image_slider, parent, false);
            return new UpCoursesViewHolder(view);
        } else if (viewType == 2) {
            View view = mInflater.inflate(R.layout.uniksame_card, parent, false);
            return new UniksameCardHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.uniksame_ads_layout, parent, false);
            return new UniksameAdsHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
//        ModelEducation item = list.get(position);

        if (holder.getClass() == UniksameAdsHolder.class) {
            UniksameAdsHolder uniksameAdsHolder = (UniksameAdsHolder) holder;
//            CardView adContainer = uniksameAdsHolder.cardAdView;
            AdView mAdView = new AdView(mContext);
            mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
            mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
            uniksameAdsHolder.cardAdView.addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } else if (holder.getClass() == GeneralKnowledgeHolder.class) {
            GeneralKnowledgeHolder generalKnowledgeHolder = (GeneralKnowledgeHolder) holder;
            generalKnowledgeHolder.title_image.setVisibility(View.VISIBLE);
            generalKnowledgeHolder.title_text.setVisibility(View.VISIBLE);
            generalKnowledgeHolder.title_text.setText("General knowledge");
            generalKnowledgeHolder.title_image.setImageResource(R.drawable.ic_baseline_model_training_24);

            FirebaseRecyclerOptions<ComputerShortsModel> currentAfInterModelOptions =
                    new FirebaseRecyclerOptions.Builder<ComputerShortsModel>().setQuery(FirebaseDatabase
                            .getInstance().getReference().child("UniksameHost").child("CurrentAffairs").limitToFirst(10), ComputerShortsModel.class).build();
            ComputerDailyShortsAdapter adapter = new ComputerDailyShortsAdapter(currentAfInterModelOptions, GK_AF_CODE);
            adapter.startListening();
            LinearLayoutManager linearLayoutManager = new
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            generalKnowledgeHolder.recyclerView.setLayoutManager(linearLayoutManager);
            generalKnowledgeHolder.recyclerView.setAdapter(adapter);

        } else if (holder.getClass() == UniksameCardHolder.class) {

            UniksameCardHolder uniksameCardHolder = (UniksameCardHolder) holder;
            Glide.with(mContext).load(UnikConstants.UNIKSAME_CARD_URL).into(uniksameCardHolder.unik_card_imageview);
        } else {
            UpCoursesViewHolder upCoursesViewHolder = (UpCoursesViewHolder) holder;
//            upCoursesViewHolder.uniksameCornerView.setVisibility(View.VISIBLE);
            upCoursesViewHolder.uniksameCoursesView.setTextColor(Color.BLACK);
            upCoursesViewHolder.uniksameCoursesView.setText("Courses Coming soon ...");

            List<SlideModel> slideModelList = new ArrayList<>();
            slideModelList.add(new SlideModel(UnikConstants.DIGITAL_MARKETING, "digital marketing", ScaleTypes.CENTER_CROP));
            slideModelList.add(new SlideModel(UnikConstants.WEBSITE_DESIGN, "Website design", ScaleTypes.CENTER_CROP));
            slideModelList.add(new SlideModel(UnikConstants.ANDROID_DEVELOP, "Android development", ScaleTypes.CENTER_CROP));
            slideModelList.add(new SlideModel(UnikConstants.SERVER_W, "Server work", ScaleTypes.CENTER_CROP));
            slideModelList.add(new SlideModel(UnikConstants.DATABASE, "Database", ScaleTypes.CENTER_CROP));
            slideModelList.add(new SlideModel(UnikConstants.PROGRAMING, "Programming", ScaleTypes.CENTER_CROP));
            slideModelList.add(new SlideModel(UnikConstants.COMPUTER_C, "Computer courses", ScaleTypes.CENTER_CROP));
            upCoursesViewHolder.cornerImageSlider.setImageList(slideModelList, ScaleTypes.CENTER_CROP);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return position;
        } else if (position == 1) {
            return position;
        } else if (position == 2) {
            return position;
        } else {
            return -1;
        }
    }

    public static class UniksameAdsHolder extends RecyclerView.ViewHolder {
        CardView cardAdView;

        public UniksameAdsHolder(View view) {
            super(view);
            cardAdView = itemView.findViewById(R.id.ads_card_view);
        }
    }

    public static class GeneralKnowledgeHolder extends RecyclerView.ViewHolder {

        View mView;
        ImageView title_image;
        TextView textView, title_text;
        RecyclerView recyclerView;

        public GeneralKnowledgeHolder(@NonNull View itemView) {
            super(itemView);

            title_image = itemView.findViewById(R.id.dashboard_image_view);
            title_text = itemView.findViewById(R.id.dashboard_title_view);
            recyclerView = itemView.findViewById(R.id.recycler_top_of_day);
            mView = itemView;

        }
    }

    public static class UpCoursesViewHolder extends RecyclerView.ViewHolder {
        TextView uniksameCoursesView;
        ImageSlider cornerImageSlider;

        public UpCoursesViewHolder(View view) {
            super(view);
//            recyclerView = view.findViewById(R.id.recycler_top_of_day);
            uniksameCoursesView = view.findViewById(R.id.uniksame_corner_view);
            cornerImageSlider = view.findViewById(R.id.corner_image_slider_view);
        }
    }

    public static class UniksameCardHolder extends RecyclerView.ViewHolder {

        ImageView unik_card_imageview;

        public UniksameCardHolder(View view) {
            super(view);
            unik_card_imageview = view.findViewById(R.id.unik_card_imageview);
        }
    }
}
