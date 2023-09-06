package com.uyjang.kakaotest.base

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.uyjang.kakaotest.view.ui.theme.kakaoTestTheme
import com.uyjang.kakaotest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 뷰모델
    val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                kakaoTestTheme {
                    // TODO : uiState 팝업 넣기.
                }
            }
        }

        setContentView(binding.root)
    }
}