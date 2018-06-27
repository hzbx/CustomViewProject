package com.hzb.customviewproject.customview.drawutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 圆形的
 */
public class CircleDrawable extends Drawable {

    private final Context context;
    Rect rect=new Rect();
    Paint mPaint;
    BitmapShader bitmapShader;
    private Bitmap mBitmap;

    public CircleDrawable(Context context,Bitmap bitmap) {
        this.context = context;
        init();
        setBitmap(bitmap);
    }


    private void init() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3f);
    }

    private void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        bitmapShader= new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(bitmapShader);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(rect.centerX(),rect.centerY(),rect.height()/2-dp2px(1),mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        if (mPaint!=null){
            mPaint.setAlpha(alpha);
        }
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rect.set(left,top,right,bottom);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (mPaint!=null){
            mPaint.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    private int dp2px(float dpValue){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(density*dpValue+0.5f);
    }
}
