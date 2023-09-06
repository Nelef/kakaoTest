package com.uyjang.kakaotest.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import com.uyjang.kakaotest.base.BaseFragment
import com.uyjang.kakaotest.databinding.FragmentMainBinding
import com.uyjang.kakaotest.view.ui.FavoriteFragment
import com.uyjang.kakaotest.view.ui.SearchFragment
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

        // 탭 선택 이벤트 리스너 추가 등의 작업을 수행할 수 있습니다.

        val fragmentList = listOf(
            SearchFragment(),
            FavoriteFragment()
        )

        binding.apply {
            viewPager.adapter = object : FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                override fun getCount(): Int = fragmentList.size

                override fun getItem(position: Int): Fragment = fragmentList[position]
            }

            tabLayout.setupWithViewPager(viewPager)
            tabLayout.getTabAt(0)?.text = "검색하기"
            tabLayout.getTabAt(1)?.text = "즐겨찾기"
        }
    }
}