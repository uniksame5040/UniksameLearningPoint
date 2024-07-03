package com.uniksame.uniksamelearningpoint;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniksame.uniksamelearningpoint.unikadapters.ThoughtShareFirebaseAdapter;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ModelSoluttion;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DetailThoughtShareActivity extends AppCompatActivity {
    public static final int CODE_TWO = 2;
    private String usernameId;
    private ImageView solutionProfileDetailView;
    private TextView solutionDetailNameTextView, solution_status_text_view,
            solution_name_toolbar_view,all_post_count_view,likes_text_count;
    private Button message_button;
    private ImageButton chat_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_thought_share);

        Toolbar toolbar = findViewById(R.id.toolbar_layout_solution_detail);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        usernameId = getIntent().getStringExtra("username");

        setUiDetailThoughtShare();
        loadUserProfile();
        inItProfileUi();
        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailThoughtShareActivity.this,
                        "This feature coming soon with ThoughtShare App !", Toast.LENGTH_SHORT).show();
            }
        });
        chat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailThoughtShareActivity.this,
                        "This feature coming soon with ThoughtShare App !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inItProfileUi() {
        solutionProfileDetailView = findViewById(R.id.solution_profile_detail_view);
        solutionDetailNameTextView = findViewById(R.id.solution_detail_name_text_view);
        solution_status_text_view = findViewById(R.id.solution_status_text_view);
        solution_name_toolbar_view = findViewById(R.id.solution_name_toolbar_view);
        all_post_count_view = findViewById(R.id.all_post_count_view);
        likes_text_count = findViewById(R.id.likes_text_count);
        message_button = findViewById(R.id.message_button);
        chat_button = findViewById(R.id.chat_button);

    }

    private void loadUserProfile() {
        FirebaseDatabase.getInstance().getReference("UniksameUsers")
                .child(usernameId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String url = dataSnapshot.child("UsersProfileDetails").child("imageUrl").getValue().toString();
                            solutionDetailNameTextView.setText(dataSnapshot.child("UsersProfileDetails").child("fullName").getValue().toString());
                            solution_status_text_view.setText(dataSnapshot.child("UsersProfileDetails").child("status").getValue().toString());
                            solution_name_toolbar_view.setText(dataSnapshot.child("UsersProfileDetails").child("userName").getValue().toString());
                            all_post_count_view.setText(dataSnapshot.child("UsersProfileDetails").child("post").getValue().toString());
                            likes_text_count.setText(dataSnapshot.child("UsersProfileDetails").child("like").getValue().toString());
//                            all_post_count_view.setText(postCount);
                            if (!url.equals("")) {
                                Glide.with(DetailThoughtShareActivity.this).load(url).into(solutionProfileDetailView);
                            } else {
                                solutionProfileDetailView.setImageResource(R.drawable.ic_baseline_person_24);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {

                    }
                });

    }

    private void setUiDetailThoughtShare() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_solution);
        FirebaseRecyclerOptions<ModelSoluttion> modelSolutionOptions =
                new FirebaseRecyclerOptions.Builder<ModelSoluttion>().setQuery(FirebaseDatabase
                        .getInstance().getReference().child("UserPosts").orderByChild("username")
                        .startAt(usernameId).endAt(usernameId+"\uf8ff"), ModelSoluttion.class).build();
        ThoughtShareFirebaseAdapter allPostsAdapter = new ThoughtShareFirebaseAdapter(modelSolutionOptions, CODE_TWO);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allPostsAdapter.startListening();
        recyclerView.setAdapter(allPostsAdapter);
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
