package com.akashgarg.sample.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akashgarg.sample.R
import com.akashgarg.sample.databinding.RowImagesListBinding
import com.akashgarg.sample.model.ImageModel
import com.akashgarg.sample.service.DownloadService
import com.akashgarg.sample.utils.AppConstants
import com.akashgarg.sample.utils.AppUtils


/**
 * Created by Akash Garg on 14-05-2019.
 */

class FilesListAdapter(var mContext: Context) :
    PagedListAdapter<ImageModel, FilesListAdapter.MyViewHolder>(ImageModel.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding: RowImagesListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.row_images_list, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val PDF_URL = "https://www.oracle.com/technetwork/java/newtojava/java8book-2172125.pdf"
        holder.binding.model = getItem(position)
        holder.imgDownload.setOnClickListener {
            Toast.makeText(mContext, mContext.getString(R.string.downloading), Toast.LENGTH_SHORT).show()

            if (!AppUtils.isMyServiceRunning(DownloadService::class.java, mContext)) {
                val intent = Intent(mContext, DownloadService::class.java)
                intent.putExtra(AppConstants.URL, getItem(position)!!.user!!.profileImage!!.large)
                intent.putExtra(AppConstants.APP_NAME, "MindValley")
                // intent.putExtra(AppConstants.URL, PDF_URL)
                mContext.startService(intent)
            } else
                Toast.makeText(it.context, "Please wait. Files already downloading...", Toast.LENGTH_SHORT).show()
        }
    }

    inner class MyViewHolder(var binding: RowImagesListBinding) : RecyclerView.ViewHolder(binding.root) {
        var imgDownload = binding.imgDownload
    }
}