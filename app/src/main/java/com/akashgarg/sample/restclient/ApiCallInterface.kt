package com.akashgarg.sample.restclient

import com.akashgarg.sample.model.ImageModel
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * Created by Akash Garg on 14-05-2019.
 */

interface ApiCallInterface {

    @GET(Urls.FetchImages)
    fun fetchImages(@Query("pages") source: String): Observable<List<ImageModel>>


    @GET
    fun downloadImageRX(@Url fullUrl: String): Observable<ResponseBody>


    companion object Factory {
        fun create(): ApiCallInterface {
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Urls.BASE_URL)
                .build()

            return retrofit.create(ApiCallInterface::class.java)
        }
    }

}