package com.example.shop2.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shop2.Main3Activity;
import com.example.shop2.R;
import com.example.shop2.adapter.MyAdapter;
import com.example.shop2.bean.MyData;
import com.example.shop2.presenter.PresenterImpls;
import com.example.shop2.view.IView;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShouYeFragment extends Fragment implements IView {

    private View v;
    private String[] imgsArray = {
            "http://www.zhaoapi.cn/images/quarter/ad1.png",
            "http://www.zhaoapi.cn/images/quarter/ad2.png",
            "http://www.zhaoapi.cn/images/quarter/ad3.png",
            "http://www.zhaoapi.cn/images/quarter/ad4.png"
    };
    private FlyBanner flyBanner;
    private RecyclerView recy;
    private String mUrl = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    private List<MyData.DataBean> datas = new ArrayList<>();
    private MyAdapter adapter;
    private TextView gao;
    private PresenterImpls presenterImpls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_shou_ye, container, false);
        initView();
        initData();
        adapter = new MyAdapter(datas,getActivity());
        recy.setAdapter(adapter);
        presenterImpls = new PresenterImpls(this);
        presenterImpls.startRequest(mUrl);
        gao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    private void initData() {
        List<String> mList = new ArrayList<>();
        for (int i = 0; i < imgsArray.length; i++) {
            mList.add(imgsArray[i]);
        }
        flyBanner.setImagesUrl(mList);
    }

    private void initView() {
        flyBanner = v.findViewById(R.id.fly_banner);
        recy = v.findViewById(R.id.recy);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recy.setLayoutManager(staggeredGridLayoutManager);
        gao = v.findViewById(R.id.gao);
    }

    @Override
    public void setSuccess(Object data) {
        MyData myData = (MyData) data;
        datas.addAll(myData.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setError(Object erros) {

    }
}
