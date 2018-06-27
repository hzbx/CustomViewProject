package com.hzb.customviewproject.view.downrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzb.customviewproject.R;

/**
 * Created by huangzhibo on 2018/4/30/030.
 * mail:1043202454@qq.com
 */
public class HeadLayout extends RelativeLayout {

    public static final int PULL_INIT=0;
    public static final int PULL_UP=1;
    public static final int PULL_REFRESH=2;

    private View mView;
    private TextView mTitle;

    public HeadLayout(Context context) {
        super(context);
        init();
    }

    public HeadLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mView = View.inflate(getContext(), R.layout.head_refresh_layout, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = this.findViewById(R.id.head_tv_titile);
    }


    public void setTitile(int status){
       switch(status){
           case HeadLayout.PULL_INIT:
               mTitle.setText("下拉刷新");
               break;
           case HeadLayout.PULL_UP:
               mTitle.setText("放开刷新");
               break;
           case HeadLayout.PULL_REFRESH:
               mTitle.setText("刷新中");
               break;
       }
    }


}
