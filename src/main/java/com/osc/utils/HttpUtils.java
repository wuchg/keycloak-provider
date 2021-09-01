package com.osc.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author WuChengguo
 */
public class HttpUtils {
    public static void postData(String data){
        if (data == null || "".equals(data.trim())) {
            System.out.println("data 为空！");
            return;
        }
        String notifyURL = System.getenv("NOTIFY_URL");
        if(notifyURL == null){
            System.out.println("notifyURL 为空！");
            return;
        }
        String applicationId = System.getenv("APPLICATION_ID");
               applicationId = null == applicationId || "".equals(applicationId.trim()) ? "proxima-core" : applicationId;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(notifyURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(2*1000);
            conn.setRequestProperty("X-Parse-Application-Id",applicationId);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = data.getBytes("UTF-8");
                os.write(input, 0, input.length);
                os.flush();
            }
            conn.connect();
            System.out.println(conn.getResponseCode()+"<<>>"+conn.getResponseMessage());
        }catch (Exception e){
           //TODO
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }
    }
}
