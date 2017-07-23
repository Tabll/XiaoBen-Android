package com.wts.xb.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wts.xb.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wts on 2017/1/28.
 */

public class ChatMessageAdaper extends BaseAdapter{

    private LayoutInflater layoutInflater;
    private List<ChatMessage> chatMessages;

    public ChatMessageAdaper(Context context, List<ChatMessage> messages){
        layoutInflater = LayoutInflater.from(context);
        this.chatMessages = messages;
    }

    @Override
    public int getCount(){

        return chatMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return chatMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage = chatMessages.get(i);
        ViewHolder viewHolder = null;
        if(view == null){
            //通过ItemType设置不同的布局
            if(getItemViewType(i) == 0){//假如为0，则是小笨的消息
                view = layoutInflater.inflate(R.layout.from_message, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.date = (TextView) view.findViewById(R.id.from_date);
                viewHolder.message = (TextView) view.findViewById(R.id.from_message_info);
            }else {//假如为1，则是发送的消息
                view = layoutInflater.inflate(R.layout.to_message, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.date = (TextView) view.findViewById(R.id.to_date);
                viewHolder.message = (TextView) view.findViewById(R.id.to_message_info);
            }
            view.setTag(viewHolder);//存储一下ViewHolder
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //设置数据
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式
        viewHolder.date.setText(simpleDateFormat.format(chatMessage.getDate()));//获取时间
        viewHolder.message.setText(chatMessage.getMsg());//获取消息
        return view;
    }

    private final class ViewHolder{
        TextView date;
        TextView message;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        if(chatMessage.getType() == ChatMessage.Type.INCOMING){
            return 0;//接收消息
        }else {
            return 1;//发送消息
        }
    }
}
