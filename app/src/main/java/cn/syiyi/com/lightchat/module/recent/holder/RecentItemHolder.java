package cn.syiyi.com.lightchat.module.recent.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.syiyi.com.lightchat.R;

/**
 * Created by songlintao on 16/1/25.
 */
public class RecentItemHolder extends RecyclerView.ViewHolder {
    public ImageView mIv_icon;
    public TextView mTv_name;
    public TextView mTv_time;
    public TextView mTv_msg;
    public RecentItemHolder(View itemView) {
        super(itemView);
        mIv_icon= (ImageView) itemView.findViewById(R.id.iv_icon);
        mTv_name= (TextView) itemView.findViewById(R.id.tv_name);
        mTv_time= (TextView) itemView.findViewById(R.id.tv_time);
        mTv_msg= (TextView) itemView.findViewById(R.id.tv_msg);
    }


}
