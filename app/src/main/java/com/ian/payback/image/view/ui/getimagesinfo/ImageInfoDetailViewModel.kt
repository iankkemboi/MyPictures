package com.ian.payback.image.view.ui.getimagesinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ian.payback.image.domain.model.ImageInfo

class ImageInfoDetailViewModel : ViewModel() {

    private val fullImage = MutableLiveData<String>()
    private val username = MutableLiveData<String>()
    private val userUrl = MutableLiveData<String>()
    private val tag = MutableLiveData<String>()

    private val likes = MutableLiveData<String>()
    private val favorites = MutableLiveData<String>()
    private val comments = MutableLiveData<String>()

    fun bind(image: ImageInfo) {
        fullImage.value = image.largeImageURL
        username.value = "@" + image.user
        userUrl.value = image.userImageURL
        tag.value = image.tags

        likes.value = image.likes + " Likes"
        if (image.favorites.isNullOrEmpty()) {
            favorites.value = "0 Favorites"
        } else {
            favorites.value = image.favorites + " Favorites"
        }
        comments.value = image.comments + " Comments"
    }

    fun getFullImageURl(): MutableLiveData<String> {
        return fullImage
    }

    fun getUsername(): MutableLiveData<String> {
        return username
    }

    fun getTags(): MutableLiveData<String> {
        return tag
    }

    fun getUserUrl(): MutableLiveData<String> {
        return userUrl
    }


    fun getComments(): MutableLiveData<String> {
        return comments
    }

    fun getLikes(): MutableLiveData<String> {
        return likes
    }

    fun getFav(): MutableLiveData<String> {
        return favorites
    }
}