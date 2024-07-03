package com.uniksame.uniksamelearningpoint;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniksame.uniksamelearningpoint.unikadapters.EducationCategoryAdapter;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ComputerShortsModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class CourseCategoryActivity extends AppCompatActivity {

    private ArrayList<ComputerShortsModel> list;
    private RecyclerView recycler_category;
    private EducationCategoryAdapter educationCategoryAdapter;
    private Toolbar toolbar;
    private String category,pageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coures_category);

        category = getIntent().getStringExtra("course");
        pageType = getIntent().getStringExtra("pageType");

        toolbar = findViewById(R.id.toolbar_layout_course_category);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(category);
        }

        recycler_category = findViewById(R.id.recycler_category);
        list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("UniksameHost").child("FreeCourse").child(pageType)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                ComputerShortsModel model = snapshot1.getValue(ComputerShortsModel.class);
                                list.add(model);
                                educationCategoryAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(CourseCategoryActivity.this, "This Course coming soon", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        Toast.makeText(CourseCategoryActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                });

        educationCategoryAdapter = new EducationCategoryAdapter(this, list,pageType);
        recycler_category.setLayoutManager(new LinearLayoutManager(this));
        recycler_category.setAdapter(educationCategoryAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// Respond to the action bar's Up/Home button
            this.finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

}
