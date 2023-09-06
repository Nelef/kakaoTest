package com.uyjang.kakaotest.data.remote.model

import com.google.gson.annotations.SerializedName

data class Document(
    val collection: String,
    val datetime: String,
    @SerializedName("display_sitename")
    val displaySitename: String,
    @SerializedName("doc_url")
    val docUrl: String,
    val height: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    val width: Int
)

data class Meta(
    @SerializedName("is_end")
    val isEnd: Boolean = false,
    @SerializedName("pageable_count")
    val pageableCount: Int = 0,
    @SerializedName("total_count")
    val totalCount: Int = 0
)

data class SearchImageResponseData(
    val documents: List<Document> = emptyList(),
    val meta: Meta = Meta()
)