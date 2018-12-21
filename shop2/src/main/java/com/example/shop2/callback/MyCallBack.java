package com.example.shop2.callback;

public interface MyCallBack<T> {
    void success(T data);
    void error(T error);
}
