package com.qianfeng.day23_listenphone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/1.
 */
public class ListenPhoneService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //来完成监听手机通过的功能
        //获取TelephonyManager对象，里面提供了可以监听电话的功能
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //开启监听。  参数1：监听的监听器  参数2：想监听的电话哪个方面
        manager.listen(new Listner(), PhoneStateListener.LISTEN_CALL_STATE);
        return super.onStartCommand(intent, flags, startId);
    }

    class Listner extends PhoneStateListener{

        private MediaRecorder recorder;

        private String getFile(String incomingNumber){
            SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
            String dateString = format.format(new Date());
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/system_file";
            File dir = new File(path);
            dir.mkdirs();
            return dir.getAbsolutePath() + "/" + incomingNumber + "_" + dateString;
        }

        /**
         * 当电话的状态发生改变的时候回调这个方法
         *
         * @param state          手机的改变之后的状态
         *                       Call有三种：待机、响铃、通话
         * @param incomingNumber 来电号码
         */
        @Override
        public void onCallStateChanged(int state, String incomingNumber){
            //待机状态
            if(state == TelephonyManager.CALL_STATE_IDLE){
                if(recorder != null){
                    recorder.stop();  //停止录音
                    recorder.reset();
                    recorder.release(); //释放资源
                    recorder = null;
                }
            }else if(state == TelephonyManager.CALL_STATE_RINGING){ //响铃状态
                //响铃的时候准备好录音
                //创建多媒体MediaRecorder对象
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);  //设置要录制的声音的来源
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); //设置音频输出的格式
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);  //设置音频编码格式
                recorder.setOutputFile(getFile(incomingNumber));  //设置录音保存的路径
                try{
                    recorder.prepare();  //开始准备录音设备
                }catch(IOException e){
                    e.printStackTrace();
                }
            }else if(state == TelephonyManager.CALL_STATE_OFFHOOK){  //通话状态
                if(recorder != null){
                    Toast.makeText(ListenPhoneService.this, "开始录音", Toast.LENGTH_SHORT).show();
                    recorder.start(); //开始录音
                }
            }
        }
    }
}
