package com.ian.payback.image

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ian.payback.image.domain.model.ImageInfo
import com.ian.payback.image.domain.model.ResponsePOJO
import com.ian.payback.image.domain.usecase.GetImagesUseCase
import com.ian.payback.image.util.Constants
import com.ian.payback.image.util.Resource
import com.ian.payback.image.util.Status
import com.ian.payback.image.view.ui.getimagesinfo.GetImagesInfoViewModel
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.mockito.*
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

class GetImageViewModelUnitTest {
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: GetImagesInfoViewModel

    @Mock
    private lateinit var getImagesUseCase: GetImagesUseCase

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Resource<List<ImageInfo>>>


    @Mock
    private lateinit var observer: Observer<Resource<List<ImageInfo>>>

    companion object {
        const val ASSET_BASE_PATH = "../app/src/main/assets/"

        @BeforeClass
        fun setUpClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = GetImagesInfoViewModel(getImagesUseCase)
        viewModel.imageListResource.observeForever(observer)
    }

    @Test
    fun testNull() {
        Mockito.`when`(getImagesUseCase.getImages(Constants.DEFAULT_SEARCH_TERM)).thenReturn(null)
        Assert.assertNotNull(viewModel.imageListResource)
        Assert.assertTrue(viewModel.getListResultState().hasObservers())
    }

    @Test
    @Throws(IOException::class)
    fun `test state change to success is correct on valid data fetched`() {

        val gson: Gson = GsonBuilder().create()
        val listres: ResponsePOJO = gson.fromJson(
            readJsonFile("success_response.json"),
            ResponsePOJO::class.java
        )
        Mockito.`when`(getImagesUseCase.getImages(Constants.DEFAULT_SEARCH_TERM))
            .thenReturn(Single.just(listres.hits))
        viewModel.getImages(Constants.DEFAULT_SEARCH_TERM)
        Mockito.verify(observer, Mockito.times(2))
            .onChanged(argumentCaptor.capture())
        val values = argumentCaptor.allValues
        Assert.assertEquals(Status.LOADING, values[0].status)
        Assert.assertEquals(Status.SUCCESS, values[1].status)
    }



    @Test
    fun `test state change to error is correct on error gotten from use case`() {

        Mockito.`when`(getImagesUseCase.getImages(Constants.DEFAULT_SEARCH_TERM))
            .thenReturn(Single.error(Throwable("Api error")))
        viewModel.getImages(Constants.DEFAULT_SEARCH_TERM)
        Mockito.verify(observer, Mockito.times(2))
            .onChanged(argumentCaptor.capture())
        val values = argumentCaptor.allValues
        Assert.assertEquals(Status.LOADING, values[0].status)
        Assert.assertEquals(Status.ERROR, values[1].status)
    }

    @Throws(IOException::class)
    fun readJsonFile(filename: String): String {
        val br = BufferedReader(InputStreamReader(FileInputStream(ASSET_BASE_PATH + filename)))
        val sb = StringBuilder()
        var line = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }
        return sb.toString()
    }


}