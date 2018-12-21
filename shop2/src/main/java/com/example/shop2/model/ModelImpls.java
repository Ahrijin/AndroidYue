package com.example.shop2.model;

import android.os.AsyncTask;

import com.example.shop2.bean.MyData;
import com.example.shop2.callback.MyCallBack;
import com.example.shop2.utils.OkHttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

public class ModelImpls implements Model {

    private MyCallBack callBack;
    @Override
    public void getData(String url, MyCallBack callBack) {
        this.callBack = callBack;
        new MyTask().execute(url);
    }

    class MyTask extends AsyncTask<String ,Void ,MyData>{
        @Override
        protected MyData doInBackground(String... strings) {
            try {
                String jsonStr = OkHttpUtils.getInstance().get(strings[0]);
                Gson gson = new Gson();
                MyData myData = gson.fromJson(jsonStr, MyData.class);
                return myData;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(MyData myData) {
            super.onPostExecute(myData);
            callBack.success(myData);
        }
    }


}
