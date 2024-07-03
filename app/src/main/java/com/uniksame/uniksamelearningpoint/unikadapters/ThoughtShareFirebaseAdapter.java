package com.uniksame.uniksamelearningpoint.unikadapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.BuildConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniksame.uniksamelearningpoint.CourseCategoryActivity;
import com.uniksame.uniksamelearningpoint.DetailThoughtShareActivity;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.ThoughtShareActivity;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ModelSoluttion;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.UserChatList;
import com.uniksame.uniksamelearningpoint.unikservicesutils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;

public class ThoughtShareFirebaseAdapter extends FirebaseRecyclerAdapter<ModelSoluttion, RecyclerView.ViewHolder> {

    private final int adapter_code;
    private Context context;

    public ThoughtShareFirebaseAdapter(@NonNull FirebaseRecyclerOptions<ModelSoluttion> options, int adapter_code) {
        super(options);
        this.adapter_code = adapter_code;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.thoughtshare_layout_row, parent, false);
            context = parent.getContext();
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.uniksame_ads_layout, parent, false);
            return new UniksameAdsHolder(view);
        }

    }
    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull ModelSoluttion model) {
        if (holder.getClass() == ViewHolder.class) {
            ViewHolder viewHolder = (ViewHolder) holder;
            if (model.getImageUrl() != null && !model.getImageUrl().equals("")) {
                Glide.with(context)
                        .load(model.getImageUrl())
                        .into(viewHolder.userSolutionProfileImageView);
            } else {
                viewHolder.userSolutionProfileImageView.setImageResource(R.drawable.ic_baseline_person_24);
            }
            if (position == 0 && adapter_code ==DetailThoughtShareActivity.CODE_TWO ||
                    position == 0 && adapter_code ==ThoughtShareActivity.CODE_ONE ) {
                viewHolder.thImage.setVisibility(View.GONE);
                viewHolder.thTitle.setVisibility(View.GONE);
            }else if (position == 0 && adapter_code == RecyclerHomeAdapter.TH_CODE_ONE){
                viewHolder.thImage.setVisibility(View.VISIBLE);
                viewHolder.thTitle.setVisibility(View.VISIBLE);
            }else {
                viewHolder.thImage.setVisibility(View.GONE);
                viewHolder.thTitle.setVisibility(View.GONE);
            }
            viewHolder.nameView.setText("Name: " + model.getName());
            viewHolder.usernameSolutionView.setText(model.getUsername());
            viewHolder.textViewSolution.setText(model.getSolutionText());
            viewHolder.textViewDate.setText(model.getDate());
            viewHolder.likeTextView.setText("" + model.getLike());
            viewHolder.dislikeTextView.setText("" + model.getDislike());
            viewHolder.dislikeTextView.setText("--");
            viewHolder.image_view_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareImageIntent(viewHolder);
                }
            });
            viewHolder.likeLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeOption(model,position, viewHolder);
                }
            });
            viewHolder.addComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBottomDialog();
                }
            });
            viewHolder.love_img_thought.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.love_img_thought.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
            });
            if (adapter_code != DetailThoughtShareActivity.CODE_TWO) {
                viewHolder.userSolutionProfileImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendDetailIntent(model.getUsername());
                    }
                });
            }

            if (adapter_code == ThoughtShareActivity.CODE_ONE) {
                viewHolder.thoughtShareOptionMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUpMenu(model, viewHolder);
                    }
                });
            } else {
                viewHolder.thoughtShareOptionMenu.setVisibility(View.GONE);
            }

        }
    }


    private void popUpMenu(ModelSoluttion model, ViewHolder viewHolder) {
        PopupMenu popup = new PopupMenu(context, viewHolder.thoughtShareOptionMenu);
        popup.inflate(R.menu.thought_share_options_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.see_profile_option:
                        sendDetailIntent(model.getUsername());
                        return true;
                    case R.id.chat_option:
                        //this is chat module imp*
//                        chatOption(model);
                        Toast.makeText(context, "This feature coming soon with ThoughtShare App !", Toast.LENGTH_SHORT).show();
                        return true;
//                    case R.id.add_to_fav_option:
//                        Toast.makeText(context, "Added to to the favourite", Toast.LENGTH_SHORT).show();
//                        return true;
                    case R.id.post_share_option:
                        shareImageIntent(viewHolder);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private void likeOption(ModelSoluttion model, int position, ViewHolder viewHolder) {
        FirebaseDatabase.getInstance().getReference("UniksameUsers")
                .child(model.getUsername())
                .child("UsersProfileDetails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    FirebaseDatabase.getInstance().getReference().child("UserPosts")
                            .child(getRef(position).getKey()).child("like").setValue(model.getLike() + 1);
                    viewHolder.likeTextView.setText("" + model.getLike());

                    FirebaseDatabase.getInstance().getReference("UniksameUsers")
                            .child(model.getUsername())
                            .child("UsersProfileDetails").child("like").setValue((long) snapshot.child("like").getValue() + 1);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void showBottomDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        dialog.setContentView(R.layout.bottom_post_layout);
        TextView textView_post_heading = dialog.findViewById(R.id.textView_post_heading);
        textView_post_heading.setText("Comment here");
        EditText editPostText = dialog.findViewById(R.id.edit_Upd_cu_full_name);
        editPostText.setHint("Write your comment ...");
        ImageButton buttonPost = dialog.findViewById(R.id.submitButton);
        if (buttonPost != null) {
            buttonPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String postText = editPostText.getText().toString().trim();
                    if (!postText.isEmpty()) {
                        Toast.makeText(context, "Comment Sent !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });
        }
        dialog.show();
//                    FirebaseDatabase.getInstance().getReference().child("UserPosts")
//                            .child(getRef(position).getKey()).child("dislike").setValue(model.getDislike() + 1);
//                    viewHolder.dislikeTextView.setText("" + model.getDislike());

    }

    private void chatOption(ModelSoluttion model) {
        if (model.getUsername() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UniksameUsers")
                    .child(PreferenceUtils.getUsernamePref(context))
                    .child("UserChatList");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (model.getUsername().equals(PreferenceUtils.getUsernamePref(context))) {
                        Toast.makeText(context, "You are a host !", Toast.LENGTH_SHORT).show();
                    } else if (!snapshot.child(model.getUsername()).exists()) {

                        addFriendsForHost(model.getUsername(), model.getImageUrl(), model.getName());
                        addFriendsForClient(model.getUsername());
                        String message = "new row created!";
                        sendUserChatListIntent(message, model.getUsername());
                    } else {
                        String message = "You are chatting already !";
                        sendUserChatListIntent(message, model.getUsername());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(context, "No such user Exist !", Toast.LENGTH_SHORT).show();
        }
    }

    private void addFriendsForHost(String usernameHost, String userImage, String name) {
        FirebaseDatabase.getInstance().getReference().child("UniksameUsers")
                .child(PreferenceUtils.getUsernamePref(context)).child("UserChatList").child(usernameHost)
                .setValue(new UserChatList(userImage, usernameHost, name,
                        PreferenceUtils.getUsernamePref(context)));
    }

    private void addFriendsForClient(String usernameClient) {
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("UniksameUsers")
                .child(PreferenceUtils.getUsernamePref(context))
                .child("UsersProfileDetails");
        referenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String imageUrl = snapshot.child("imageUrl").getValue().toString();
                String fullName = snapshot.child("fullName").getValue().toString();
                String userName = snapshot.child("userName").getValue().toString();
                FirebaseDatabase.getInstance().getReference().child("UniksameUsers")
                        .child(usernameClient).child("UserChatList").child(PreferenceUtils.getUsernamePref(context))
                        .setValue(new UserChatList(imageUrl, usernameClient, fullName, userName));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendUserChatListIntent(String message, String chatUsername) {
        Intent thoughtShareChatIntent = new Intent(context, CourseCategoryActivity.class);
        thoughtShareChatIntent.putExtra("username", chatUsername);
        context.startActivity(thoughtShareChatIntent);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void sendDetailIntent(String username) {
        Intent cardSolutionIntent = new Intent(context, DetailThoughtShareActivity.class);
        cardSolutionIntent.putExtra("username", username);
        context.startActivity(cardSolutionIntent);
    }

    private void shareImageIntent(ViewHolder viewHolder) {
        Bitmap bitmap = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bitmap = Bitmap.createBitmap(viewHolder.textViewSolution
                    .getWidth(), viewHolder.textViewSolution.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        viewHolder.textViewSolution.draw(canvas);
        try {
            File file = new File(context.getExternalCacheDir(), File.separator + "getty.jpg");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewSolution, likeTextView, dislikeTextView,
                usernameSolutionView, nameView, thoughtShareOptionMenu, thTitle;
        ImageView likeImageView, dislikeImageView, userSolutionProfileImageView, thImage, love_img_thought, image_view_share;
        LinearLayout likeLinearLayout, addComments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thTitle = itemView.findViewById(R.id.th_mo_title);
            thImage = itemView.findViewById(R.id.th_image_view);
            love_img_thought = itemView.findViewById(R.id.love_img_thought);
            nameView = itemView.findViewById(R.id.name_solution_view);
            userSolutionProfileImageView = itemView.findViewById(R.id.mo_image);
            likeLinearLayout = itemView.findViewById(R.id.like_Linear_l);
            addComments = itemView.findViewById(R.id.dislike_linear_d);
            usernameSolutionView = itemView.findViewById(R.id.username_solution_view);
            textViewDate = itemView.findViewById(R.id.solution_date);
            textViewSolution = itemView.findViewById(R.id.solution_text);
            likeTextView = itemView.findViewById(R.id.like_text_view);
            dislikeTextView = itemView.findViewById(R.id.dislike_text_view);
            thoughtShareOptionMenu = itemView.findViewById(R.id.textViewOptions);
            likeImageView = itemView.findViewById(R.id.like_image_view);
            image_view_share = itemView.findViewById(R.id.image_view_share);
            dislikeImageView = itemView.findViewById(R.id.dislike_image_view);
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
