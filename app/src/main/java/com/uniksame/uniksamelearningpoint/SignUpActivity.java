package com.uniksame.uniksamelearningpoint;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.SignUpHelper;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.WorkModelHelper;
import com.uniksame.uniksamelearningpoint.unikservicesutils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;

public class SignUpActivity extends AppCompatActivity {

    private EditText edit_full_name, edit_create_u_name, editMobile, editEmail,
            edit_password, edit_con_password; //referral_code_view;

    private String name;
    private String userName;
    private String mobileNo;
    private String emailId;
    private String password;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inItAllFields();

        Button regButtonView = findViewById(R.id.regButtonView);
        regButtonView.setOnClickListener(view -> {
            if (!validateName() | !validatePassword() | !validatePhoneNo() |
                    !validateEmail() | !validateUsername()) {
                Toast.makeText(this, "No valid details ", Toast.LENGTH_SHORT).show();
            } else if (confPassword()) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registering process ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        FirebaseDatabase.getInstance().getReference().child("UniksameUsers")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!snapshot.child(userName).exists()){
                    FirebaseDatabase.getInstance().getReference().child("UniksameUsers")
                            .child(userName).child("UsersProfileDetails")
                            .setValue(new SignUpHelper(name, userName, mobileNo, emailId, "",
                                    password, "", gender, "", "",0,0));
                    PreferenceUtils.saveUsername(userName,SignUpActivity.this);
                    PreferenceUtils.saveFullName(name,SignUpActivity.this);
                    workModel();
                    progressDialog.dismiss();
                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                    finish();
                }else {
                    edit_create_u_name.setError("Please create different username");
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Username already taken !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(SignUpActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void workModel() {
        FirebaseDatabase.getInstance().getReference().child("UniksameUsers")
                .child(userName).child("work").setValue(new WorkModelHelper("5",
                "0","0","00","00","0"));
    }

    private void inItAllFields() {
        edit_full_name = findViewById(R.id.edit_full_name);
        edit_create_u_name = findViewById(R.id.edit_create_u_name);
        editMobile = findViewById(R.id.editMobile);
        editEmail = findViewById(R.id.editEmail);
        edit_password = findViewById(R.id.edit_password);
        edit_con_password = findViewById(R.id.edit_con_password);
//        referral_code_view = findViewById(R.id.referral_code_view);
    }

    private Boolean validateName() {
        name = edit_full_name.getText().toString().trim();

        if (name.isEmpty()) {
            edit_full_name.setError("Field cannot be empty");
            return false;
        } else {
            edit_full_name.setError(null);
            return true;
        }
    }

    private Boolean validateUsername() {
        userName = edit_create_u_name.getText().toString().trim();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (userName.isEmpty()) {
            edit_create_u_name.setError("Field cannot be empty");
            return false;
        } else if (userName.length() >= 15) {
            edit_create_u_name.setError("Username too long");
            return false;
        } else if (!userName.matches(noWhiteSpace)) {
            edit_create_u_name.setError("White Spaces are not allowed");
            return false;
        } else {
            edit_create_u_name.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        mobileNo = editMobile.getText().toString().trim();
        if (mobileNo.isEmpty()) {
            editMobile.setError("Field cannot be empty");
            return false;
        } else {
            editMobile.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        emailId = editEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emailId.isEmpty()) {
            editEmail.setError("Field cannot be empty");
            return false;
        } else if (!emailId.matches(emailPattern)) {
            editEmail.setError("Invalid email address");
            return false;
        } else {
            editEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        password = edit_password.getText().toString().trim();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 6 characters
                "$";

        if (password.isEmpty()) {
            edit_password.setError("Field cannot be empty");
            return false;
        } else if (!password.matches(passwordVal)) {
            edit_password.setError("Password is too weak,must length 6, At least one spacial character");
            return false;
        } else {
            edit_password.setError(null);
            return true;
        }
    }

    private boolean confPassword() {
        String conPassword = edit_con_password.getText().toString().trim();
        if (!password.equals(conPassword)) {
            edit_con_password.setError("Confirm Password don't mach");
            return false;
        } else {
            edit_con_password.setError(null);
            return true;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_male:
                gender = "male";
                return;
            case R.id.radio_female:
                gender = "female";
                return;
            default:
                gender = "other";
        }
    }
}
