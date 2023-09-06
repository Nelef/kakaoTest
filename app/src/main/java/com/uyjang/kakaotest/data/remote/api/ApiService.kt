package com.uyjang.kakaotest.data.remote.api

import com.uyjang.kakaotest.data.remote.model.SearchImageResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/search/image")
    suspend fun getSearchImage(
        @Query("query") query: String,
        @Query("query") sort: String,
        @Query("page") page: Int
    ): SearchImageResponseData
}