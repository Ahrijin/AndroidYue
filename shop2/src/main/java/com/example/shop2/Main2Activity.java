package com.example.shop2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.shop2.fragment.MyFragment;
import com.example.shop2.fragment.ShouYeFragment;

public class Main2Activity extends AppCompatActivity {

    private FrameLayout frame_layout;
    private RadioButton first;
    private RadioButton my;
    private RadioGroup rg;
    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        setListener();
        manager.beginTransaction().replace(R.id.frame_layout, new ShouYeFragment()).commit();

    }






    private void setListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.first:
                        manager.beginTransaction().replace(R.id.frame_layout, new ShouYeFragment()).commit();
                        break;
                    case R.id.my:
                        manager.beginTransaction().replace(R.id.frame_layout, new MyFragment()).commit();
                        break;
                }
            }
        });
    }

    private void initView() {
        frame_layout = (FrameLayout) findViewById(R.id.frame_layout);
        first = (RadioButton) findViewById(R.id.first);
        my = (RadioButton) findViewById(R.id.my);
        rg = (RadioGroup) findViewById(R.id.rg);
        manager = getSupportFragmentManager();
    }
}
