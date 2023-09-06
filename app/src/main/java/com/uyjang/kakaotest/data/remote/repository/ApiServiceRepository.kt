package com.uyjang.kakaotest.data.remote.repository

import android.util.Log
import com.uyjang.kakaotest.data.remote.RetrofitClient
import com.uyjang.kakaotest.data.remote.model.SearchImageResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class ApiResult<T> {
    class Loading<T> : ApiResult<T>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val exception: Throwable) : ApiResult<T>()
}

class ApiServiceRepository {
    fun fetchSearchImage(
        query: String, sort: String, page: Int
    ): Flow<ApiResult<SearchImageResponseData>> = flow {
        emit(ApiResult.Loading())  // 로딩 상태 전송
        try {
            val response = RetrofitClient.apiService.getSearchImage(query, sort, page)
            emit(ApiResult.Success(response))  // 성공 상태 및 데이터 전송
        } catch (e: Exception) {
            Log.e("fetchSearchImage", e.toString())
            emit(ApiResult.Error(e))  // 에러 상태 전송
        }
    }
}