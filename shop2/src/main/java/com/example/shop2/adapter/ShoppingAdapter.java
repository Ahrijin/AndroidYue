package com.example.shop2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shop2.R;
import com.example.shop2.bean.MyShoppingCartData;
import com.example.shop2.weight.JiaJianView;

import java.util.List;

public class ShoppingAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<MyShoppingCartData.DataBean> mList;

    public ShoppingAdapter(Context context, List<MyShoppingCartData.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getSpus().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = View.inflate(context, R.layout.group_item, null);
            groupHolder.cb = convertView.findViewById(R.id.group_cb);
            groupHolder.titls = convertView.findViewById(R.id.group_titles);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        String name = mList.get(groupPosition).getName();
        groupHolder.titls.setText(name + "");
        boolean a = isChildAllCheck(groupPosition);
        groupHolder.cb.setChecked(a);
        groupHolder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = isChildAllCheck(groupPosition);
                childAllCheck(groupPosition, !b);
                notifyDataSetChanged();
                if (mAdapterCallBack != null) {
                    mAdapterCallBack.setFlush();
                }
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = View.inflate(context, R.layout.child_item, null);
            childHolder.cb1 = convertView.findViewById(R.id.child_cb);
            childHolder.imgs = convertView.findViewById(R.id.child_imgs);
            childHolder.prices = convertView.findViewById(R.id.child_price);
            childHolder.titls = convertView.findViewById(R.id.child_titles);
            childHolder.jiaJian = convertView.findViewById(R.id.jia_jian);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        MyShoppingCartData.DataBean.SpusBean spusBean = mList.get(groupPosition).getSpus().get(childPosition);
        childHolder.titls.setText(spusBean.getName());
        childHolder.prices.setText(spusBean.getSkus().get(0).getPrice());
        childHolder.jiaJian.setNusm(spusBean.getPraise_num());
        Glide.with(context).load(spusBean.getPic_url()).into(childHolder.imgs);

        //isChildCheck判断spusBean的true、false，与组联动
        childHolder.cb1.setChecked(spusBean.isChildCheck());

        childHolder.cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean boo = isChildCheck(groupPosition, childPosition);
                setChildCheck(groupPosition, childPosition, !boo);
                notifyDataSetChanged();
                if (mAdapterCallBack != null) {
                    mAdapterCallBack.setFlush();
                }
            }
        });
        //刷新底部视图
        childHolder.jiaJian.setChange(new JiaJianView.OnCountChange() {
            @Override
            public void setCount(int con) {
                setNums(groupPosition,childPosition,con);
                notifyDataSetChanged();
                if (mAdapterCallBack != null) {
                    mAdapterCallBack.setFlush();
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    //判断child是否被全部选中
    public void childAllCheck(int groupPosition, boolean boo) {
        MyShoppingCartData.DataBean dataBean = mList.get(groupPosition);
        List<MyShoppingCartData.DataBean.SpusBean> spus = dataBean.getSpus();
        for (int i = 0; i < spus.size(); i++) {
            MyShoppingCartData.DataBean.SpusBean spusBean = spus.get(i);
            spusBean.setChildCheck(boo);//设置小组成员是否被选中的状态
        }
    }

    //判断小组成员的选中状态（全部）
    public boolean isChildAllCheck(int groupPosition) {
        boolean boo = true;
        MyShoppingCartData.DataBean dataBean = mList.get(groupPosition);
        List<MyShoppingCartData.DataBean.SpusBean> spus = dataBean.getSpus();
        for (int i = 0; i < spus.size(); i++) {
            MyShoppingCartData.DataBean.SpusBean spusBean = spus.get(i);
            if (!spusBean.isChildCheck()) {
                return false;
            }
        }
        return boo;
    }

    //给child赋值
    public void setChildCheck(int groupPosition, int childPosition, boolean childcheck) {
        MyShoppingCartData.DataBean.SpusBean spusBean = mList.get(groupPosition).getSpus().get(childPosition);
        spusBean.setChildCheck(childcheck);
    }

    //判断小组成员的选中状态（单个）
    public boolean isChildCheck(int groupPosition, int childPosition) {
        MyShoppingCartData.DataBean.SpusBean spusBean = mList.get(groupPosition).getSpus().get(childPosition);
        if (spusBean.isChildCheck()) {
            return true;
        }
        return false;
    }

    //给全选设置数据
    public void setAllGoods(boolean isAllCheck) {
        for (int i = 0; i < mList.size(); i++) {
            MyShoppingCartData.DataBean dataBean = mList.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                MyShoppingCartData.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                spusBean.setChildCheck(isAllCheck);
            }
        }
    }

    //判断所有数据是否选中
    public boolean isAllGoodsCheck() {
        boolean boo = true;
        for (int i = 0; i < mList.size(); i++) {
            MyShoppingCartData.DataBean dataBean = mList.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                MyShoppingCartData.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                if (!spusBean.isChildCheck()) {
                    boo = false;
                }
            }
        }
        return boo;
    }

    //给商品数量赋值
    public void setNums(int groupPosition, int childPosition, int con) {
        MyShoppingCartData.DataBean.SpusBean spusBean = mList.get(groupPosition).getSpus().get(childPosition);
        spusBean.setPraise_num(con);
    }

    //计算商品数量
    public int setAllGoodsNums() {
        int allNumber = 0;
        for (int i = 0; i < mList.size(); i++) {
            MyShoppingCartData.DataBean dataBean = mList.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                MyShoppingCartData.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                //加判断，判断是否勾选
                if(spusBean.isChildCheck()){
                    //如果勾选了，就改变商品数量
                    allNumber += spusBean.getPraise_num();
                }

            }
        }
        return allNumber;
    }

    //计算商品价格
    public Float setAllGoodsPrices() {
        float allPrice = 0;
        for (int i = 0; i < mList.size(); i++) {
            MyShoppingCartData.DataBean dataBean = mList.get(i);
            for (int j = 0; j < dataBean.getSpus().size(); j++) {
                MyShoppingCartData.DataBean.SpusBean spusBean = dataBean.getSpus().get(j);
                //加判断，判断是否勾选
                if(spusBean.isChildCheck()){
                    //如果勾选了，就改变商品价格
                    allPrice += spusBean.getPraise_num() * Float.parseFloat(spusBean.getSkus().get(0).getPrice());
                }

            }
        }
        return allPrice;
    }

    class GroupHolder {
        CheckBox cb;
        TextView titls;
    }

    class ChildHolder {
        CheckBox cb1;
        ImageView imgs;
        TextView titls;
        TextView prices;
        JiaJianView jiaJian;
    }

    public interface AdapterCallBack {
        //设置底部刷新的回调
        void setFlush();
    }

    private AdapterCallBack mAdapterCallBack;

    public void setCallBack(AdapterCallBack adapterCallBack) {
        this.mAdapterCallBack = adapterCallBack;
    }

}
