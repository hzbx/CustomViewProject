package com.hzb.customviewproject.customview.piechart;

public interface SectorV {
    void setDatas(int... num);
    void setColor(int... colors);
    void setCount(int sum);
    void startRotate();
    void stopRotate();
}
