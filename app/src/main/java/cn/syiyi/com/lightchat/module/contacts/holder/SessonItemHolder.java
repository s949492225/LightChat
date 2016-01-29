package cn.syiyi.com.lightchat.module.contacts.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.syiyi.com.lightchat.R;

/**
 * Created by songlintao on 16/1/25.
 */
public class SessonItemHolder extends RecyclerView.ViewHolder {
    public TextView mTitle;

    public SessonItemHolder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.tv_title);
    }


}
