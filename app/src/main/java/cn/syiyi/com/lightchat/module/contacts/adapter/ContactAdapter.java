package cn.syiyi.com.lightchat.module.contacts.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.syiyi.com.lightchat.R;
import cn.syiyi.com.lightchat.bean.ContactInfo;
import cn.syiyi.com.lightchat.module.contacts.holder.ContactItemHolder;
import cn.syiyi.com.lightchat.module.contacts.holder.SessonItemHolder;
import cn.syiyi.com.lightchat.util.ViewUtil;

/**
 * Created by songlintao on 16/1/25.
 */
public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ContactInfo mData;

    public ContactAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ContactInfo.TYPE_SESSION) {
            return new SessonItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_title, parent, false));
        } else if (viewType == ContactInfo.TYPE_CONTACT) {
            return new ContactItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.getType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContactItemHolder) {
            ContactItemHolder cHolder = (ContactItemHolder) holder;
            ContactInfo.Contact contact = (ContactInfo.Contact) mData.getItem(position);
            cHolder.mTv_name.setText(contact.getDesplayName());
            cHolder.mTv_msg.setText(contact.getPhoneNum());
        } else if (holder instanceof SessonItemHolder) {
            SessonItemHolder sHolder = (SessonItemHolder) holder;
            String title = (String) mData.getItem(position);
            sHolder.mTitle.setText(title);
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.getCount();
        } else {
            return 0;
        }
    }

    public void setData(ContactInfo mData) {
        this.mData = mData;
        notifyDataSetChanged();

    }

    public int getPositionByTitle(String s) {
        if (mData != null && mData.getItems() != null) {
            List<Object> titles = mData.getItems();
            for (int i = 0; i < titles.size(); i++) {
                if ((titles.get(i) instanceof String) && titles.get(i).toString().equals(s)) {
                    return i;
                }
            }
            return -1;
        } else {
            return -1;
        }
    }

    public static class ContactDecoration extends RecyclerView.ItemDecoration {
        private Context context;
        private static final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        private Drawable mDivider;

        public ContactDecoration(Context ctx) {
            context = ctx;
            final TypedArray a = ctx.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int margin = ViewUtil.dpi2px(context, 16);
            final int left = parent.getPaddingLeft() + margin;
            final int right = parent.getWidth() - parent.getPaddingRight() - margin - ViewUtil.dpi2px(context, 6);
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                Object object = parent.getChildViewHolder(child);
                if (!(object instanceof SessonItemHolder)) {
                    if (i < childCount - 1) {
                        View nextView = parent.getChildAt(i + 1);
                        Object nextObject = parent.getChildViewHolder(nextView);
                        if (!(nextObject instanceof SessonItemHolder)) {
                            mDivider.draw(c);
                        }
                    }

                }

            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }
}
