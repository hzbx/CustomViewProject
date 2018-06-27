package com.hzb.customviewproject.ui

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.hzb.customviewproject.R
import com.hzb.customviewproject.adapter.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val datas_tiitle:MutableList<String> by lazy {
        resources.getStringArray(R.array.item_tiitle).toMutableList<String>()
    }

    private val datas_activity:MutableList<String> by lazy {
        resources.getStringArray(R.array.item_activity).toMutableList()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Toast.makeText(this,"${packageManager.canRequestPackageInstalls()}",Toast.LENGTH_LONG).show()
        }
    }


    private fun init() {
        recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager=LinearLayoutManager(this)
        val itemAdapter = ItemAdapter(R.layout.item_activity, datas_tiitle)
        recyclerView.adapter= itemAdapter
        itemAdapter.setOnItemClickListener { _, _, position ->
            startActivity(Intent(this@MainActivity, Class.forName("com.hzb.customviewproject.ui.${datas_activity[position]}")))
        }

    }
}
