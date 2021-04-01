package com.example.chatroom;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MessageHolder extends RecyclerView.ViewHolder {
    private TextView otherUserName;
    private TextView otherMsg;
    private TextView otherTime;
    private ImageView otherImg;

    private TextView myMsg;
    private TextView myTime;
    private ImageView myImg;

    RelativeLayout myLayout, otherLayout;

    public MessageHolder(@NonNull View v) {
        super(v);
        otherUserName = (TextView) v.findViewById(R.id.otherUserName);
        otherMsg = (TextView) v.findViewById(R.id.otherMsg);
        otherTime = (TextView) v.findViewById(R.id.otherTime);
        myMsg = (TextView) v.findViewById(R.id.myMsg);
        myTime = (TextView) v.findViewById(R.id.myTime);

        myImg = (ImageView) v.findViewById(R.id.myImg);
        otherImg = (ImageView) v.findViewById(R.id.otherImg);

        myLayout =  (RelativeLayout) v.findViewById((R.id.myLayout));
        otherLayout = (RelativeLayout) v.findViewById(R.id.otherLayout);
    }

    public void setMessageValue(Message m, String uid){
        if(m != null){
            if(m.getUid().equals(uid)){
                myLayout.setVisibility(View.VISIBLE);
                otherLayout.setVisibility(View.GONE);
                myMsg.setText(m.getMessage());
                myTime.setText(String.valueOf(new SimpleDateFormat("hh:mm a", Locale.CHINESE).format(m.getTime())));
            }
            else{
                otherLayout.setVisibility(View.VISIBLE);
                myLayout.setVisibility(View.GONE);
                otherUserName.setText(m.getUserName());
                otherMsg.setText(m.getMessage());
                otherTime.setText(String.valueOf(new SimpleDateFormat("hh:mm a", Locale.CHINESE).format(m.getTime())));
            }
        }
    }
}

