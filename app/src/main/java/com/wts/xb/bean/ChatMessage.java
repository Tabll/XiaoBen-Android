package com.wts.xb.bean;

import java.util.Date;

/**
 * Created by wts on 2017/1/17.
 * 与小笨聊天的内容
 */

public class ChatMessage {
    private String name;
    private String msg;
    private Type type;
    private Date date;

    //定义消息类型
    public enum Type{
        INCOMING,OUTCOMING
    }

    //构造方法
    public ChatMessage(){
    }
    public ChatMessage(String msg, Type type, Date date) {
        super();
        this.msg = msg;
        this.type = type;
        this.date = date;
    }//构造方法结束

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public Type getType(){
        return type;
    }

    public void setType(Type type){
        this.type = type;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

}
