package com.android.sample.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.sample.common.util.ViewState
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * BaseViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 */
open class BaseViewModel<T>(
    private val repository: BaseRepository<T>,
    private val schedulerProvider: BaseSchedulerProvider,
    private val linkUrl: String? = null,
    private val linkId: String? = null
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _liveData = MutableLiveData<ViewState<T>>()
    val liveData: LiveData<ViewState<T>>
        get() = _liveData

    init {
        loadItems(false)
    }

    fun loadItems(isRefreshing: Boolean) {
        if (isRefreshing) {
            repository.refresh()
        }
        _liveData.value = ViewState.Loading
        repository.getResult(linkUrl, linkId).subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                _liveData.postValue(ViewState.Success(it))
            }) {
                _liveData.postValue(ViewState.Error(it.localizedMessage))
                Timber.e(it)
            }.also { compositeDisposable.add(it) }
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all disposables;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}