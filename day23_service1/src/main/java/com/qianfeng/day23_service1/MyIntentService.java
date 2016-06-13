package com.qianfeng.day23_service1;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.qianfeng.day23_service1.http.HttpUtil;
import com.qianfeng.day23_service1.util.InputStreamUtil;

import java.io.InputStream;

public class MyIntentService extends IntentService{
    //服务必须与无参构造方法
    public MyIntentService(){
        super("下载线程");
    }


    /**
     * 专门处理异步任务的方法。 这个方法是在子线程中执行，所以随便执行耗时操作。
     * 一旦这个方法执行完毕，当前服务会自动停止，不需要我们手动去控制
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent){
        Log.d("MyIntentService", Thread.currentThread().getName());
        //完成下载的动作
        String url = intent.getStringExtra("url");
        if(TextUtils.isEmpty(url)){  //如果是null，则代表没有提供有效的url，则直接返回，不进一步处理
            return;
        }

        InputStream is = HttpUtil.getInputStreamFromNet(this, url);
        Log.d("MyIntentService", "aaaa" + is);
        String content = InputStreamUtil.StreamToString(is, "gbk");
        Log.d("MyIntentService", content);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyIntentService", "服务自动停止");
    }
}
