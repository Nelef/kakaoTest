package com.uyjang.kakaotest.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    init {

    }
}

data class SearchItem(val text: String)