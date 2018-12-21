package com.example.shop2.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shop2.R;

public class JiaJianView extends LinearLayout implements View.OnClickListener {

    private TextView jia;
    private TextView jian;
    private TextView number;
    private int mCount;

    public JiaJianView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.jia_jian_item, this);
        initView();


    }

    private void initView() {
        jia = findViewById(R.id.jia);
        jia.setOnClickListener(this);
        jian = findViewById(R.id.jian);
        jian.setOnClickListener(this);
        number = findViewById(R.id.nums);
    }

    //将数据中的数量先赋值给控件
    public void setNusm(int con) {
        this.mCount = con;
        number.setText(con+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jia:
                mCount++;
                number.setText(mCount + "");
                //判断接口对象是否为空，调用方法,将数量MCount传到前面
                if (onCountChange != null) {
                    onCountChange.setCount(mCount);
                }
                break;
            case R.id.jian:
                if (mCount > 0) {
                    mCount--;
                    number.setText(mCount + "");
                    //判断接口对象是否为空，调用方法,将数量MCount传到前面
                    if (onCountChange != null) {
                        onCountChange.setCount(mCount);
                    }
                }
                break;
        }
    }

    public interface OnCountChange {
        void setCount(int con);
    }

    private OnCountChange onCountChange;

    public void setChange(OnCountChange onCountChange) {
        this.onCountChange = onCountChange;
    }
}
