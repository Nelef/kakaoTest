package com.uyjang.kakaotest.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uyjang.kakaotest.base.UiState
import com.uyjang.kakaotest.data.internal.FavoriteDataSource
import com.uyjang.kakaotest.data.remote.model.Document
import com.uyjang.kakaotest.data.remote.model.SearchImageResponseData
import com.uyjang.kakaotest.data.remote.repository.ApiResult
import com.uyjang.kakaotest.data.remote.repository.ApiServiceRepository
import com.uyjang.kakaotest.viewModel.adapter.SearchAdapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    init {
        viewModelScope.launch {
            repository = ApiServiceRepository()
            // DataStore로부터 즐겨찾기 목록을 가져옵니다.
            favoriteDataSource = FavoriteDataSource(getApplication())
            favoriteList = favoriteDataSource.getPreference().first().toMutableList()
        }
    }

    // UIState(현재 ui 상태 확인용)
    private val _uiState = MutableLiveData<UiState<*>>()
    val uiState: LiveData<UiState<*>> get() = _uiState

    // 리사이클러뷰
    private val items = mutableListOf<Document>() // 아이템
    private lateinit var favoriteDataSource: FavoriteDataSource // 즐겨찾기 기능
    private var favoriteList = mutableListOf<Document>()
    val onFavoriteButtonClick: (Document) -> Unit = { document ->
        addToFavorites(document)
    }
    val adapter = SearchAdapter(items, favoriteList, onFavoriteButtonClick) // 어댑터

    // 결과 리스트
    private var imageDataList = mutableListOf<Document>()

    // Api 연결
    private lateinit var repository: ApiServiceRepository

    // api 호출
    suspend fun getTickerDetails(query: String, sort: String = "recency", page: Int = 1) {
        repository.fetchSearchImage(query, sort, page).collectLatest { result ->
            imageDataList = when (result) {
                is ApiResult.Loading -> {
                    setUIState(UiState.Loading)
                    imageDataList
                }

                is ApiResult.Error -> {
                    setUIState(UiState.Error("api 요청에 실패하였습니다."))
                    mutableListOf()
                }

                is ApiResult.Success -> {
                    setUIState(UiState.None)
                    result.data.documents.toMutableList()
                }
            }
            updateItems(imageDataList, true)
            Log.i("uyjang", imageDataList.toString())
        }
    }

    fun setUIState(uiState: UiState<*>) {
        _uiState.postValue(uiState)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<Document> = listOf(), clear: Boolean = false) {
        if (clear)
            items.clear() // 현재 리스트를 비웁니다.
        items.addAll(newItems) // 새로운 아이템 리스트로 업데이트합니다.
        adapter.notifyDataSetChanged() // 데이터 변경을 어댑터에 알립니다.
    }

    fun addToFavorites(document: Document) {

    }
}