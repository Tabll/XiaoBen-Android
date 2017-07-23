package com.wts.xb;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.wts.xb.bean.ChatMessage;
import com.wts.xb.bean.ChatMessageAdaper;
import com.wts.xb.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XbActivity extends AppCompatActivity {

    private ListView XBmessages;
    private ChatMessageAdaper chatMessageAdaper;
    private List<ChatMessage> messages;

    private EditText imput_text;
    private Button sendbutton;

    private static final String TAG = "XbActivity";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //等待接收子线程完成数据的返回

            ChatMessage fromMessage = (ChatMessage) msg.obj;
            messages.add(fromMessage);
            chatMessageAdaper.notifyDataSetChanged();//表示数据源发送变化
            //super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xb);

        initView();//初始化所有的View
        initDatas();//初始化所有的数据
        initListener();//初始化事件
        Log.i(TAG, "小笨初始化已完成");
    }

    private void initListener() {
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String toMessage = imput_text.getText().toString();
                if(TextUtils.isEmpty(toMessage)){
                    Toast.makeText(XbActivity.this, "发送消息为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatMessage tocMessage = new ChatMessage();//将按钮中的数据也在List中显示
                tocMessage.setDate(new Date());
                tocMessage.setMsg(toMessage);
                tocMessage.setType(ChatMessage.Type.OUTCOMING);
                messages.add(tocMessage);
                chatMessageAdaper.notifyDataSetChanged();//通知更新

                imput_text.setText("");//清空输入框

                new Thread(){
                    public void run(){
                        ChatMessage fromMessage = HttpUtils.sendMessage(toMessage);
                        Message m = Message.obtain();
                        m.obj = fromMessage;
                        handler.sendMessage(m);
                    }
                }.start();

            }
        });
    }

    private void initDatas() {
        messages = new ArrayList<ChatMessage>();
        //messages.add(new ChatMessage("你好", ChatMessage.Type.INCOMING, new Date()));
        //messages.add(new ChatMessage("你好", ChatMessage.Type.OUTCOMING, new Date()));
        chatMessageAdaper = new ChatMessageAdaper(this, messages);

        XBmessages.setAdapter(chatMessageAdaper);
    }

    private void initView() {
        XBmessages = (ListView) findViewById(R.id.XB_list);
        imput_text = (EditText) findViewById(R.id.import_message);
        sendbutton = (Button) findViewById(R.id.send_button);
    }

    public void back(View view) {
        this.finish();
    }
}