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
import com.uniksame.uniksamelearningpoint.CourseCategoryActivity;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ComputerShortsModel;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ModelEducation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerEducationTopicAdapter extends FirebaseRecyclerAdapter<ComputerShortsModel, RecyclerView.ViewHolder> {
    private static final int POSITION_ID = 0;
    private static final int AD_SHOW_POSITION = 101;
    private ArrayList<ModelEducation> list;
    private LayoutInflater mInflater;
    private Context mContext;
    private String frag_page_heading;

    public RecyclerEducationTopicAdapter(@NonNull @NotNull FirebaseRecyclerOptions<ComputerShortsModel> options, String frag_page_heading) {
        super(options);
        this.frag_page_heading = frag_page_heading;
    }

    @NonNull
    @Override
    public RecyclerEducationTopicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        this.mContext = parent.getContext();
        this.mInflater = LayoutInflater.from(parent.getContext());

//        if (viewType == POSITION_ID) {
//            View view = mInflater.inflate(R.layout.top_of_day_recycler_view, parent, false);
//            return new SubTopicHolder(view);
//        } else {
        View view = mInflater.inflate(R.layout.recyclerview_education_row, parent, false);
        return new ViewHolder(view);
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return position;
//        } else if (position % 4 == 0) {
//            return AD_SHOW_POSITION;
//        } else {
//            return -1;
//        }
//    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position, @NonNull @NotNull ComputerShortsModel model) {
        if (holder.getClass() == ViewHolder.class) {
            ViewHolder viewHolder = (ViewHolder) holder;
            if (position == POSITION_ID) {
                viewHolder.headingImage.setVisibility(View.VISIBLE);
                viewHolder.educationTitleView.setVisibility(View.VISIBLE);
                viewHolder.educationTitleView.setText(frag_page_heading);

            } else {
                viewHolder.headingImage.setVisibility(View.GONE);
                viewHolder.educationTitleView.setVisibility(View.GONE);
            }
            viewHolder.sub_title2.setText(model.getCurrentDesc());
            viewHolder.textView1.setText(model.getCurrentTitle());
            Glide.with(mContext).load(model.getCurrentImgUrl()).into(viewHolder.imageView1);
            viewHolder.cardViewEducationView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CourseCategoryActivity.class);
                    intent.putExtra("course", model.getCurrentTitle());
                    intent.putExtra("pageType", model.getPageTipe());
                    mContext.startActivity(intent);
                }
            });
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView educationTitleView;
        View mView;
        TextView textView1, sub_title2;
        ImageView imageView1, headingImage;
        CardView cardViewEducationView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewEducationView = itemView.findViewById(R.id.card_education_row);
            educationTitleView = itemView.findViewById(R.id.all_topics_text_view);
            headingImage = itemView.findViewById(R.id.dashboard_image_view);
            textView1 = itemView.findViewById(R.id.title1);
            sub_title2 = itemView.findViewById(R.id.sub_title2);
            imageView1 = itemView.findViewById(R.id.image1);

            mView = itemView;

        }
    }

//    public static class SubTopicHolder extends RecyclerView.ViewHolder {
//        TextView moTitle;
//        ImageView usersProfileImageView;
//        CardView cardView;
//        RecyclerView recyclerView;
//
//        public SubTopicHolder(View view) {
//            super(view);
//            recyclerView = view.findViewById(R.id.recycler_top_of_day);
////            moTitle = view.findViewById(R.id.mo_title);
//        }
//    }

//    public static class UniksameAdsHolder extends RecyclerView.ViewHolder {
//        CardView cardAdView;
//
//        public UniksameAdsHolder(View view) {
//            super(view);
//            cardAdView = itemView.findViewById(R.id.ads_card_view);
//        }
//    }
}
