package xyz.haijin.notice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private  VerticalRollTextView verticalRollTextView;
    private Context mContext;

    //公告
    private RecyclerView notice;//公告
    private List<String> mDatas;//公告信息
    private boolean flag = false;//公告栏 信息控制切换页面时，公告是否滚动

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mContext = this;

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();///获取前面的那个activity传过来的数据
        setData(bundle.getString("msg"));

        init();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//设置屏幕常亮

        Intent intentMusic = new Intent(MainActivity.this,MusicServer.class);
        startService(intentMusic);

//        verticalRollTextView.startAutoScroll();
    }

    protected void onStop(){
        Intent intent = new Intent(MainActivity.this,MusicServer.class);
        stopService(intent);
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        onStop();
    }

    /**
     * 公告
     */
    private void noticeFunction(){
        initNotice();
    }

    private void init() {
        notice = findViewById(R.id.user_notice);
        noticeFunction();//初始化公告
    }

    /**
     * 公告信息初始化
     */
    private void setData(String msg){
        mDatas = new ArrayList<>();
        mDatas.add(msg+" ");
    }


    /**
     * 公告栏 信息时间控制
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0x00){
                notice.scrollBy(notice.getScrollX()+8,notice.getScrollY());
                handler.sendEmptyMessageDelayed(0x00, 10);
                if (!flag) {
//                    handler.sendEmptyMessageDelayed(0x00, 10);
////                    Log.d(TAG, "handleMessage: 发送消息");
//                    handler.sendEmptyMessageDelayed(0x01, 1000);
//                    handler.sendEmptyMessageDelayed(0x02, 1000);
//                    handler.sendEmptyMessageDelayed(0x02, 10);
//                    flag = true;
                }
            }
            else if(msg.what==0x01){//刷新页面数据
                handler.sendEmptyMessageDelayed(0x01, 50);
            }
        }
    };

    /**
     * 初始化公告
     */
    private void initNotice(){
        notice = findViewById(R.id.user_notice);
        notice.setOnClickListener(this);
//        initData();
        NoticeAdapter noticeAdapter = new NoticeAdapter();
        noticeAdapter.setmDatas(mDatas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        notice.setLayoutManager(linearLayoutManager);
        notice.setAdapter(noticeAdapter);
        noticeAdapter.notifyDataSetChanged();
        handler.sendEmptyMessageDelayed(0x00,10);
        handler.sendEmptyMessageDelayed(0x01,10);
        handler.sendEmptyMessageDelayed(0x02,1000);
    }

    @Override
    public void onClick(View v) {

    }
}
