package com.ian.payback.image.view.ui.getimagesinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ian.payback.image.domain.model.ImageInfo


class ImageItemViewModel(private val image: ImageInfo) : ViewModel() {

    val imageData = MutableLiveData<ImageInfo>()

    init {
        imageData.value = image
    }


}