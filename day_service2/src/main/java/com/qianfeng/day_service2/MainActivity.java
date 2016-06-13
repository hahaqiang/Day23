package com.qianfeng.day_service2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        //显示意图：  参数1：要启动的那个Service所在的app的包名  参2：Service的全名
        intent.setClassName("com.qianfeng.day23_service1", "com.qianfeng.day23_service1.MyService");
        startService(intent);
    }
}
