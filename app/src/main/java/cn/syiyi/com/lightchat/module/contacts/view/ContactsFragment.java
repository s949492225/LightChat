package cn.syiyi.com.lightchat.module.contacts.view;


import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.syiyi.com.lightchat.R;
import cn.syiyi.com.lightchat.bean.ContactInfo;
import cn.syiyi.com.lightchat.module.contacts.adapter.ContactAdapter;
import cn.syiyi.com.lightchat.view.SideBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.recycleView)
    RecyclerView mRecycleView;
    @Bind(R.id.slideBar)
    SideBar mSlideBar;
    @Bind(R.id.tv_alert)
    TextView mTvAlert;
    private ContactAdapter mAdapter;
    private HashMap<Integer, ContactInfo.Contact> contactIdMap;
    boolean isInited;
    private MyAsyncQueryHandler asyncQueryHandler;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View content = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, content);
        return content;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isInited) {
            init();
        }
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.addItemDecoration(new ContactAdapter.ContactDecoration(getContext()));
        mAdapter = new ContactAdapter();
        mRecycleView.setAdapter(mAdapter);

        asyncQueryHandler = new MyAsyncQueryHandler(getContext().getContentResolver());
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
        // 查询的字段
        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID};
        // 按照sort_key升序查詢
        asyncQueryHandler.startQuery(0, null, uri, projection, null, null,
                "sort_key COLLATE LOCALIZED asc");
        mSlideBar.setTextView(mTvAlert);
        mSlideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int pos = mAdapter.getPositionByTitle(s);
                if (pos != -1) {
                    mRecycleView.scrollToPosition(pos);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                contactIdMap = new HashMap<Integer, ContactInfo.Contact>();
                ArrayList<ContactInfo.Contact> mData = new ArrayList<>();
                cursor.moveToFirst(); // 游标移动到第一项
                ContactInfo contacts = new ContactInfo();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(1);
                    String number = cursor.getString(2);
                    String sortKey = cursor.getString(3);
                    int contactId = cursor.getInt(4);
                    Long photoId = cursor.getLong(5);
                    if (contactIdMap.containsKey(contactId)) {
                        // 无操作
                    } else {
                        // 创建联系人对象
                        ContactInfo.Contact contact = new ContactInfo.Contact();
                        contact.setDesplayName(name);
                        contact.setPhoneNum(number);
                        contact.setPhotoId(photoId);
                        mData.add(contact);
                        contactIdMap.put(contactId, contact);
                    }
                }
                if (mData.size() > 0) {
                    contacts.setContacts(mData);
                    mAdapter.setData(contacts);
                }
            }

            super.onQueryComplete(token, cookie, cursor);
        }

    }
}
