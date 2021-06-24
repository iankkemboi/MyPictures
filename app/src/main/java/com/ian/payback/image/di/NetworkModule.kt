package com.ian.payback.image.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ian.payback.image.data.repository.ImageRepositoryImpl
import com.ian.payback.image.data.source.local.AppDatabase
import com.ian.payback.image.data.source.remote.RetrofitService
import com.ian.payback.image.domain.repository.ImageRepository
import com.ian.payback.image.util.Constants.BASE_URL
import com.ian.payback.image.util.CustomJsonConverterFactory
import com.ian.payback.image.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)

            .addConverterFactory(CustomJsonConverterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(client)


            .build()
            .create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            addInterceptor(loggingInterceptor)
            build()
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()


    @Singleton
    @Provides
    fun provideGetImageRepository(
        appDatabase: AppDatabase,
        retrofitService: RetrofitService
    ): ImageRepository {
        return ImageRepositoryImpl(appDatabase, retrofitService)
    }
}