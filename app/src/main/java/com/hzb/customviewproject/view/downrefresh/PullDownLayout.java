package com.hzb.customviewproject.view.downrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.hzb.customviewproject.utils.DensityUtils;

/**
 * Created by huangzhibo on 2018/4/30/030.
 * mail:1043202454@qq.com
 */
public class PullDownLayout extends ViewGroup {
    private boolean isMeasure = true;
    private HeadLayout mHeadLayout;
    private int mMeasureWidth;
    private int mMMeasureHeight;
    private float mStartY;
    private float mStartY1;
    private float mOffestY;
    private ScrollView mScrollView;

    public PullDownLayout(Context context) {
        super(context);
        init();
    }

    public PullDownLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullDownLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHeadLayout = new HeadLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtils.dp2px(getContext(), 150));
        mHeadLayout.setLayoutParams(layoutParams);
        addView(mHeadLayout);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(isMeasure) {
            mMeasureWidth = getMeasureSize(widthMeasureSpec);
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            for(int i = 0; i < getChildCount(); i++) {
                mMMeasureHeight += getChildAt(i).getMeasuredHeight();
            }
            isMeasure = false;
        }
        setMeasuredDimension(mMeasureWidth, mMMeasureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        view.layout(0, -view.getMeasuredHeight(), view.getMeasuredWidth(), 0);
        View childAt = getChildAt(1);
        childAt.layout(0, 0, childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
        mScrollView = (ScrollView) this.getChildAt(1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mStartY1 = ev.getY();
                Log.i("ii", "onInterceptTouchEvent: Down "+mStartY1);
                break;
            case MotionEvent.ACTION_MOVE:
                float v = ev.getY() - mStartY1;
                if(v>0){//下拉
                    if(mScrollView.getScrollY() == 0) {
                        return true;
                    }else{
                        return false;
                    }
                }else if(v<0){//上啦
                    if(this.getScrollY()<=0){
                        return false;
                    }else{
                        return true;
                    }
                }
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("aa", "onTouchEvent: ");
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = event.getY();
                Log.i("ii", "onTouchEvent: Down "+mStartY);
                break;
            case MotionEvent.ACTION_MOVE:
                float endY = event.getY();
                mOffestY = endY - mStartY1;
                Log.i("ii", "onTouchEvent: MOVE  终点"+endY+" 起点"+mStartY+"   "+mOffestY);
                this.scrollBy(0, -(int) mOffestY/2);
                mStartY1 = endY;
                break;
        }
        Log.i("发的", "onTouchEvent: " + this.getScrollY());
        return true;
    }


    private int getMeasureSize(int Spec) {
        int result = 0;
        int mode = MeasureSpec.getMode(Spec);
        int size = MeasureSpec.getSize(Spec);
        if(mode == MeasureSpec.AT_MOST) {
            result = DensityUtils.dp2px(getContext(), 150);
        }else {
            result = size;
        }
        return result;
    }
}
