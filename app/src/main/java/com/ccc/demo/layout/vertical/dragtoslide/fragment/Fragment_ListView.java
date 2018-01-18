package com.ccc.demo.layout.vertical.dragtoslide.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.ccc.demo.layout.vertical.dragtoslide.R;
import com.ccc.demo.layout.vertical.dragtoslide.util.ColorUtil;
import com.ccc.lib.layout.vertical.dragtoslide.view.VerticalListView;

import java.util.ArrayList;
import java.util.List;

public class Fragment_ListView extends BaseFragment {

    private VerticalListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        listview = (VerticalListView) rootView.findViewById(R.id.listView);

        TextView textView1 = new TextView(getActivity());
        textView1.setText("header1");
        textView1.setGravity(Gravity.CENTER);
        textView1.setTextColor(Color.WHITE);
        textView1.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        textView1.setBackgroundColor(Color.GRAY);
        TextView textView2 = new TextView(getActivity());
        textView2.setText("header2");
        textView2.setGravity(Gravity.CENTER);
        textView2.setTextColor(Color.WHITE);
        textView2.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        textView2.setBackgroundColor(Color.DKGRAY);
        TextView textView3 = new TextView(getActivity());
        textView3.setText("footer1");
        textView3.setGravity(Gravity.CENTER);
        textView3.setTextColor(Color.WHITE);
        textView3.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        textView3.setBackgroundColor(Color.GRAY);
        listview.addHeaderView(textView1);
        listview.addHeaderView(textView2);
        listview.addFooterView(textView3);

        listview.setAdapter(new MyAdapter());
        return rootView;
    }

    @Override
    public void goTop() {
        listview.goTop();
    }

    private class MyAdapter extends BaseAdapter {

        private List<String> strings;

        public MyAdapter() {
            strings = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                strings.add("条目" + i);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = View.inflate(getActivity(), android.R.layout.simple_list_item_1, null);
            }
            TextView textView = (TextView) convertView;
            textView.setText(getItem(position));
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundColor(ColorUtil.generateBeautifulColor());
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public String getItem(int position) {
            return strings.get(position);
        }

        @Override
        public int getCount() {
            return strings.size();
        }
    }
}
