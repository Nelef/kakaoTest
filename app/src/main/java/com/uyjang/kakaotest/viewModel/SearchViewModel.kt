package com.uyjang.kakaotest.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uyjang.kakaotest.base.UiState
import com.uyjang.kakaotest.data.remote.model.SearchImageResponseData
import com.uyjang.kakaotest.data.remote.repository.ApiResult
import com.uyjang.kakaotest.data.remote.repository.ApiServiceRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    init {
        viewModelScope.launch {
            repository = ApiServiceRepository()
        }
    }

    // UIState(현재 ui 상태 확인용)
    private val _uiState = MutableLiveData<UiState<*>>()
    val uiState: LiveData<UiState<*>> get() = _uiState

    // Api 연결
    private lateinit var repository: ApiServiceRepository

    // api 호출
    suspend fun getTickerDetails(query: String, sort: String = "recency", page: Int = 1) {
        var imageData = SearchImageResponseData()

        repository.fetchSearchImage(query, sort, page).collectLatest { result ->
            imageData = when (result) {
                is ApiResult.Loading -> {
                    setUIState(UiState.Loading)
                    imageData
                }

                is ApiResult.Error -> {
                    setUIState(UiState.Error("api 요청에 실패하였습니다."))
                    SearchImageResponseData()
                }

                is ApiResult.Success -> {
                    setUIState(UiState.None)
                    result.data
                }
            }
            Log.i("uyjang", imageData.toString())
        }
    }

    fun setUIState(uiState: UiState<*>){
        _uiState.postValue(uiState)
    }
}

data class SearchItem(val text: String)