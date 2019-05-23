package com.zhiyi.chinaipo.ui.fragment.news;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiyi.chinaipo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页视频页
 */
@SuppressLint({"NewApi", "ValidFragment"})
public class VideoItemFragment extends Fragment {

    private RecyclerView mRvVideo;
    int netType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vieo_item, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
//
//        mRvVideo= (RecyclerView) v.findViewById(R.id.rv_video);
//        mVideosList=new ArrayList<>();
//        mVideosList=data();
//        mVideosAdapter=new VideosAdapter(getActivity(),mVideosList);
//        mRvVideo.setAdapter(mVideosAdapter);
//        mRvVideo.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }

}
