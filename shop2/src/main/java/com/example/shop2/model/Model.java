package com.example.shop2.model;

import com.example.shop2.callback.MyCallBack;

public interface Model {
    void getData(String url,MyCallBack callBack);
}
