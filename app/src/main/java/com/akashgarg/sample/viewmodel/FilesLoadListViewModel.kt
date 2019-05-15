package com.akashgarg.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.akashgarg.sample.datasource.FilesDataSourceClass
import com.akashgarg.sample.datasource.FilesDataSourceFactory
import com.akashgarg.sample.model.ImageModel
import com.akashgarg.sample.restclient.Repository
import io.reactivex.disposables.CompositeDisposable

class FilesLoadListViewModel(var repository: Repository) : ViewModel() {
    private var newsDataSourceFactory: FilesDataSourceFactory
    private lateinit var listLiveData: LiveData<PagedList<ImageModel>>
    private var progressLoadStatus: LiveData<String> = MutableLiveData()
    private var compositeDisposable = CompositeDisposable()

    init {
        newsDataSourceFactory = FilesDataSourceFactory(repository, compositeDisposable)
        initializePaging()
    }

    private fun initializePaging() {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(10).build()

        listLiveData = LivePagedListBuilder(newsDataSourceFactory, pagedListConfig).build()

        progressLoadStatus = Transformations.switchMap(
            newsDataSourceFactory.getMutableLiveData(),
            FilesDataSourceClass::getProgressLiveStatus
        )
    }

    fun getListLiveData(): LiveData<PagedList<ImageModel>> {
        return listLiveData
    }

    fun getProgressLoadStatus(): LiveData<String> {
        return progressLoadStatus
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}