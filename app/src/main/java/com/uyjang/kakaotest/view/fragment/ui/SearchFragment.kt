package com.uyjang.kakaotest.view.fragment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.uyjang.kakaotest.databinding.FragmentSearchBinding
import com.uyjang.kakaotest.viewModel.SearchItem
import com.uyjang.kakaotest.viewModel.SearchViewModel
import com.uyjang.kakaotest.viewModel.adapter.SearchAdapter

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gridRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 4) // 그리드 레이아웃 설정
        val items = mutableListOf(
            SearchItem("아이템 1"),
            SearchItem("아이템 2"),
            SearchItem("아이템 3"),
            // 여기에 더 많은 아이템을 추가할 수 있습니다.
        )
        val adapter = SearchAdapter(items)
        binding.gridRecyclerView.adapter = adapter
    }
}