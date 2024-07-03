package com.uniksame.uniksamelearningpoint;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uniksame.uniksamelearningpoint.unikservicesutils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE = 111;
    private ImageView image_profile_pick;
    private TextView profile_status_text_view, text_view_name_view,
            text_view_contact_view, text_view_email_view,likes_detail_text,heart_detail_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar_profile_app_detail);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        inItProfileUi();
    }

    private void inItProfileUi() {
        image_profile_pick = findViewById(R.id.image_profile_pick);
        profile_status_text_view = findViewById(R.id.profile_status_text_view);
        text_view_name_view = findViewById(R.id.text_view_name_view);
        text_view_contact_view = findViewById(R.id.text_view_contact_view);
        text_view_email_view = findViewById(R.id.text_view_email_view);
        heart_detail_text = findViewById(R.id.heart_detail_text);
        likes_detail_text = findViewById(R.id.likes_detail_text);
        loadProfileNameImage();
    }

    private void loadProfileNameImage() {
        FirebaseDatabase.getInstance().getReference("UniksameUsers")
                .child(PreferenceUtils.getUsernamePref(this))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String url = dataSnapshot.child("UsersProfileDetails")
                                    .child("imageUrl").getValue().toString();
                            if (!url.equals("")) {
                                Glide.with(ProfileActivity.this).load(url).into(image_profile_pick);
                            } else {
                                image_profile_pick.setImageResource(R.drawable.ic_baseline_person_24);
                            }
                            profile_status_text_view.setText(dataSnapshot.child("UsersProfileDetails").child("status").getValue().toString());
                            text_view_name_view.setText(dataSnapshot.child("UsersProfileDetails").child("fullName").getValue().toString());
                            text_view_contact_view.setText(dataSnapshot.child("UsersProfileDetails").child("usersMobile").getValue().toString());
                            text_view_email_view.setText(dataSnapshot.child("UsersProfileDetails").child("usersEmail").getValue().toString());
                            likes_detail_text.setText(dataSnapshot.child("UsersProfileDetails").child("like").getValue().toString());
                            heart_detail_text.setText(dataSnapshot.child("UsersProfileDetails").child("post").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {

                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_profile_pick:
                pickImage();
                break;
            case R.id.edit_status_button:
                dialogInfo("Update Status", "status");
                break;
            case R.id.relative_name_view:
                dialogInfo("Update Name", "fullName");
                break;
            case R.id.relative_mob_view:
                dialogInfo("Update Mobile No", "usersMobile");
                break;
            case R.id.relative_email_view:
                dialogInfo("Update Email", "usersEmail");
                break;
            case R.id.relative_pass_view:
                dialogInfo("Update Password", "password");
                break;
            default:
                Toast.makeText(this, "No options", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void pickImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);

        pickActivityResultLauncher.launch("image/*");
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<String> pickActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        // There are no request codes
//                        Intent data = result.getData();
                        uploadImage(result);
                    }
                }
            });

    private void uploadImage(Uri result) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        image_profile_pick.setImageURI(result);
        if (result != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("uploads/"
                    + PreferenceUtils.getUsernamePref(ProfileActivity.this));
            // adding listeners on upload
            // or failure of image
            ref.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FirebaseDatabase.getInstance().getReference("UniksameUsers")
                                    .child(PreferenceUtils.getUsernamePref(ProfileActivity.this))
                                    .child("UsersProfileDetails").child("imageUrl").setValue(uri.toString());
                            PreferenceUtils.saveImgUrl(uri.toString(), ProfileActivity.this);
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }

    private void dialogInfo(String textIdString, String updateChildId) {
        BottomSheetDialog dialog = new BottomSheetDialog(ProfileActivity.this, R.style.BottomSheetDialogTheme);
        dialog.setContentView(R.layout.edit_text_custom_layout);


        EditText edit_Upd_cu_full_name = dialog.findViewById(R.id.edit_Upd_cu_full_name);
        edit_Upd_cu_full_name.setHint(textIdString);
        TextView textView_user_detail = dialog.findViewById(R.id.textView_user_detail);
        textView_user_detail.setText(textIdString);

        Button submitButton = dialog.findViewById(R.id.submitButton);
        if (submitButton != null) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!edit_Upd_cu_full_name.getText().toString().equals("")) {
                        String updateInfoString = edit_Upd_cu_full_name.getText().toString().toString().trim();

                        FirebaseDatabase.getInstance().getReference("UniksameUsers")
                                .child(PreferenceUtils.getUsernamePref(ProfileActivity.this))
                                .child("UsersProfileDetails").child(updateChildId).setValue(updateInfoString);
                        loadProfileNameImage();
                        dialog.dismiss();
                    }
                }
            });
        }
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
