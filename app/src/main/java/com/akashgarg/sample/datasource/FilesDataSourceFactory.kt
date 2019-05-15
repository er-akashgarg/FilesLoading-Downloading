package com.akashgarg.sample.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.akashgarg.sample.model.ImageModel
import com.akashgarg.sample.restclient.Repository
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Akash Garg on 15-05-2019.
 */

class FilesDataSourceFactory(
    var repository: Repository,
    private var compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, ImageModel>() {

    private val liveData: MutableLiveData<FilesDataSourceClass> = MutableLiveData()


    override fun create(): DataSource<Int, ImageModel> {
        val dataSourceClass = FilesDataSourceClass(repository, compositeDisposable)
        liveData.postValue(dataSourceClass)
        return dataSourceClass
    }

    fun getMutableLiveData(): MutableLiveData<FilesDataSourceClass> {
        return liveData
    }
}