package cn.syiyi.com.lightchat.module.recent.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.syiyi.com.lightchat.R;
import cn.syiyi.com.lightchat.module.chat.view.ChatActivity;
import cn.syiyi.com.lightchat.module.recent.holder.RecentItemHolder;
import cn.syiyi.com.lightchat.util.ViewUtil;

/**
 * LightChat...........
 * ${PACKAGE_NAME}...........
 -------------------------------
 * Created by songlintao on 16/1/25.
 */
public class RecentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public RecentAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecentItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public static class ChatDecoration extends RecyclerView.ItemDecoration {
        private Context context;
        private static final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        private Drawable mDivider;

        public ChatDecoration(Context ctx) {
            context = ctx;
            final TypedArray a = ctx.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int margin = ViewUtil.dpi2px(context, 16);
            final int left = parent.getPaddingLeft() + margin;
            final int right = parent.getWidth() - parent.getPaddingRight() - margin;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }
}
