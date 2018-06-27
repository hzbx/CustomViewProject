package com.hzb.customviewproject.customview.drawutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class HiveDrawable extends Drawable {

    private final Context context;
    Rect rect=new Rect();
    Paint mPaint;
    Path mPath;
    BitmapShader bitmapShader;
    private Bitmap mBitmap;

    public HiveDrawable(Context context, Bitmap bitmap) {
        this.context = context;
        init();
        setBitmap(bitmap);
    }

    private void init() {
        mPaint=new Paint();
        mPath=new Path();
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
        canvas.drawPath(mPath,mPaint);
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
        initPath();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (mPaint!=null){
            mPaint.setColorFilter(colorFilter);
        }
    }
    // 初始化好Path要走的路径
    private void initPath() {
        float l = (float) (rect.width() / 2);
        float h = (float) (Math.sqrt(3)*l);
        float top = (rect.height() - h) / 2  ;
        mPath.reset();
        mPath.moveTo(l/2,top);
        mPath.lineTo(0,h/2+top);
        mPath.lineTo(l/2,h+top);
        mPath.lineTo((float) (l*1.5),h+top);
        mPath.lineTo(2*l,h/2+top);
        mPath.lineTo((float) (l*1.5),top);
        mPath.lineTo(l/2,top);
        mPath.close();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

}
