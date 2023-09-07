package com.uyjang.kakaotest.view.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.uyjang.kakaotest.R
import com.uyjang.kakaotest.base.UiState
import com.uyjang.kakaotest.data.remote.model.Document
import com.uyjang.kakaotest.databinding.FragmentSearchBinding
import com.uyjang.kakaotest.viewModel.SearchViewModel
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
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
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
                    showCustomDialog(uiState.message) { viewModel.setUIState(UiState.None) }
                }

                is UiState.Success<*> -> {
                    // 성공 상태 처리 (필요한 경우)
                }
            }
        }

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
                    showSnackbar("검색어를 입력하세요.")
                }
            }
            testButton1.setOnClickListener {
                viewModel.updateItems(clear = true)
            }
            testButton2.setOnClickListener {
                viewModel.updateItems(
                    mutableListOf(
                        Document(
                            collection = "example_collection1",
                            datetime = "2023-09-06 10:30:00",
                            displaySitename = "Example Site 1",
                            docUrl = "https://example.com/doc1",
                            height = 800,
                            imageUrl = "https://search2.kakaocdn.net/argon/130x130_85_c/3CKSw1lgNrM",
                            thumbnailUrl = "https://search2.kakaocdn.net/argon/130x130_85_c/3CKSw1lgNrM",
                            width = 1200
                        ),
                        Document(
                            collection = "example_collection2",
                            datetime = "2023-09-06 11:45:00",
                            displaySitename = "Example Site 2",
                            docUrl = "https://example.com/doc2",
                            height = 600,
                            imageUrl = "https://search4.kakaocdn.net/argon/130x130_85_c/LeDRsSMTETJ",
                            thumbnailUrl = "https://search4.kakaocdn.net/argon/130x130_85_c/LeDRsSMTETJ",
                            width = 900
                        ),
                        Document(
                            collection = "example_collection3",
                            datetime = "2023-09-06 14:20:00",
                            displaySitename = "Example Site 3",
                            docUrl = "https://example.com/doc3",
                            height = 1200,
                            imageUrl = "https://search1.kakaocdn.net/argon/130x130_85_c/HB6IuG80j3h",
                            thumbnailUrl = "https://search1.kakaocdn.net/argon/130x130_85_c/HB6IuG80j3h",
                            width = 1800
                        )
                    )
                )
            }

            gridRecyclerView.layoutManager =
                GridLayoutManager(requireContext(), 4) // 그리드 레이아웃 설정
            gridRecyclerView.adapter = viewModel.adapter
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

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showCustomDialog(message: String, onClick: () -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
            .setPositiveButton("확인") { dialog, _ ->
                // 확인 버튼 클릭 시 실행할 동작
                onClick()
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }
}