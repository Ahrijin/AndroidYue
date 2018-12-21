package com.example.shop2.presenter;

import com.example.shop2.callback.MyCallBack;
import com.example.shop2.model.CanModelImpls;
import com.example.shop2.view.CanView;

public class CanPresenterImpls implements CanPresenter {
    private CanModelImpls canModelImpls;
    private CanView canView;

    public CanPresenterImpls(CanView canView) {
        this.canView = canView;
        canModelImpls = new CanModelImpls();
    }

    @Override
    public void startRequest(String url) {
        canModelImpls.getData(url, new MyCallBack() {
            @Override
            public void success(Object data) {
                canView.setSuccess(data);
            }

            @Override
            public void error(Object error) {
                canView.setError(error);
            }
        });
    }

    public void sets() {
        if (canModelImpls != null) {
            canModelImpls = null;
        }
        if (canView != null) {
            canView = null;
        }
    }
}
