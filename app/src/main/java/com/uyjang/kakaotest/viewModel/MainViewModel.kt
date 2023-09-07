package com.uyjang.kakaotest.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.uyjang.kakaotest.data.internal.FavoriteDataSource
import com.uyjang.kakaotest.data.remote.model.Document
import com.uyjang.kakaotest.data.remote.repository.ApiServiceRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

open class MainViewModel(application: Application) : AndroidViewModel(application) {
    init {
        viewModelScope.launch {
            // DataStore로부터 즐겨찾기 목록을 가져옵니다.
            favoriteDataSource = FavoriteDataSource(getApplication())
            favoriteList = favoriteDataSource.getPreference().first().toMutableList()
        }
    }

    private lateinit var favoriteDataSource: FavoriteDataSource // 즐겨찾기 기능
    var favoriteList = mutableListOf<Document>()
}