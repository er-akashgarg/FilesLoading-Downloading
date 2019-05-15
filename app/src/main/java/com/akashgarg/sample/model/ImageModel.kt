package com.akashgarg.sample.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

/**
 * Created by Akash Garg on 14-05-2019.
 */

data class ImageModel(

    @SerializedName("urls")
    val urls: Urls? = null,

    @SerializedName("current_user_collections")
    val currentUserCollections: List<Any?>? = null,

    @SerializedName("color")
    val color: String? = null,

    @SerializedName("width")
    val width: Int? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("links")
    val links: Links? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("categories")
    val categories: List<CategoriesItem?>? = null,

    @SerializedName("liked_by_user")
    val likedByUser: Boolean? = null,

    @SerializedName("user")
    val user: User? = null,

    @SerializedName("height")
    val height: Int? = null,

    @SerializedName("likes")
    val likes: Int? = null
) {
    companion object {
        var DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageModel>() {
            override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun equals(obj: Any?): Boolean {
        if (obj === this)
            return true

        val article = obj as ImageModel?
        return article!!.id == this.id
    }

}