package com.hzb.customviewproject.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Scroller
import android.animation.ObjectAnimator


class CircleLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    var r = 0
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        this.r = getChildAt(0).measuredHeight
        if (changed) {
            circleLayout()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }


    private fun circleLayout() {
        val childCount = childCount
        getChildAt(0).layout((width - r) / 2, (height - r) / 2, (width - r) / 2 + r, (height - r) / 2 + r)
        for (i in 1 until childCount) {
            val childAt = getChildAt(i)
            val positions = getPositions(i)
            childAt.layout(positions[0], positions[1], r + positions[0], r + positions[1])
        }
    }


    private fun getPositions(index: Int): IntArray {
        var position: IntArray = IntArray(2)
        val angele = index * (360 / (childCount - 1)) * Math.PI / 180
        val centerX = Math.cos(angele) * r + width / 2 - r / 2
        val centerY = Math.sin(angele) * r + height / 2 - r / 2
        position.set(0, centerX.toInt())
        position.set(1, centerY.toInt())
        return position
    }


}