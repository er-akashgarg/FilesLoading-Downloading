package com.akashgarg.sample.utils

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akashgarg.sample.restclient.Repository
import com.akashgarg.sample.viewmodel.FilesLoadListViewModel

import javax.inject.Inject

/**
 * @author Akash Garg on 16-05-2019.
 */

open class ViewModelFactory

@Inject
constructor(private val repository: Repository) : ViewModelProvider.Factory {

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilesLoadListViewModel::class.java!!)) {
            return FilesLoadListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
