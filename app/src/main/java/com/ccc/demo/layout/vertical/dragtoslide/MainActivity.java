package com.ccc.demo.layout.vertical.dragtoslide;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ccc.demo.layout.vertical.dragtoslide.fragment.BaseFragment;
import com.ccc.demo.layout.vertical.dragtoslide.fragment.Fragment_GridView;
import com.ccc.demo.layout.vertical.dragtoslide.fragment.Fragment_ListView;
import com.ccc.demo.layout.vertical.dragtoslide.fragment.Fragment_RecyclerView;
import com.ccc.demo.layout.vertical.dragtoslide.fragment.Fragment_ScrollView;
import com.ccc.demo.layout.vertical.dragtoslide.fragment.Fragment_ViewPager;
import com.ccc.lib.layout.vertical.dragtoslide.CCVerticalDragToSlideLayout;
import com.ccc.lib.layout.vertical.dragtoslide.util.CCLogUtil;

/**
 * Created by CodingCodersCode on 2018/1/17.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = getClass().getCanonicalName();

    private CCVerticalDragToSlideLayout verticalSlide;

    private BaseFragment fragment1;
    private BaseFragment fragment2;
    private BaseFragment fragment3;
    private BaseFragment fragment4;
    private BaseFragment fragment5;

    private FloatingActionButton fab;


    private TextView topTv;
    private TextView bottomTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        this.topTv = (TextView) findViewById(R.id.top);
        this.topTv.setOnClickListener(this);

        this.bottomTv = (TextView) findViewById(R.id.bottom);
        this.bottomTv.setOnClickListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        fragment1 = new Fragment_ScrollView();
        fragment2 = new Fragment_ListView();
        fragment3 = new Fragment_GridView();
        fragment4 = new Fragment_RecyclerView();
        fragment5 = new Fragment_ViewPager();
        transaction.replace(R.id.first, fragment1);
        transaction.replace(R.id.second, fragment2);
        transaction.replace(R.id.three, fragment3);
        transaction.replace(R.id.four, fragment4);
        transaction.replace(R.id.five, fragment5);

        transaction.commit();

        CCVerticalDragToSlideLayout.OnShowNextPageListener nextIntf = new CCVerticalDragToSlideLayout.OnShowNextPageListener() {
            @Override
            public void onShowNextPage(int nextView) {
                switch (nextView) {
                    case 0:

                        break;
                    case 1:
                        //fragment2.initView();
                        break;
                    case 2:

                        break;
                    case 3:
                        //fragment4.initView();
                        break;
                    case 4:

                        break;
                }

                printLog(LOG_TAG, "onShowNextPage(int nextView)方法，nextView=" + nextView);
            }
        };
        verticalSlide = (CCVerticalDragToSlideLayout) findViewById(R.id.dragLayout);
        verticalSlide.setOnShowNextPageListener(nextIntf);

        //开启日志打印
        CCLogUtil.setDebugAble(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top:
                if (verticalSlide != null) {
                    verticalSlide.showPreviousPage();
                }
                break;
            case R.id.bottom:
                if (verticalSlide != null) {
                    verticalSlide.showNextPage();
                }
                break;
        }
    }

    private void printLog(String tag, String msg) {
        Log.e(tag, msg);
    }
}
