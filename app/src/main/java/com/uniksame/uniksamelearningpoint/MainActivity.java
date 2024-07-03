package com.uniksame.uniksamelearningpoint;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.uniksame.uniksamelearningpoint.ui.main.SectionsPagerAdapter;
import com.uniksame.uniksamelearningpoint.unikfragment.FragmentDashBoard;
import com.uniksame.uniksamelearningpoint.unikfragment.FragmentEducation;
import com.uniksame.uniksamelearningpoint.unikfragment.FragmentInterview;
import com.uniksame.uniksamelearningpoint.unikfragment.FragmentMotivation;
import com.uniksame.uniksamelearningpoint.unikfragment.FragmentTraining;
import com.uniksame.uniksamelearningpoint.unikservicesutils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, PaymentResultListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private FloatingActionButton fabButton;
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private ImageView image_profile_main, profile_image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());

        toolbar = findViewById(R.id.toolbar_layout);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        setNavigationViewHeader();
        initUi();

        fabButton = findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ThoughtShareActivity.class));

            }
        });

        linearLayout = findViewById(R.id.linear_layout_drawer_id);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateAndTime = sdf.format(new Date());

        if (currentDateAndTime.isEmpty() || !(PreferenceUtils.getDatePref(this).equals(currentDateAndTime))) {
            PreferenceUtils.saveProgressPref(2, this);
            PreferenceUtils.savePostProgressPref(0, this);
            PreferenceUtils.saveDatePref(currentDateAndTime, this);
        } else {
            int progress = PreferenceUtils.getProgressPref(this);
            progress += 8;
            PreferenceUtils.saveProgressPref(progress, this);

        }
    }

    private void setNavigationViewHeader() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        image_profile_main = findViewById(R.id.image_profile_main);

        View hView = navigationView.inflateHeaderView(R.layout.header_drawer);
        profile_image_view = hView.findViewById(R.id.profile_image_view);
        TextView header_name_text_view = hView.findViewById(R.id.header_name_text_view);
        TextView header_email_text_view = hView.findViewById(R.id.header_email_text_view);
        loadProfileNameImage(profile_image_view, image_profile_main, header_name_text_view, header_email_text_view);

        profile_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.close();
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
    }

    private void loadProfileNameImage(ImageView profile_image_view, ImageView image_profile_main,
                                      TextView header_name_text_view, TextView header_email_text_view) {
        FirebaseDatabase.getInstance().getReference("UniksameUsers")
                .child(PreferenceUtils.getUsernamePref(this))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String url = dataSnapshot.child("UsersProfileDetails").child("imageUrl").getValue().toString();
                            header_name_text_view.setText(dataSnapshot.child("UsersProfileDetails").child("fullName").getValue().toString());
                            header_email_text_view.setText(dataSnapshot.child("UsersProfileDetails").child("usersEmail").getValue().toString());
                            if (!url.equals("")) {
                                Glide.with(MainActivity.this).load(url).into(image_profile_main);
                                Glide.with(MainActivity.this).load(url).into(profile_image_view);
                                PreferenceUtils.saveImgUrl(url, MainActivity.this);
                            } else {
                                image_profile_main.setImageResource(R.drawable.ic_baseline_person_24);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {

                    }
                });

    }

    private void initUi() {
        SectionsPagerAdapter sectionsPagerAdapter;
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.addFrag(new FragmentDashBoard());
        sectionsPagerAdapter.addFrag(new FragmentEducation());
        sectionsPagerAdapter.addFrag(new FragmentTraining());
        sectionsPagerAdapter.addFrag(new FragmentInterview());
        sectionsPagerAdapter.addFrag(new FragmentMotivation());
        drawerLayout = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setTabIcon();
    }

    private void setTabIcon() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_cast_for_education_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_model_training_24);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_fact_check_24);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_baseline_video_library_24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.unik_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_app_option:
//                ItemListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
//                shareAppWork();
                alertDialogAgentInfo();
                return true;
            case R.id.notification_main_option:
                startActivity(new Intent(this, UpdateInfoNotification.class));
                return true;
            case R.id.drawer_main:
                drawerLayout.open();
//                makePayment();
                return true;
            case R.id.signup:
                startActivity(new Intent(this, SignUpActivity.class));
                return true;
            case R.id.login:
                startActivity(new Intent(this, UniksameLoginActivity.class));
                return true;
            case R.id.agent_login_id:
                startActivity(new Intent(this, UpdateCourseGKIFCActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void makePayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_kbYWEarVMPx6la");
        checkout.setImage(R.drawable.uns_no_back);

        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Uniksame");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact", "9988776655");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        drawerLayout.close();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_redeem_option:
                drawerLayout.close();
//                startActivity(new Intent(this, AddAccountActivity.class));
                alertDialogAgentInfo();
                return true;
            case R.id.nav_contact_us:
                drawerLayout.close();
                composeEmail(new String[]{"muhammadfaijan5@gmail.com"}, "I want to contact");
                return true;
            case R.id.nav_feedback:
                composeEmail(new String[]{"uniksamelearning@gmail.com"}, " Feedback");
                drawerLayout.close();
                return true;
            case R.id.nav_terms_condition:
                drawerLayout.close();
                startActivity(new Intent(this, TermsAndCondition.class));
                return true;
            default:
                drawerLayout.close();
                startActivity(new Intent(this, TermsAndCondition.class));
                return true;
        }
    }

    private void alertDialogAgentInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Redeem Amount will be Available with agent id please buy" +
                " agent id to earn live money.")
                .setTitle("Buy agent id !")
                .setNegativeButton("Got it !", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful transaction id: " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment failed due to " + s, Toast.LENGTH_SHORT).show();

    }

    private void shareAppWork() {
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
                        Toast.makeText(MainActivity.this, "success ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        dialog.show();
    }

}
