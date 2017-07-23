package com.wts.xb.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wts.xb.bean.ChatMessage;
import com.wts.xb.bean.Result;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by wts on 2017/1/17.
 * 发送Http请求的一个工具类
 */

public class HttpUtils {
    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String XB_APIkey = "49f2b50b474174b487a9d88f3cea5ff2";

    public static ChatMessage sendMessage(String msg){
        ChatMessage chatMessage = new ChatMessage();

        String jsonRes = doGet(msg);
        Gson gson = new Gson();
        Result result = null;
        try {
            result = gson.fromJson(jsonRes, Result.class);
            chatMessage.setMsg(result.getText());
        }catch (JsonSyntaxException e){
            chatMessage.setMsg("小笨正忙");
        }
        chatMessage.setDate(new Date());//设置时间
        chatMessage.setType(ChatMessage.Type.INCOMING);//设置类型

        return chatMessage;
    }

    public  static  String doGet(String msg){
        String result="";
        String url = setParams(msg);//设置参数，返回一个Url
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try{
            java.net.URL urlNet = new java.net.URL(url);
            HttpURLConnection xb = (HttpURLConnection)urlNet.openConnection();
            xb.setReadTimeout(5000);
            xb.setConnectTimeout(5000);
            xb.setRequestMethod("GET");

            is = xb.getInputStream();

            int len = -1;
            byte[]buf = new byte[128];//缓冲区128个字节
            baos = new ByteArrayOutputStream();

            while ((len = is.read(buf))!=-1){
                baos.write(buf,0,len);
            }
            baos.flush();//清除缓冲区
            result = new String (baos.toByteArray());

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            try {
                if(is!=null){
                    is.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return result;
    }

    private static String setParams(String msg){
        String url = "";
        try {
            url = URL + "?key=" + XB_APIkey + "&info=" + URLEncoder.encode(msg, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return url;
    }
}
