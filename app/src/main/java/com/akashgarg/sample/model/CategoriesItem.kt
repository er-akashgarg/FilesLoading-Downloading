package com.akashgarg.sample.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Akash Garg on 14-05-2019.
 */


data class CategoriesItem(

    @SerializedName("photo_count")
    val photoCount: Int? = null,

    @SerializedName("links")
    val links: Links? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("title")
    val title: String? = null
)