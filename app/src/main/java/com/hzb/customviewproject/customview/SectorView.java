package com.hzb.customviewproject.customview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * 饼型View
 */
public class SectorView extends View implements SectorV {

    private int[] colors = new int[]{Color.RED, Color.GRAY, Color.BLUE, Color.GREEN, Color.CYAN};
    private int[] datas = new int[]{10, 40, 70, 5, 2};
    private Paint paint;
    private RectF rectF;
    private float startAngle = 0f;
    private int sum = 127;
    private ObjectAnimator rotate;
    private GestureDetector gestureDetector;
    private Scroller scroller;
    private int InitAngle;
    private float oriRotation;
    private boolean isMove;

    public SectorView(Context context) {
        super(context);
        init();
    }

    public SectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        rectF = new RectF();
        scroller = new Scroller(getContext());
        gestureDetector = new GestureDetector(getContext(), new MyGesture());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        rectF.set(0, 0, this.getWidth(), this.getHeight());
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void setDatas(int... datas) {
        this.datas = datas;
    }

    @Override
    public void setColor(int... colors) {
        this.colors = colors;
    }

    @Override
    public void setCount(int sum) {
        this.sum = sum;
    }

    @Override
    public void startRotate() {
        rotate = ObjectAnimator.ofFloat(this, "rotation", 0f, 720f);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatMode(ValueAnimator.RESTART);
        rotate.setRepeatCount(ValueAnimator.INFINITE);
        rotate.setDuration(1000);
        rotate.start();
    }

    @Override
    public void stopRotate() {
        rotate.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //遍历画饼图
        for (int index = 0; index < datas.length; index++) {
            paint.setColor(colors[index]);
            float sweepAngle = (360 / (float) sum) * datas[index];
            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
            startAngle += sweepAngle;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);

    }


    class MyGesture extends GestureDetector.SimpleOnGestureListener {

        private Point point;
        private int lastX;
        private int lastY;

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float old = getAngle(e1);
            float newrote = getAngle(e2);
            oriRotation += newrote - old;
            // Log.i("ii", "起点: "+old+" 终点 "+newrote+"   "+oriRotation);
            SectorView.this.setRotation(oriRotation);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            isMove=false;
            return true;
        }
    }

    /**
     * 获取旋转角度
     *
     * @param event
     * @return
     */
    private float getAngle(MotionEvent event) {
        double delta_x = (event.getX() - rectF.centerX());
        double delta_y = (event.getY() - rectF.centerY());
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }


}
