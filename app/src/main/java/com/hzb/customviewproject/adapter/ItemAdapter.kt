package com.hzb.customviewproject.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hzb.customviewproject.R

/**
 * Created by huangzhibo on 2018/4/30/030.
 * mail:1043202454@qq.com
 */
class ItemAdapter(layoutResId: Int, data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.apply {
            this.setText(R.id.item_title,item)
        }
    }
}