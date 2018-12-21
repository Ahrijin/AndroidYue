package com.example.shop2.model;

import android.os.AsyncTask;

import com.example.shop2.bean.MyShoppingCartData;
import com.example.shop2.callback.MyCallBack;
import com.example.shop2.utils.OkHttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

public class CanModelImpls implements  CanModel {

    private MyCallBack callBack;
    @Override
    public void getData(String url, MyCallBack callBack) {
        this.callBack = callBack;
        new MyTask().execute(url);
    }

    class MyTask extends AsyncTask<String,Void,MyShoppingCartData>{
        @Override
        protected MyShoppingCartData doInBackground(String... strings) {
            try {
                String jsonStr = OkHttpUtils.getInstance().get(strings[0]);
                Gson gson = new Gson();
                MyShoppingCartData cartData = gson.fromJson(jsonStr, MyShoppingCartData.class);
                return cartData;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(MyShoppingCartData myShoppingCartData) {
            super.onPostExecute(myShoppingCartData);
            callBack.success(myShoppingCartData);
        }
    }
}
