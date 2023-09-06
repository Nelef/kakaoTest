package com.uyjang.kakaotest.data.remote

import com.uyjang.kakaotest.data.remote.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://dapi.kakao.com/"
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val KAKAO_API_KEY = "KakaoAK 131b1ce854494fa55a8053f3c4b98fa8"

    val apiService: ApiService by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder: Request.Builder = originalRequest.newBuilder()
                    .header(AUTHORIZATION_HEADER, KAKAO_API_KEY)
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
