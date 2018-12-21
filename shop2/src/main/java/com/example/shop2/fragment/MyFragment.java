package com.example.shop2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.shop2.R;
import com.example.shop2.adapter.ShoppingAdapter;
import com.example.shop2.bean.MyShoppingCartData;
import com.example.shop2.presenter.CanPresenterImpls;
import com.example.shop2.view.CanView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements CanView {

    private View v;
    private ExpandableListView expand;
    private CheckBox cb;
    private TextView heji;
    private TextView jies;
    private ShoppingAdapter adapter;
    private String mUrl = "http://www.wanandroid.com/tools/mockapi/6523/restaurant-list";
    private List<MyShoppingCartData.DataBean> lists = new ArrayList<>();
    private CanPresenterImpls canPresenterImpls;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_my, container, false);
        initView();
        adapter = new ShoppingAdapter(getActivity(), lists);
        expand.setAdapter(adapter);
        canPresenterImpls = new CanPresenterImpls(this);
        canPresenterImpls.startRequest(mUrl);
        adapter.setCallBack(new ShoppingAdapter.AdapterCallBack() {
            @Override
            public void setFlush() {
                setBottomFlush();
            }
        });
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用适配器调用所有数据是否选中的方法isAllGoodsCheck
                boolean b = adapter.isAllGoodsCheck();
                adapter.setAllGoods(!b);
                adapter.notifyDataSetChanged();
                setBottomFlush();
            }
        });
        return v;
    }

    //写一个方法
    private void setBottomFlush() {
        //让group和全选按钮联动
        boolean boo = adapter.isAllGoodsCheck();
        cb.setChecked(boo);
        int nums = adapter.setAllGoodsNums();
        Float prices = adapter.setAllGoodsPrices();
        heji.setText(prices + "");
        jies.setText(nums + "");
    }

    private void initView() {
        expand = v.findViewById(R.id.expand);
        cb = v.findViewById(R.id.cb);
        heji = v.findViewById(R.id.addUpTo);
        jies = v.findViewById(R.id.settle);
        expand.setGroupIndicator(null);
    }

    @Override
    public void setSuccess(Object data) {
        MyShoppingCartData cartData = (MyShoppingCartData) data;
        lists.addAll(cartData.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setError(Object erros) {

    }
}
