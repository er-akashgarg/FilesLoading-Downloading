package com.akashgarg.sample.restclient

import com.akashgarg.sample.model.ImageModel
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * Created by Akash Garg on 14-05-2019.
 */

class Repository(var apiCallInterface: ApiCallInterface) {

    fun executeNewsApi(index: Int): Observable<List<ImageModel>> {
        return apiCallInterface.fetchImages(
            index.toString()
        )
    }

    fun downloadFile(url: String): Observable<ResponseBody> {
        return apiCallInterface.downloadImageRX(url)
    }

}