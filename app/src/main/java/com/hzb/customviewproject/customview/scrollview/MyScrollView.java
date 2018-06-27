package com.hzb.customviewproject.customview.scrollview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 模仿ScrollView
 */
public class MyScrollView extends LinearLayout {

    private GestureDetector gestureDetector;
    private int mTopViewHeight;
    private Scroller scroller;

    public MyScrollView(Context context) {
        super(context);
        init(context);
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        gestureDetector = new GestureDetector(getContext(), new Listener());
        scroller = new Scroller(context);
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(0,(scroller.getCurrY()));
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = this.getChildAt(0).getMeasuredHeight()*this.getChildCount()-this.getMeasuredHeight();
    }

    public class Listener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            if (!scroller.isFinished()){
                scroller.abortAnimation();
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            scrollBy(0, (int) distanceY*3/2);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            scroller.fling(getScrollX(),getScrollY(),(int) velocityX,(int)- velocityY*3/2,0,0,-getHeight()/2,mTopViewHeight*2);
            invalidate();
            return true;
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) { //顶部阀值　0
            y = 0;
        }
        if (y > mTopViewHeight) { //底部阀值 最大可滑动位置高度
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }


}
