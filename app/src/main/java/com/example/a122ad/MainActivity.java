package com.example.a122ad;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // 直接跳转到笔记列表页面
            Intent intent = new Intent(this, NoteListActivity.class);
            startActivity(intent);
            finish(); // 结束当前Activity
        } catch (Exception e) {
            e.printStackTrace();
            // 显示错误消息并退出应用，避免用户看到空白界面
            finish();
        }
    }
}