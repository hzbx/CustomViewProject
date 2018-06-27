package com.hzb.customviewproject.customview.drawutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class CornerDrawable extends Drawable {
    private Context context;
    private Paint mPaint;
    private Bitmap bitmap;
    private RectF rect;
    private int rValue;

    public CornerDrawable(Context context, Bitmap bitmap) {
        this.context = context;
        this.bitmap = bitmap;
        rValue=dp2px(20);
        init();
        initBitmap();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void initBitmap() {
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(bitmapShader);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rect=new RectF(left,top,right,bottom);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRoundRect(rect,rValue,rValue,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    public void setRoundR(int rValue){
        this.rValue = rValue;
    }
    private int dp2px(float value){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(density*value+0.5f);
    }
}
