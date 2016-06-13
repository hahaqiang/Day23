package com.qianfeng.day23_service1.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/6/1.
 */
public class InputStreamUtil{
    public static String StreamToString(InputStream is, String charset){
        int len = -1;
        byte[] buff = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try{
            while((len = is.read(buff)) != -1){
                sb.append(new String(buff, 0, len, charset));
            }
            return sb.toString();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
