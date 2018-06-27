package com.hzb.customviewproject.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import com.hzb.customviewproject.R

class XfermodeActivity : AppCompatActivity(){
    val gestureDetector by lazy {
        GestureDetector(this, Listener())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xfermode)
    }

    inner class Listener: GestureDetector.SimpleOnGestureListener(){
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            Log.i("onfling","抛${velocityY}")
            return true

        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            Log.i("onScroll","滚动${distanceY}")
            return true
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        this.gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}
