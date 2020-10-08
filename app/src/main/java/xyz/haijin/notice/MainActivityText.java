package xyz.haijin.notice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityText extends AppCompatActivity implements View.OnClickListener {

    View mButton;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_text);
        init();
    }

    private void init() {
        mButton = findViewById(R.id.submit);
        mButton.setOnClickListener(this);
        text = findViewById(R.id.msg);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.submit){
            Intent it=new Intent();
            it.setClass(MainActivityText.this,MainActivity.class);//从MainActivity跳转到AfterLoginActivity
            it.putExtra("msg", text.getText().toString().trim());//给intent添加数据，函数第一个参数是key,第二个是值。
            startActivity(it);
        }
    }
}
