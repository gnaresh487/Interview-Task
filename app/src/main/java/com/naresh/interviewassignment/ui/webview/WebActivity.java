package com.naresh.interviewassignment.ui.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.naresh.interviewassignment.R;
import com.naresh.interviewassignment.databinding.ActivityWebBinding;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWebBinding webBinding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            webBinding.webProgress.setVisibility(View.VISIBLE);
            webBinding.webView.loadUrl(intent.getExtras().getString("web_link"));
            webBinding.webProgress.setVisibility(View.GONE);
        }
    }
}
