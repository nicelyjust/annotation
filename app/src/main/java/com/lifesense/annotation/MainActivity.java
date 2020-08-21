package com.lifesense.annotation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nicely.inject.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectContentView(this);
        InjectUtils.injectView(this);

    }


}