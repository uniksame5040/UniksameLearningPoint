package com.uniksame.uniksamelearningpoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniksame.uniksamelearningpoint.unikadapters.ThoughtShareFirebaseAdapter;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ModelSoluttion;
import com.uniksame.uniksamelearningpoint.unikservicesutils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThoughtShareActivity extends AppCompatActivity {

    public static final int CODE_ONE = 1;
    private RecyclerView recyclerThoughShareAlPost;
    private ThoughtShareFirebaseAdapter adapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private TextView textViewName;
    private ImageView imageViewProfile;
    private ImageButton edit_Post_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_share);

        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        initThoughtShareUi();
        loadProfileNameImage();

        edit_Post_button = findViewById(R.id.edit_Post_button);
        edit_Post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPosts();
            }
        });


    }

    private void uploadPosts() {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        dialog.setContentView(R.layout.bottom_post_layout);
        EditText editPostText = dialog.findViewById(R.id.edit_Upd_cu_full_name);
        ImageButton buttonPost = dialog.findViewById(R.id.submitButton);
        if (buttonPost != null) {
            buttonPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String postText = editPostText.getText().toString().trim();
                    if (!postText.isEmpty()) {
                        FirebaseDatabase.getInstance().getReference("UniksameUsers")
                                .child(PreferenceUtils.getUsernamePref(ThoughtShareActivity.this))
                                .child("UsersProfileDetails").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    FirebaseDatabase.getInstance().getReference("UniksameUsers")
                                            .child(PreferenceUtils.getUsernamePref(ThoughtShareActivity.this))
                                            .child("UsersProfileDetails").child("post").setValue((long)snapshot.child("post").getValue()+1);

                                    int progress = PreferenceUtils.getPostProgressPref(ThoughtShareActivity.this);
                                    progress+=10;
                                    PreferenceUtils.savePostProgressPref(progress,ThoughtShareActivity.this);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                        SimpleDateFormat sdf = new SimpleDateFormat("E-dd-MMM-yyyy 'at' hh:mm a");
                        String currentDateAndTime = sdf.format(new Date());
                        FirebaseDatabase.getInstance().getReference().child("UserPosts").push()
                                .setValue(new ModelSoluttion(PreferenceUtils.getFullNamePref(
                                        ThoughtShareActivity.this), postText, currentDateAndTime, 0, 0,
                                        PreferenceUtils.getUsernamePref(ThoughtShareActivity.this),
                                        PreferenceUtils.getImgUrlPref(ThoughtShareActivity.this)))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialog.dismiss();
                                        Toast.makeText(ThoughtShareActivity.this, " uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            });
        }
        dialog.show();
    }

    private void loadProfileNameImage() {
        imageViewProfile = findViewById(R.id.thought_share_dp);
        textViewName = findViewById(R.id.thought_share_username);
        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThoughtShareActivity.this,DetailThoughtShareActivity.class);
                intent.putExtra("username",PreferenceUtils.getUsernamePref(ThoughtShareActivity.this));
                startActivity(intent);
            }
        });
        FirebaseDatabase.getInstance().getReference("UniksameUsers")
                .child(PreferenceUtils.getUsernamePref(this))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            textViewName.setText(PreferenceUtils.getUsernamePref(ThoughtShareActivity.this));

                            String url = dataSnapshot.child("UsersProfileDetails")
                                    .child("imageUrl").getValue().toString();
                            if (!url.equals("")) {
                                Glide.with(ThoughtShareActivity.this)
                                        .load(url)
                                        .into(imageViewProfile);
                            } else {
                                imageViewProfile.setImageResource(R.drawable.ic_baseline_person_24);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    private void initThoughtShareUi() {
        recyclerThoughShareAlPost = findViewById(R.id.recycler_view_though_share_all_post);

        FirebaseRecyclerOptions<ModelSoluttion> modelSolutionOptions =
                new FirebaseRecyclerOptions.Builder<ModelSoluttion>().setQuery(FirebaseDatabase
                        .getInstance().getReference().child("UserPosts"), ModelSoluttion.class).build();
        adapter = new ThoughtShareFirebaseAdapter(modelSolutionOptions, CODE_ONE);
        adapter.startListening();
        recyclerThoughShareAlPost.setLayoutManager(new LinearLayoutManager(this));
        recyclerThoughShareAlPost.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
