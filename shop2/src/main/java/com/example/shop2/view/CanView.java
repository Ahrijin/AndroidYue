package com.example.shop2.view;

public interface CanView<T> {
    void setSuccess(T data);
    void setError(T erros);
}
