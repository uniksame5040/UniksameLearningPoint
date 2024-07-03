package com.uniksame.uniksamelearningpoint;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TermsAndCondition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        TextView textView = findViewById(R.id.text_con);
    }
}
