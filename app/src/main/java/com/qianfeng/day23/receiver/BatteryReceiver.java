package com.qianfeng.day23.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/6/1.
 */
public class BatteryReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        String action = intent.getAction();
        Toast.makeText(context, action, Toast.LENGTH_SHORT).show();
    }
}
