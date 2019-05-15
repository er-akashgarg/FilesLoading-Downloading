package com.akashgarg.sample.datasource

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.akashgarg.sample.model.ImageModel
import com.akashgarg.sample.restclient.Repository
import com.akashgarg.sample.utils.AppConstants
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Akash Garg on 14-05-2019.
 */

class FilesDataSourceClass(
    var repository: Repository,
    private var compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, ImageModel>() {
    private var sourceIndex: Int = 1
    private var progressLiveStatus: MutableLiveData<String> = MutableLiveData()


    fun getProgressLiveStatus(): MutableLiveData<String> {
        return progressLiveStatus
    }

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ImageModel>) {
        repository.let {
            it.executeNewsApi(sourceIndex)
                .doOnSubscribe { disposable ->
                    compositeDisposable.add(disposable)
                    progressLiveStatus.postValue(AppConstants.LOADING)
                }
                .subscribe(
                    { result: List<ImageModel> ->
                        progressLiveStatus.postValue(AppConstants.LOADED)
                        val arrayList = ArrayList<ImageModel>()
                        arrayList.addAll(result)
                        sourceIndex++
                        callback.onResult(arrayList, null, sourceIndex)
                    },
                    { throwable ->
                        progressLiveStatus.postValue(AppConstants.LOADED)
                    }
                )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageModel>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageModel>) {
        repository.let {
            it.executeNewsApi(params.key)
                .doOnSubscribe { disposable ->
                    //  compositeDisposable.add(disposable)
                    progressLiveStatus.postValue(AppConstants.LOADING)
                }
                .subscribe(
                    { result: List<ImageModel> ->
                        progressLiveStatus.postValue(AppConstants.LOADED)
                        val arrayList = ArrayList<ImageModel>()
                        arrayList.addAll(result)
                        callback.onResult(arrayList, if (params.key === 3) null else params.key + 1)
                    },
                    { throwable ->
                        Log.e("throwable", "#loadAfter# :  " + throwable.message)
                        progressLiveStatus.postValue(AppConstants.LOADED)
                    }
                )
        }
    }
}