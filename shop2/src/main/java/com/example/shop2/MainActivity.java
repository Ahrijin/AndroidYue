package com.example.shop2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView imgs;
    private Button begin;
    private int in = 3;
    private ObjectAnimator objectAnimator1;
    private ObjectAnimator objectAnimator2;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
             if(in == 0){
                 Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                 startActivity(intent);
             }else {
                 in--;
                 handler.sendEmptyMessageDelayed(0,1000);
             }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        imgs = (ImageView) findViewById(R.id.imgs);
        begin = (Button) findViewById(R.id.begin);

        begin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.begin:
                objectAnimator1 = ObjectAnimator.ofFloat(imgs,"rotationX",180f);
                objectAnimator2 = ObjectAnimator.ofFloat(imgs,"alpha",0f,0.8f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimator1).with(objectAnimator2);
                animatorSet.setDuration(3000);
                animatorSet.start();
                handler.sendEmptyMessage(0);
                break;
        }
    }
}
