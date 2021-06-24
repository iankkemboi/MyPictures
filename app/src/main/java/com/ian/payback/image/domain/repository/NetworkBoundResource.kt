package com.ian.payback.image.domain.repository
import android.annotation.SuppressLint
import androidx.annotation.StringDef
import androidx.room.EmptyResultSetException
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

import java.util.concurrent.TimeUnit
abstract class NetworkBoundResource<ResultType> {
    val value: ResultType?
        get() = result.value
    private val result = BehaviorSubject.create<ResultType>()
    @State
    private var state = OFF
    private var firstCallFinished: Boolean = false
    private val deBouncer = Debouncer()
    private fun fetchFromNetwork(searchString: String): Single<ResultType> {
        return createCall(searchString)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                saveCallResult(it)
            }
            .map { processResult(it) }
            .doOnSuccess {
                firstCallFinished = true
                setValue(it)
            }
    }
    private fun setValue(newValue: ResultType) {
        if (result.value != newValue) {
            result.onNext(newValue)
        }
    }
    abstract fun processResult(it: ResultType): ResultType
    protected abstract fun saveCallResult(item: ResultType)
    open fun shouldFetch(data: ResultType?): Boolean {
        return data == null
    }
    protected abstract fun loadFromDb(): Single<ResultType>
    protected abstract fun createCall(searchString: String): Single<ResultType>
    protected open fun onFetchFailed(error: Throwable) {

    }
    protected open fun onFirstSet(newValue: ResultType) {
    }

    @SuppressLint("CheckResult")
    private fun getInitialData(searchString: String) {
        deBouncer.debounce(Void::class.java, Runnable {
            asSingle(searchString)
                .subscribe({
                }, {
                    onFetchFailed(it)
                })
        }, 300, TimeUnit.MILLISECONDS)
    }
    fun asSingle(searchString: String): Single<ResultType> = fetchFromNetwork(searchString)
        .subscribeOn(Schedulers.io())
        .map {
            processResult(it)
        }
        .doOnSubscribe {
            state = ON
        }
        .onErrorResumeNext { error ->
                return@onErrorResumeNext  loadFromDb()
        }
        .flatMap {
            setValue(newValue = it)
            return@flatMap if (shouldFetch(it) && !firstCallFinished) fetchFromNetwork(searchString)
            else Single.just(it)
        }.doOnSuccess {
            setValue(it)
            onFirstSet(it)
            firstCallFinished = true
        }
        .doOnError {
            onFetchFailed(it)
        }
    fun clear() {
        firstCallFinished = false
        state = OFF
    }
    companion object {
        @StringDef(ON, OFF)
        private annotation class State
        private const val OFF = "off"
        private const val ON = "on"
    }
}