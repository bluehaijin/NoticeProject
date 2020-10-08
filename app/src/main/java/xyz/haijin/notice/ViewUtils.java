package xyz.haijin.notice;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Administrator on 2017-2-11.
 */

public class ViewUtils {
    private static final String TAG = "ViewUtils";

    public static void setText(TextView view, String name) {
        if (view != null && name != null) {
            String key = name;
            if (!key.startsWith("s_")) {
                key = "s_" + name;
            }
            int resId = getStringResId(view.getContext(), key);
            if (resId != 0) {
                view.setText(resId);
            } else {
                view.setText(name);
            }
        } else if (view != null) {
            view.setText(null);
        }
    }


    public static void setDrawableTop(TextView view, String name) {
        int resId = getDrawableResId(view.getContext(), name);
        if (resId != 0) {
            view.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
        }
    }

    private static int getStringResId(Context context, String name) {
        int resId = 0;
        if (!TextUtils.isEmpty(name) && name.startsWith("s_")) {
            resId = context.getResources().getIdentifier(name, "string", context.getPackageName());
        }
        return resId;
    }

    private static int getDrawableResId(Context context, String name) {
        if (!name.startsWith("i_")) {
            name = "i_" + name;
        }
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static void transparentStatusBar(Activity activity) {
        Window window = activity.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static View inflate(ViewGroup parent, int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    public static void setHeight(View view, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params.height != height) {
            params.height = height;
            view.setLayoutParams(params);
        }
    }

    public static void setLeftMargin(View view, int left) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (params.leftMargin != left) {
            params.leftMargin = left;
            view.setLayoutParams(params);
        }
    }

    public static void setBottomMargin(View view, int bottom) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (params.bottomMargin != bottom) {
            params.bottomMargin = bottom;
            view.setLayoutParams(params);
        }
    }

    public static void setTopMargin(View view, int top) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (params.topMargin != top) {
            params.topMargin = top;
            view.setLayoutParams(params);
        }
    }

    public static int getWidth(View view) {
        return view != null ? view.getLayoutParams().width : 0;
    }

    public static int getHeight(View view) {
        return view != null ? view.getLayoutParams().height : 0;
    }

    public static void showStatusBar(Activity activity) {
        if (activity != null) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        }
    }

    public static void hideStatusBar(Activity activity) {
        if (activity != null) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        }
    }
}
