package xyz.haijin.notice;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class VerticalRollTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private static final int FLAG_START_AUTO_SCROLL = 199;

    private float mTextSize = 16 ;
    private int stillTime = 3000;
    private int textColor = Color.BLACK;
    private int lines = -1;
    private boolean isRunning = false;

    private OnItemClickListener itemClickListener;
    private Context mContext;
    private int currentId = -1;
    private ArrayList<String> textList = new ArrayList<String>();

    public VerticalRollTextView(Context context) {
        this(context, null);
        mContext = context;
    }

    public VerticalRollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.VerticalRollTextView);
        textColor = a.getColor(R.styleable.VerticalRollTextView_rollTextColor, Color.BLACK);
        mTextSize = a.getDimension(R.styleable.VerticalRollTextView_rollTextSize, 16);
        lines = a.getInteger(R.styleable.VerticalRollTextView_rollLines,-1);
        textList = new ArrayList<String>();
    }

    public void setAnimTime(long animDuration) {
        if (getChildCount() != 2) {
            removeAllViews();
        }
        setFactory(this);
        Animation in = new TranslateAnimation(0, 0, 100, 0);
        in.setDuration(animDuration);
        in.setInterpolator(new AccelerateInterpolator());
        Animation out = new TranslateAnimation(0, 0, 0, -100);
        out.setDuration(animDuration);
        out.setInterpolator(new AccelerateInterpolator());
        setInAnimation(in);
        setOutAnimation(out);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (isRunning){
                handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
                SystemClock.sleep(stillTime);
            }
        }
    };

    private Handler handler =new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FLAG_START_AUTO_SCROLL&&textList != null) {
                if (textList.size() > 0) {
                    currentId++;
                    setText(textList.get(currentId % textList.size()));
                }
            }
        }
    };
    /**
     * 设置数据源
     * @param titles
     */
    public void setTextList(ArrayList<String> titles) {
        if (titles == null){
            isRunning = false;
            return;
        }
        if (textList.toString().equals(titles.toString())){
            return;
        }
        textList = titles;
        if (titles.size() == 1){
            currentId = 0;
            setText(textList.get(currentId % textList.size()));
            isRunning = false;
            return;
        }
        startAutoScroll();
    }

    /**
     * 开始滚动
     */
    public void startAutoScroll() {
        if (isRunning == false){
            isRunning = true;
            Thread thread = new Thread(runnable);
            currentId = -1;
            thread.start();
        }
    }

    /**
     * 停止滚动
     */
    public void stopAutoScroll() {
        isRunning = false;
    }

    @Override
    public View makeView() {
        TextView t = new TextView(mContext);
        t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        t.setLayoutParams(layoutParams);
        if (lines != -1) {
            t.setLines(lines);
        }
        t.setTextColor(textColor);
        t.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTextSize);
        t.setEllipsize(TextUtils.TruncateAt.END);
        t.setClickable(true);
        t.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null && textList.size() > 0 && currentId != -1) {
                    itemClickListener.onItemClick(currentId % textList.size());
                }
            }
        });
        return t;
    }

    /**
     * @param textSize 字号
     * @param textColor 字体颜色
     */
    public void setText(float textSize,int textColor) {
        mTextSize = textSize;
        this.textColor = textColor;
    }

    public void setStillTime(int stillTime){
        this.stillTime = stillTime;
    }

    /**
     * 设置点击事件监听
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 轮播文本点击监听器
     */
    public interface OnItemClickListener {
        /**
         * 点击回调
         * @param position 当前点击ID
         */
        void onItemClick(int position);
    }

}