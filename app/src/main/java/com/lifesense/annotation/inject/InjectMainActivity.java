package com.lifesense.annotation.inject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lifesense.annotation.R;
import com.nicely.inject.InjectView;

import androidx.appcompat.app.AppCompatActivity;

public class InjectMainActivity extends AppCompatActivity {
    @InjectView(R.id.tv_1)
    TextView mTv1;
    @InjectView(R.id.tv_2)
    TextView mTv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inject_main);
        InjectButterKnife.bind(this);
        mTv1.setText("haha");
        mTv2.setText("xixi");
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, InjectMainActivity.class);
        context.startActivity(starter);
    }
}