package com.hzb.customviewproject.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hzb.customviewproject.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    var isRotte:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

      button.setOnClickListener{v: View? ->

            sectorView.rotation= 30F
        } /*
        button2.setOnClickListener{v: View? ->

            imageView.invalidate()
        }*/
    }


}
