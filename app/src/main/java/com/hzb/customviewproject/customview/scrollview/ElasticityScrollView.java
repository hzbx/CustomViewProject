package com.hzb.customviewproject.customview.scrollview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 弹性的ScrollView
 */
public class ElasticityScrollView extends ScrollView implements IView {
    //用于记录正常的布局位置
    private Rect originalRect = new Rect();
    private View mContentView;
    private float startY;
    private boolean canPullDown;
    private boolean canPullUp;
    private boolean isMoveView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.getChildCount() > 0) {
            mContentView = getChildAt(0);
        }
    }

    @Override
    public boolean canPullDown() {
        return getScrollY() == 0;
    }

    @Override
    public boolean canPullUp() {
        return mContentView.getHeight() <= getHeight() + getScrollY();
    }

    @Override
    public void autoScroll(STATUS status, int offest) {
        switch (status) {
            case pull_up:
                mContentView.layout(originalRect.left, originalRect.top + offest, originalRect.right, originalRect.bottom + offest);
                break;
            case pull_down:
                mContentView.layout(originalRect.left, originalRect.top + offest, originalRect.right, originalRect.bottom + offest);
                break;
            case auto_scroll:
                // 开启动画
                ObjectAnimator translationY = ObjectAnimator.ofFloat(mContentView, "TranslationY", mContentView.getTop(), originalRect.top);
                translationY.setDuration(300);
                translationY.start();
                //复位
                mContentView.layout(originalRect.left, originalRect.top,
                        originalRect.right, originalRect.bottom);

                break;
        }
    }

    public enum STATUS {
        pull_down, auto_scroll, pull_up
    }


    public ElasticityScrollView(Context context) {
        super(context);
    }

    public ElasticityScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ElasticityScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        originalRect.set(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mContentView == null) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                canPullDown = canPullDown();
                canPullUp = canPullUp();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getY();
                int deltaY = (int) ((nowY - startY) * 0.3);
                if (canPullDown && deltaY > 0) {
                    autoScroll(STATUS.pull_down, deltaY);
                    isMoveView = true;
                }
                if (canPullUp && deltaY < 0) {
                    autoScroll(STATUS.pull_up, deltaY);
                    isMoveView = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isMoveView) {
                    isMoveView = false;
                    autoScroll(STATUS.auto_scroll, 0);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
