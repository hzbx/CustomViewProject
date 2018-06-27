package com.hzb.customviewproject.ui

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hzb.customviewproject.R
import com.hzb.customviewproject.customview.drawutils.CircleDrawable
import com.hzb.customviewproject.customview.drawutils.CornerDrawable
import com.hzb.customviewproject.customview.drawutils.HiveDrawable
import kotlinx.android.synthetic.main.activity_much_shade.*

class MuchShadeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_much_shade)
        iv.setImageDrawable(HiveDrawable(this, BitmapFactory.decodeResource(resources,R.mipmap.bg)))
        iv_hive.setImageDrawable(CircleDrawable(this, BitmapFactory.decodeResource(resources,R.mipmap.bg)))
        iv_corner.setImageDrawable(CornerDrawable(this, BitmapFactory.decodeResource(resources,R.mipmap.bg)))


        iv_1.setImageDrawable(CircleDrawable(this, BitmapFactory.decodeResource(resources,R.mipmap.bg)))
        iv_2.setImageDrawable(CircleDrawable(this, BitmapFactory.decodeResource(resources,R.mipmap.bg)))
        iv_3.setImageDrawable(CircleDrawable(this, BitmapFactory.decodeResource(resources,R.mipmap.bg)))
        iv_4.setImageDrawable(CircleDrawable(this, BitmapFactory.decodeResource(resources,R.mipmap.bg)))
        iv_5.setImageDrawable(CircleDrawable(this, BitmapFactory.decodeResource(resources,R.mipmap.bg)))
        iv_6.setImageDrawable(CircleDrawable(this, BitmapFactory.decodeResource(resources,R.mipmap.bg)))
        iv_7.setImageDrawable(CircleDrawable(this, BitmapFactory.decodeResource(resources,R.mipmap.bg)))
    }
}
