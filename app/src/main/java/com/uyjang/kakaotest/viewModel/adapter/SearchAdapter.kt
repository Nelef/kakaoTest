package com.uyjang.kakaotest.viewModel.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uyjang.kakaotest.data.remote.model.Document
import com.uyjang.kakaotest.databinding.ItemSearchBinding

class SearchAdapter(
    private val itemList: List<Document>,
    private val favoriteList: List<Document>,
    private val onItemClick: (Document) -> Unit // 아이템 클릭 리스너 추가
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView = binding.imageView
        private val textView = binding.textDateTime
        private val buttonFavorite = binding.buttonFavorite

        init {
            // buttonFavorite 클릭 리스너 설정
            buttonFavorite.setOnClickListener {
                // buttonFavorite가 클릭되었을 때의 동작
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = itemList[position]
                    onItemClick(item)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(document: Document) {
            // Glide를 사용하여 이미지를 로드하고 imageView에 설정
            Glide.with(itemView.context)
                .load(document.imageUrl)
                .into(imageView)

            // datetime 값을 "2023-01-01 01:01" 형식으로 변환
            var dateTime = document.datetime

            try {
                dateTime = dateTime.replace("T", " ").substringBefore(".")
            } catch (e: Exception) {
                Log.e("dateTime.replace", e.toString())
            }

            textView.text = dateTime + " " + document.displaySitename
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
