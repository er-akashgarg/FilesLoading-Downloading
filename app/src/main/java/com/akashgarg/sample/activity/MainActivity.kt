package com.akashgarg.sample.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akashgarg.sample.R
import com.akashgarg.sample.adapter.FilesListAdapter
import com.akashgarg.sample.app.MyApplication
import com.akashgarg.sample.databinding.PagingListLayoutBinding
import com.akashgarg.sample.utils.AppConstants
import com.akashgarg.sample.utils.ViewModelFactory
import com.akashgarg.sample.viewmodel.FilesLoadListViewModel
import javax.inject.Inject


/**
 * Created by Akash Garg on 14-05-2019.
 */

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    private lateinit var pagingLayoutBinding: PagingListLayoutBinding
    private lateinit var viewModel: FilesLoadListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagingLayoutBinding = DataBindingUtil.setContentView(this, R.layout.paging_list_layout)
        (application as MyApplication).getAppComponent().doInjectInApp(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FilesLoadListViewModel::class.java)
        init()
    }

    private fun init() {
        val adapter = FilesListAdapter(this@MainActivity)
        pagingLayoutBinding.rvList.adapter = adapter
        if (!AppConstants.checkInternetConnection(this)) {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }

        viewModel.getListLiveData().observe(this, Observer { response ->
            adapter.submitList(response)
        })

        viewModel.getProgressLoadStatus().observe(this, Observer { status ->
            if (status == AppConstants.LOADING) {
                pagingLayoutBinding.progressBar.visibility = View.VISIBLE
            } else
                pagingLayoutBinding.progressBar.visibility = View.GONE
        })
    }
}
