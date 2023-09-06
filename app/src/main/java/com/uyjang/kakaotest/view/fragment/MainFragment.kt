package com.uyjang.kakaotest.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.uyjang.kakaotest.base.BaseFragment
import com.uyjang.kakaotest.databinding.FragmentMainBinding
import com.uyjang.kakaotest.viewModel.MainViewModel

class MainFragment : BaseFragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // MainFragment에 특정한 초기화 코드를 작성할 수 있습니다.
    }
}