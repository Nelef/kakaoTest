package com.uyjang.kakaotest.view.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.uyjang.kakaotest.R
import com.uyjang.kakaotest.base.UiState
import com.uyjang.kakaotest.databinding.FragmentSearchBinding
import com.uyjang.kakaotest.viewModel.SearchItem
import com.uyjang.kakaotest.viewModel.SearchViewModel
import com.uyjang.kakaotest.viewModel.adapter.SearchAdapter
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var loadingView: View // 로딩 UI를 나타내는 뷰

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // loading_layout.xml을 인플레이트하고 View 변수에 할당
        loadingView = LayoutInflater.from(requireContext())
            .inflate(R.layout.loading_layout, binding.root, false)

        // uiState를 관찰하여 UI 업데이트
        viewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    // 로딩 UI를 표시하거나 진행 중인 작업을 수행
                    showLoadingUI()
                }

                is UiState.None -> {
                    // 로딩 UI를 숨기고 일반 UI를 표시
                    hideLoadingUI()
                }

                is UiState.Error -> {
                    // 에러 메시지를 토스트로 띄우거나 사용자에게 알림
                    hideLoadingUI()
                    showCustomDialog(uiState.message)
                }

                is UiState.Success<*> -> {
                    // 성공 상태 처리 (필요한 경우)
                }
            }
        })

        binding.apply {
            searchButton.setOnClickListener {
                val searchText = searchBar.text.toString()

                if (searchText.isNotEmpty()) {
                    // 검색 바에 입력된 내용이 비어 있지 않으면 showToast 호출
                    lifecycleScope.launch {
                        Snackbar.make(
                            requireView(),
                            searchBar.text.toString() + " 검색합니다.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        viewModel.getTickerDetails(searchText)
                    }
                } else {
                    // 검색 바에 입력된 내용이 비어 있을 때 에러 메시지 표시
                    showToast("검색어를 입력하세요.")
                }
            }

            gridRecyclerView.layoutManager =
                GridLayoutManager(requireContext(), 4) // 그리드 레이아웃 설정
            val items = mutableListOf(
                SearchItem("아이템 1"),
                SearchItem("아이템 2"),
                SearchItem("아이템 3"),
                // 여기에 더 많은 아이템을 추가할 수 있습니다.
            )
            val adapter = SearchAdapter(items)
            gridRecyclerView.adapter = adapter
        }
    }

    private fun showLoadingUI() {
        // 이전에 로딩 UI를 제거
        hideLoadingUI()
        // 로딩 UI를 추가
        binding.root.addView(loadingView)
    }

    private fun hideLoadingUI() {
        // 로딩 UI를 제거
        binding.root.removeView(loadingView)
    }

    private fun showToast(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showCustomDialog(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
            .setPositiveButton("확인") { dialog, _ ->
                // 확인 버튼 클릭 시 실행할 동작
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }
}