package com.android.sample.common.base

import com.android.sample.common.util.NoDataException
import io.reactivex.Observable

abstract class BaseRepository<T> {

    private var cacheIsDirty = false

    protected abstract fun getResultFromRemoteDataSource(url: String?): Observable<T>

    protected abstract fun getResultFromLocalDataSource(id: String?): T?

    private fun getRemoteResult(url: String?) = getResultFromRemoteDataSource(url)
        .doOnComplete { cacheIsDirty = false }

    fun getResult(url: String?, id: String?): Observable<T> =
        Observable.fromCallable { cacheIsDirty }.flatMap {
            if (it) {
                getRemoteResult(url)
            } else {
                val resultFromLocalDataSource = getResultFromLocalDataSource(id)
                Observable.create { subscriber ->
                    if (resultFromLocalDataSource == null) {
                        subscriber.onError(NoDataException())
                    } else {
                        subscriber.onNext(resultFromLocalDataSource)
                    }
                }
            }.onErrorResumeNext(getRemoteResult(url))
        }

    fun refresh() {
        cacheIsDirty = true
    }
}