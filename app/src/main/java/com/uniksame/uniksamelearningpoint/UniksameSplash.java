package com.uniksame.uniksamelearningpoint;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.uniksame.uniksamelearningpoint.unikservicesutils.PreferenceUtils;

public class UniksameSplash extends AppCompatActivity {

    private static final long SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniksame_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String checkIsUserLogInString = PreferenceUtils.getUsernamePref(UniksameSplash.this);

                if (checkIsUserLogInString != null){
                    Intent mainIntent = new Intent(UniksameSplash.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else {
                    startActivity(new Intent(UniksameSplash.this,UniksameLoginActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME);

    }
}
