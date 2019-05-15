package com.akashgarg.sample.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Akash Garg on 14-05-2019.
 */

data class Urls(

    @SerializedName("small")
    val small: String? = null,

    @SerializedName("thumb")
    val thumb: String? = null,

    @SerializedName("raw")
    val raw: String? = null,

    @SerializedName("regular")
    val regular: String? = null,

    @SerializedName("full")
    val full: String? = null
)