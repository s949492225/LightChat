package cn.syiyi.com.lightchat.module.mine;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sanjay.selectorphotolibrary.SelectedPhotoActivity;
import com.example.sanjay.selectorphotolibrary.bean.ImgOptions;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.syiyi.com.lightchat.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {

    private static final String EXTRA_DATA = "extra_data";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.tv_result)
    TextView mTvResult;
    private boolean isInited;
    @Bind(R.id.btn_openImage)
    Button mBtnOpenImage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
        View content = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, content);
        return content;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isInited) {
            mBtnOpenImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImgOptions options = new ImgOptions(ImgOptions.MODE_MULTI, true);
                    startActivityForResult(SelectedPhotoActivity.makeIntent(getContext(), options), 0);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<String> imgs = data.getStringArrayListExtra(EXTRA_DATA);
                StringBuilder builder = new StringBuilder();
                for (String img : imgs) {
                    builder.append(img + "\r\n");
                }
                mTvResult.setText(builder.toString());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
