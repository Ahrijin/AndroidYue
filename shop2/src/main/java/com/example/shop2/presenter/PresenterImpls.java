package com.example.shop2.presenter;

import com.example.shop2.callback.MyCallBack;
import com.example.shop2.model.ModelImpls;
import com.example.shop2.view.IView;

public class PresenterImpls implements IPresenter {
    private IView iView;
    private ModelImpls modelImpls;

    public PresenterImpls(IView iView) {
        this.iView = iView;
        modelImpls = new ModelImpls();
    }

    @Override
    public void startRequest(String url) {
        modelImpls.getData(url, new MyCallBack() {
            @Override
            public void success(Object data) {
                iView.setSuccess(data);
            }

            @Override
            public void error(Object error) {
                iView.setError(error);
            }
        });
    }

    public void sets() {
        if (modelImpls != null) {
            modelImpls = null;
        }
        if (iView != null) {
            iView = null;
        }
    }
}
