package com.akashgarg.sample.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Akash Garg on 14-05-2019.
 */

data class User(

    @SerializedName("profile_image")
    val profileImage: ProfileImage? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("links")
    val links: Links? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("username")
    val username: String? = null
)