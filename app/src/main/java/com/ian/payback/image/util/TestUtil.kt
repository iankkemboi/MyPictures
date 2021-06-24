package com.ian.payback.image.util

import com.ian.payback.image.domain.model.ImageInfo


object TestUtil {



    fun makeImageInfoList(size: Int): MutableList<ImageInfo> {
        val list = ArrayList<ImageInfo>(size)
        list.forEach {
            list.add(it)
        }
        return list
    }


}