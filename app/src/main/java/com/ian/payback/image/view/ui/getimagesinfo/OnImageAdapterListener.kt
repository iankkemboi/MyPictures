package com.ian.payback.image.view.ui.getimagesinfo

import com.ian.payback.image.domain.model.ImageInfo

interface OnImageAdapterListener {

    fun gotoDetailPage(image: ImageInfo?)

}