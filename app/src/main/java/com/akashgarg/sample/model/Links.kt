package com.akashgarg.sample.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Akash Garg on 14-05-2019.
 */


data class Links(

    @SerializedName("self")
    val self: String? = null,

    @SerializedName("html")
    val html: String? = null,

    @SerializedName("photos")
    val photos: String? = null,

    @SerializedName("likes")
    val likes: String? = null
)