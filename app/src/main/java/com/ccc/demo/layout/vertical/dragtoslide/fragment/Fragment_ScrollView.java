package com.ccc.demo.layout.vertical.dragtoslide.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccc.demo.layout.vertical.dragtoslide.R;
import com.ccc.lib.layout.vertical.dragtoslide.view.VerticalScrollView;


public class Fragment_ScrollView extends BaseFragment {

    private VerticalScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scrollview, container, false);
        scrollView = (VerticalScrollView) rootView.findViewById(R.id.scrollView);
        TextView oldTextView = (TextView) rootView.findViewById(R.id.old_textview);
        //设置删除线
        oldTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return rootView;
    }

    @Override
    public void goTop() {
        scrollView.goTop();
    }
}
