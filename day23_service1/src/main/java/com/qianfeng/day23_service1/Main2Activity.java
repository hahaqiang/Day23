package com.qianfeng.day23_service1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName name, IBinder service){
            }

            @Override
            public void onServiceDisconnected(ComponentName name){
            }
        }, BIND_AUTO_CREATE);
    }
}
