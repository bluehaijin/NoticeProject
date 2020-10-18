package xyz.haijin.notice;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;


public class DialogUtils {
    private Context mContext;
    private ImageView imageView;

    public DialogUtils(Context context){
        mContext = context;
        init();
    }

    private void init() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogLayout = inflater.inflate(R.layout.dialog_showimage, null);

        imageView = dialogLayout.findViewById(R.id.imageView);

        final AlertDialog dialog = builder
                .setView(dialogLayout) //自定义的布局文件
                .create();
        dialog.show();
        dialog.getWindow().findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); //取消对话框
            }
        });
    }

}
