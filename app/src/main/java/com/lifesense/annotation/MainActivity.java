package com.lifesense.annotation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lifesense.annotation.annotation.OnClick;
import com.nicely.inject.BindView;
import com.nicely.inject.ContentView;

import androidx.appcompat.app.AppCompatActivity;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_1)
    Button mButton1;
    @BindView(R.id.btn_2)
    Button mButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectContentView(this);
        InjectUtils.injectView(this);
        InjectUtils.injectEvent(this);

        mButton1.setText("按钮1");
        mButton2.setText("按钮2");

    }
    @OnClick({R.id.btn_1,R.id.btn_2})
    public void adc(View view){
        if (view.getId() == R.id.btn_1) {
            Toast.makeText(this,"button1 被点击",Toast.LENGTH_SHORT).show();
        }else if (view.getId() == R.id.btn_2) {
            Toast.makeText(this,"button2 被点击",Toast.LENGTH_SHORT).show();
        }
    }


}