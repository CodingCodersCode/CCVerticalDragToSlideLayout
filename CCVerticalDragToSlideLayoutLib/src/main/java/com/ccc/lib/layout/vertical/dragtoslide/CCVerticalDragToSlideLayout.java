package com.ccc.lib.layout.vertical.dragtoslide;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ccc.lib.layout.vertical.dragtoslide.util.CCLogUtil;


/**
 * Created by CodingCodersCode on 2018/1/17.
 * <p>
 * 垂直拖拽滑动布局，仿原淘宝详情页，支持多页
 */

public class CCVerticalDragToSlideLayout extends ViewGroup {

    private final String LOG_TAG = getClass().getCanonicalName();

    // 滑动速度的阈值，超过这个绝对值认为是上下
    private static final int VEL_THRESHOLD = 6000;
    // 单位是dp，当上下滑动速度不够时，通过这个阈值来判定是应该粘到顶部还是底部
    private int DISTANCE_THRESHOLD = 60;

    private ViewDragHelper mDragHelper;
    private GestureDetectorCompat mGestureDetector;

    // 手指松开是否加载下一页的监听器
    private OnShowNextPageListener nextPageListener;

    //上面的视图
    private View mTopView;
    //中间的视图
    private View mMiddleView;
    //下面的视图
    private View mBottomView;

    public CCVerticalDragToSlideLayout(Context context) {
        this(context, null);
    }

    public CCVerticalDragToSlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CCVerticalDragToSlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        DISTANCE_THRESHOLD = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DISTANCE_THRESHOLD, getResources().getDisplayMetrics());

        mDragHelper = ViewDragHelper.create(this, 10f, new DragCallBack());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);

        mGestureDetector = new GestureDetectorCompat(getContext(), new YScrollDetector());

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //初始化上面的可拖动视图
        this.mTopView = getChildAtIndex(-1);
        //初始化中间的可拖动视图
        this.mMiddleView = getChildAtIndex(0);
        //初始化下面的可拖动视图
        this.mBottomView = getChildAtIndex(1);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View childView;

        if (!changed) {
            return;
        }

        for (int childIndex = 0; childIndex < getChildCount(); childIndex++) {

            childView = getChildAt(childIndex);

            /*if (childView.getVisibility() == View.GONE) {
                continue;
            }*/

            childView.layout(0, 0, r - l, b - t);

            if (childView == mTopView) {
                if (mMiddleView.getTop() == 0) {
                    mTopView.layout(0, -mTopView.getMeasuredHeight(), r - l, 0);
                } else {
                    mTopView.layout(mTopView.getLeft(), mTopView.getTop(), mTopView.getRight(), mTopView.getBottom());
                }
            } else if (childView == mMiddleView) {
                if (mMiddleView.getTop() == 0) {
                    mMiddleView.layout(0, 0, r - l, b - t);
                } else {
                    mMiddleView.layout(mMiddleView.getLeft(), mMiddleView.getTop(), mMiddleView.getRight(), mMiddleView.getBottom());
                }

            } else if (childView == mBottomView) {
                if (mMiddleView.getTop() == 0) {
                    mBottomView.offsetTopAndBottom(mMiddleView.getMeasuredHeight());
                } else {
                    mBottomView.layout(mBottomView.getLeft(), mBottomView.getTop(), mBottomView.getRight(), mBottomView.getBottom());
                }
            } else {
                childView.offsetTopAndBottom(mMiddleView.getMeasuredHeight());
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean shouldIntercept = false;
        boolean yScroll = mGestureDetector.onTouchEvent(ev);
        try {
            shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev);
        } catch (Exception e) {
            CCLogUtil.printLog("e", LOG_TAG, "系统回调onInterceptTouchEvent时将MotionEvent转交ViewDragHelper.shouldInterceptTouchEvent处理发生异常，详情见异常信息", e);
        }
        return shouldIntercept && yScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            CCLogUtil.printLog("e", LOG_TAG, "系统回调onTouchEvent时将MotionEvent转交ViewDragHelper.processTouchEvent处理发生异常，详情见异常信息", e);
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 显示下一页
     */
    public void showNextPage() {
        try {
            forceAnimTopOrBottom(mMiddleView, 1);
        } catch (Exception e) {
            CCLogUtil.printLog("e", LOG_TAG, "调用showNextPage方法显示下一页发生异常，详情见异常信息", e);
        }
    }

    /**
     * 显示上一页
     */
    public void showPreviousPage() {
        try {
            forceAnimTopOrBottom(mMiddleView, 2);
        } catch (Exception e) {
            CCLogUtil.printLog("e", LOG_TAG, "调用showPreviousPage方法显示上一页发生异常，详情见异常信息", e);
        }
    }

    private View getChildAtIndex(int viewIndex) {
        if (viewIndex >= 0 && viewIndex < getChildCount()) {
            return getChildAt(viewIndex);
        } else {
            return null;
        }
    }

    private void onViewPosChanged(View changedView, int left, int top, int dx, int dy) {

        View tmpMiddleView = changedView;
        View tmpTopView = getChildAtIndex(indexOfChild(tmpMiddleView) - 1);
        View tmpBottomView = getChildAtIndex(indexOfChild(tmpMiddleView) + 1);

        if (tmpTopView != null) {
            int offsetTopBottom = tmpMiddleView.getTop() - tmpTopView.getMeasuredHeight() - tmpTopView.getTop();
            tmpTopView.offsetTopAndBottom(offsetTopBottom);

            //tmpTopView.offsetTopAndBottom(dy);//出现页面变空白的具体原因待查
        }

        if (tmpBottomView != null) {
            int offsetTopBottom = tmpMiddleView.getMeasuredHeight() + tmpMiddleView.getTop() - tmpBottomView.getTop();
            tmpBottomView.offsetTopAndBottom(offsetTopBottom);

            //tmpBottomView.offsetTopAndBottom(dy);//出现页面变空白的具体原因待查
        }
        // 如果不重绘，拖动的时候，其他View会不显示
        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 强制执行动画，平缓滑动到顶部或底部
     *
     * @param releaseChild 释放的view
     * @param direction    滑动方向，0——不变，1——向上，2——向下
     */
    private void forceAnimTopOrBottom(View releaseChild, int direction) {
        switch (direction) {
            case 0:

                break;
            case 1:
                animTopOrBottom(releaseChild, -VEL_THRESHOLD * 2);
                break;
            case 2:
                animTopOrBottom(releaseChild, VEL_THRESHOLD * 2);
                break;
        }
    }

    private void animTopOrBottom(View releasedChild, float yvel) {

        //0——不变 1——向上 2——向下
        int direction = 0;

        int activeChildViewIndex = 0;

        mMiddleView = releasedChild;

        activeChildViewIndex = indexOfChild(mMiddleView);

        mTopView = getChildAtIndex(activeChildViewIndex - 1);
        mBottomView = getChildAtIndex(activeChildViewIndex + 1);


        // 默认是粘到最顶端
        int finalTop = 0;

        if (activeChildViewIndex == 0) {
            // 拖动第一个view松手
            if (yvel < -VEL_THRESHOLD || (mMiddleView.getTop() < -DISTANCE_THRESHOLD)) {
                // 向上的速度足够大，就滑动到顶端
                // 向上滑动的距离超过某个阈值，就滑动到顶端
                finalTop = -mMiddleView.getMeasuredHeight();

                direction = 1;

                // 下一页可以初始化了
                if (null != nextPageListener) {
                    nextPageListener.onShowNextPage(activeChildViewIndex + 1);
                }
            }
        } else if (activeChildViewIndex == getChildCount() - 1) {
            // 拖动第二个view松手
            if (yvel > VEL_THRESHOLD || (releasedChild.getTop() > DISTANCE_THRESHOLD)) {
                // 保持原地不动
                finalTop = mTopView.getMeasuredHeight();

                direction = 2;
            }
        } else {
            // 拖动第一个view松手
            if (yvel < -VEL_THRESHOLD || (mMiddleView.getTop() < -DISTANCE_THRESHOLD)) {
                // 向上的速度足够大，就滑动到顶端
                // 向上滑动的距离超过某个阈值，就滑动到顶端
                finalTop = -mMiddleView.getMeasuredHeight();

                direction = 1;

                // 下一页可以初始化了
                if (null != nextPageListener) {
                    nextPageListener.onShowNextPage(activeChildViewIndex + 1);
                }
            } else if (yvel > VEL_THRESHOLD || (releasedChild.getTop() > DISTANCE_THRESHOLD)) {
                // 保持原地不动
                finalTop = mTopView.getMeasuredHeight();

                direction = 2;
            }
        }

        if (mDragHelper.smoothSlideViewTo(releasedChild, 0, finalTop)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }

        //更新 mTopView、mMiddleView、mBottomView

        switch (direction) {
            case 1:

                mTopView = mMiddleView;
                mMiddleView = getChildAtIndex(indexOfChild(mTopView) + 1);
                mBottomView = getChildAtIndex(indexOfChild(mTopView) + 2);

                break;
            case 2:

                mBottomView = mMiddleView;
                mMiddleView = getChildAtIndex(indexOfChild(mBottomView) - 1);
                mTopView = getChildAtIndex(indexOfChild(mBottomView) - 2);

                break;
        }
    }

    public void setOnShowNextPageListener(OnShowNextPageListener nextPageListener) {
        this.nextPageListener = nextPageListener;
    }

    public interface OnShowNextPageListener {
        void onShowNextPage(int nextView);
    }

    private class DragCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            // 两个子View都需要跟踪，返回true
            if (indexOfChild(child) != -1) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

            onViewPosChanged(changedView, left, top, dx, dy);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            // 这个用来控制拖拽过程中松手后，自动滑行的速度
            return child.getHeight();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            // 滑动松开后，需要向上或者乡下粘到特定的位置
            animTopOrBottom(releasedChild, yvel);

        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            //若要防止第一个childView下拉出现空白，开启此代码块
            /*if (indexOfChild(child) == 0 && child.getTop() >= 0 && dy > 0){
                return 0;
            }*/

            //若要防止最后一个childView上滑出现空白，开启此代码块
            /*if (indexOfChild(child) == getChildCount() - 1 && child.getTop() <= 0 && dy < 0){
                return 0;
            }*/

            // 阻尼滑动，让滑动位移将为1/2
            return child.getTop() + dy / 2;
        }
    }

    private class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
            // 垂直滑动时dy>dx，才被认定是上下拖动
            return Math.abs(dy) > Math.abs(dx);
        }
    }
}
