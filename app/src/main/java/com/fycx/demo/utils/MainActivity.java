package com.fycx.demo.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fycx.utils.StatusBarUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtils.setStatusBarLightMode(this);
    }
}
