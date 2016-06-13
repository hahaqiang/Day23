package com.qianfeng.day23_service1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity{

//    private MyService.MyBinder binder;
    private ServiceConnection connection;

    private IService iService;

    public void another(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public void invoke(View view){
       /* binder.eat();
        binder.sing("小苹果");*/
        iService.eat();
        iService.sing("小苹果");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bind(View view){
        if(connection == null){
            connection = new MyServiceConnection();
        }
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

    }
    public void unbind(View view){
        //绑定使用的那个ServiceConnection
        if(connection != null){
            unbindService(connection);
            connection = null;

        }
    }




    public class MyServiceConnection implements ServiceConnection{



        /**
         * 当bind服务成功后，调用
         * @param name  绑定那个服务的类的类名
         * @param service   在附中的onBind方法返回的那个IBinder类型对象
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){
//            binder = (MyService.MyBinder) service;
            iService = (IService) service;

            Log.d("MyServiceConnection", "连接成功");
        }

        /**
         * 非正常情况失去连接的时候调用
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name){
            Log.d("MyServiceConnection", "失去连接");
        }
    }







    public void start(View view){
        //启动服务 startService
        //        Intent intent = new Intent(this, MyService.class);
        //从5.0开始，android禁止使用隐式意图启动服务。
        Intent intent = new Intent();
        //        intent.setAction("xxx");
        intent.setClassName("com.qianfeng.day23_service1", "com.qianfeng.day23_service1.MyService");
        startService(intent);
    }

    public void stop(View view){
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);  //停止服务
    }

    public void downLoad(View view){
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("url", "http://www.qq.com");
        startService(intent);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(connection != null){
            unbindService(connection);
            connection = null;
        }
    }
}
