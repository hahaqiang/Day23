package com.qianfeng.day23_service1.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/6/1.
 */
public class HttpUtil{
    public static InputStream getInputStreamFromNet(Context context, String url){
        //先判断当前网络是否可用
        boolean isOk = isNetOk(context);
        Log.d("HttpUtil", "bbb");
        if(!isOk){
            return null;
        }
        try{
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            if(conn.getResponseCode() == 200){
                return conn.getInputStream();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断当前网络是否可用
     *
     * @param context
     * @return
     */
    private static boolean isNetOk(Context context){
        //获取网络连接的管理器
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取已经Active的网络的信息
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info.isAvailable(); //返回当前网络是否可用
    }
}
