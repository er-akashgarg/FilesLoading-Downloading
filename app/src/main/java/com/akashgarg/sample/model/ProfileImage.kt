package com.akashgarg.sample.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Akash Garg on 14-05-2019.
 */

data class ProfileImage(

    @SerializedName("small")
    val small: String? = null,

    @SerializedName("large")
    val large: String? = null,

    @SerializedName("medium")
    val medium: String? = null

)