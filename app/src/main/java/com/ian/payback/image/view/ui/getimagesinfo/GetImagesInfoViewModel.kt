package com.ian.payback.image.view.ui.getimagesinfo


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ian.payback.image.domain.model.ImageInfo
import com.ian.payback.image.domain.usecase.GetImagesUseCase
import com.ian.payback.image.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

@HiltViewModel
class GetImagesInfoViewModel @Inject constructor(private val getImagesUseCase: GetImagesUseCase) :
    ViewModel() {

    val imageListResource = MutableLiveData<Resource<List<ImageInfo>>>()

    private var disposable: CompositeDisposable = CompositeDisposable()


    fun getImages(searchString: String) {

        imageListResource.postValue(Resource.loading(null))
        disposable.add(
            getImagesUseCase.getImages(searchString)
                .subscribeOn(Schedulers.io())

                .subscribe(
                    { result:  List<ImageInfo>-> this.onSuccess(result) },
                    { error: Throwable? ->
                        onError(
                            error!!
                        )
                    }
                ),
        )





    }

    private fun onSuccess(result: List<ImageInfo>) {

        imageListResource.postValue(Resource.success(result))
    }

    private fun onError(error: Throwable) {
        imageListResource.postValue(
            Resource.error(
                "Something went wrong",
                null,
            ),
        )
    }


    fun getListResultState(): MutableLiveData<Resource<List<ImageInfo>>> {
        return imageListResource
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()

    }
}