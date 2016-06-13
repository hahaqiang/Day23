package com.qianfeng.day23_service1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2016/6/1.
 */
public class MyService extends Service{
    /**
     * 当有其他组件绑定到这个服务的时候，回调这个方法
     * @param intent    绑定服务的时候使用的那个意图
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        Log.d("MyService", "onBind...");
        return new MyBinder();
    }

    /**
     * 如果想让第二个和以后的客户端绑定的时候调用onRebind，则返回true，否则返回false
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent){

        Log.d("MyService", "onUnbind..");
        return false;
    }

    @Override
    public void onRebind(Intent intent){
        Log.d("MyService", "onRebind...");
    }

    private class MyBinder extends Binder implements IService{
        
        @Override
        public void eat(){
            MyService.this.eat();
        }
        @Override
        public void sing(String name){
            MyService.this.sing(name);
        }


    }

    private void sing(String name){
        Log.d("MyService", "正在唱" + name);
    }

    private void eat(){
        Log.d("MyService", "吃饭");
    }

    /**
     * 当服务第一次启动或者第一次绑定的时候调用
     * 初始化的操作，由于这个方法是在主线程中执行的，所以，不能执行耗时操作
     */
    @Override
    public void onCreate(){
        Log.d("MyService", "创建服务" + Thread.currentThread().getName());
    }

    /**
     * 当启动服务的时候会调用这个方法.
     * 每次调用startService都会回调这个方法。
     * <p/>
     * 这个方法也是在主线程中执行，不能执行耗时操作
     *
     * @param intent  启动服务使用的那个Intent
     * @param flags   flags
     * @param startId Service的id
     * @return Service.START_STICKY 粘性服务 当非正常杀死进程后，自动启动的时候会回调onCreate和onStartCommand。 自动启动的时候，intent参数是null
     * Service.START_NOT_STICKY 非粘性服务  非正常杀死，不会自动启动
     * START_STICKY_COMPATIBILITY 可以兼容START_STICKY，但是不保证会调用onStartCommand。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //        Log.d("MyService", "服务启动了"+ Thread.currentThread().getName()  + intent );
        new Thread(){
            @Override
            public void run(){
                //模拟下载过程
                for(int i = 0; i < 100; i++){
                    Log.d("MyService", "i:" + i);
                    SystemClock.sleep(100);
                }
//                stopSelf(); //停止当前服务.  服务自杀
            }
        }.start();
        return Service.START_STICKY;
    }

    /**
     * 当服务停止的时候回调这个方法
     */
    @Override
    public void onDestroy(){
        Log.d("MyService", "服务停止了");
    }
}
