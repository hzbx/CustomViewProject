package com.hzb.customviewproject.customview.scrollview;

public interface IView {
    boolean canPullDown();
    boolean canPullUp();
    void autoScroll(ElasticityScrollView.STATUS status, int offest);
}
