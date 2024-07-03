package com.uniksame.uniksamelearningpoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniksame.uniksamelearningpoint.unikservicesutils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;

public class UniksameLoginActivity extends AppCompatActivity {

    Button button_login;
    ImageButton button_sign_up;
    EditText edit_login_user_name, edit_login_password;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniksame_login);

        edit_login_user_name = findViewById(R.id.edit_login_user_name);
        edit_login_password = findViewById(R.id.edit_login_password);
        button_login = findViewById(R.id.button_login);
        button_sign_up = findViewById(R.id.button_sign_up);

        button_sign_up.setOnClickListener(view -> startActivity(new Intent(
                UniksameLoginActivity.this, SignUpActivity.class)));
        button_login.setOnClickListener(view -> loginProcess());

    }

    private void loginProcess() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging in ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (validateUserName()){
            FirebaseDatabase.getInstance().getReference("UniksameUsers")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if (snapshot.child(userName).exists()){
                                String password = snapshot.child(userName).child("UsersProfileDetails")
                                        .child("password").getValue().toString();
                                if (password.equals(edit_login_password.getText().toString().trim())){
                                    PreferenceUtils.saveUsername(userName,UniksameLoginActivity.this);
                                    progressDialog.dismiss();
                                    startActivity(new Intent(UniksameLoginActivity.this,MainActivity.class));
                                    finish();
                                }else {
                                    progressDialog.dismiss();
                                    edit_login_password.setError("Password wrong");
                                }
                            }else {
                                progressDialog.dismiss();
                                edit_login_user_name.setError("Username wrong");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            Toast.makeText(UniksameLoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

        }else {
            progressDialog.dismiss();
        }
    }
    private Boolean validateUserName() {
        userName = edit_login_user_name.getText().toString().trim();
        String checkPass = edit_login_password.getText().toString().trim();
        if (userName.isEmpty()) {
            edit_login_user_name.setError("Field cannot be empty");
            return false;
        } else if (checkPass.isEmpty()){
            edit_login_password.setError("Field cannot be empty");
            return false;
        }else {
            edit_login_user_name.setError(null);
            return true;
        }
    }
}
