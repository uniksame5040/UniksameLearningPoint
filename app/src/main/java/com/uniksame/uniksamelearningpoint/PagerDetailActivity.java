package com.uniksame.uniksamelearningpoint;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.uniksame.uniksamelearningpoint.unikadapters.EducationCategoryAdapter;
import com.uniksame.uniksamelearningpoint.unikadapters.PagesDetailPagerAdapter;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ComputerShortsModel;

import java.util.ArrayList;

public class PagerDetailActivity extends AppCompatActivity {

    private static final long WAIT_FOR_PAGE_TIME = 1000;
    private ViewPager2 viewPager2;
    private PagesDetailPagerAdapter arrayListAdapter;
    private String intentCode;
    private int pageCode, position;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_detail);
        viewPager2 = findViewById(R.id.current_affares_pager);
        viewPager2.setPageTransformer(new ZoomOutPageTransfer());

        intentCode = getIntent().getStringExtra("intent_code");
        pageCode = getIntent().getIntExtra("pageCode", 0);
        position = getIntent().getIntExtra("position", 0);

        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        if (pageCode == EducationCategoryAdapter.PAGE_DETAIL_CODE) {
            setPageAdapterCoursePage();
        } else {
            setPageAdapter();
        }
    }

    private void setPageAdapterCoursePage() {

        ArrayList<ComputerShortsModel> list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("UniksameHost").child("FreeCourse").child(intentCode)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                shimmerFrameLayout.setVisibility(View.GONE);
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    ComputerShortsModel model = snapshot1.getValue(ComputerShortsModel.class);
                                    list.add(model);
                                    arrayListAdapter.notifyDataSetChanged();
                                }
                                viewPager2.setCurrentItem(position);
                            }
                        }, 1000);

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        Toast.makeText(PagerDetailActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                });

        arrayListAdapter = new PagesDetailPagerAdapter(list, this, pageCode);
        viewPager2.setAdapter(arrayListAdapter);
    }

    private void setPageAdapter() {
        ArrayList<ComputerShortsModel> list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("UniksameHost").child("ComputerDailyShorts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                shimmerFrameLayout.setVisibility(View.GONE);
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    ComputerShortsModel model = snapshot1.getValue(ComputerShortsModel.class);
                                    list.add(model);
                                    arrayListAdapter.notifyDataSetChanged();
                                }
                                viewPager2.setCurrentItem(position);
                            }
                        }, 1000);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        Toast.makeText(PagerDetailActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                });
        arrayListAdapter = new PagesDetailPagerAdapter(list, this, pageCode);
        viewPager2.setAdapter(arrayListAdapter);
    }
}
