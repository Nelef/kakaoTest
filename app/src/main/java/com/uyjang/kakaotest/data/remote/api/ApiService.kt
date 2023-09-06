package com.uyjang.kakaotest.data.remote.api

import com.uyjang.kakaotest.data.remote.model.TickerDetail
import retrofit2.http.GET

interface ApiService {
    @GET("v1/ticker/detailed/all")
    suspend fun getTickerDetails(): Map<String, TickerDetail>
}