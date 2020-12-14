package com.example.groups_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

public class groupChatActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageButton sendMesageButton;
    private EditText userMessageInput;
    private ScrollView mScrollView;
    private TextView displayTextMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        InitializeFields();


    }

    private void InitializeFields() {

        //mToolbar = (Toolbar) findViewById(R.id.group_chat_toolbar);
        sendMesageButton = (ImageButton) findViewById(R.id.send_message_button);
        userMessageInput = (EditText) findViewById(R.id.input_group_message);
        mScrollView = (ScrollView) findViewById(R.id.group_chat_scroll_view);
        displayTextMessages = (TextView) findViewById(R.id.group_chat_text_disp);





    }
}