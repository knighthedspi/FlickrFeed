package com.tigerspike.flickrfeed.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tigerspike.flickrfeed.domain.model.ImageItem
import com.tigerspike.flickrfeed.domain.service.FlickrService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val flickrService: FlickrService
) : ViewModel() {

    private val isLoadingEventMutable = MutableLiveData<Boolean>()
    private val titleMutable = MutableLiveData<String>()
    private val updatedTimeMutable = MutableLiveData<LocalDateTime>()
    private val itemsMutable = MutableLiveData<List<ImageItem>>()
    private val disposable = CompositeDisposable()

    val isLoadingEvent: LiveData<Boolean>
        get() = isLoadingEventMutable
    val title: LiveData<String>
        get() = titleMutable
    val updatedTime: LiveData<LocalDateTime>
        get() = updatedTimeMutable
    val items: LiveData<List<ImageItem>>
        get() = itemsMutable

    fun loadData() {
        isLoadingEventMutable.postValue(true)
        flickrService
            .getImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLoadingEventMutable.postValue(false)
                titleMutable.postValue(it.title)
                updatedTimeMutable.postValue(it.modified)
                itemsMutable.postValue(it.items)
            }, {
                Timber.e(it)
                isLoadingEventMutable.postValue(false)
            })
            .addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}