package xyz.haijin.notice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeViewHolder>{
    private Context mContext;

    List<String> mDatas = new ArrayList<>();

    public void setmDatas(List<String> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new NoticeViewHolder(mContext,ViewUtils.inflate(parent, R.layout.item_user_notice));
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder holder, final int position) {
        final  int tempPos = position%(mDatas.size());
        holder.setTextViewValue(mDatas.get(tempPos));

    }

    @Override
    public int getItemCount() {
        return 100000;
    }
}

class NoticeViewHolder extends RecyclerView.ViewHolder{
    private Context mContext;
    private TextView textView;

    public NoticeViewHolder(Context context,View itemView) {
        super(itemView);
        mContext = context;
        textView = (TextView) itemView.findViewById(R.id.tab_title);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogUtils(mContext);
//                Toast toast =    Toast.makeText(MainActivity.this,mDatas.get(tempPos),Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER,0,0);
//                toast.show();
            }
        });
    }

    public void setTextViewValue(String data){
        textView.setText(data);
    }


}
