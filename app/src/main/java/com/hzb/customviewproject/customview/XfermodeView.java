package com.hzb.customviewproject.customview;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hzb.customviewproject.R;

/**
 * Created by huangzhibo on 2018/5/7/007.
 * mail:1043202454@qq.com
 */
public class XfermodeView extends View {

    private Paint mPaint=new Paint();

    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void drawDst(Canvas canvas, Paint p) {
        //画黄色圆形
        p.setColor(0xFFFFCC44);
        canvas.drawOval(new RectF(0, 0, getWidth(), getWidth()), mPaint);
    }

    private void drawSrc(Canvas canvas, Paint p) {
        //画蓝色矩形
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.frist),0,0,mPaint);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //必须设置，否则设置的PorterDuffXfermode会无效，具体原因不明
        int saveLayer = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        //先绘制的是dst，后绘制的是src
        drawDst(canvas, mPaint);
        //设置xfermode
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        drawSrc(canvas, mPaint);
        //还原
        mPaint.setXfermode(null);
        //将如上画的层覆盖到原有层级上
        canvas.restoreToCount(saveLayer);
    }
}
