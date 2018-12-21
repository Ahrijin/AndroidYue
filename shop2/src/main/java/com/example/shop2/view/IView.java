package com.example.shop2.view;

public interface IView<T> {
    void setSuccess(T data);
    void setError(T erros);
}
