package com.takehomechallenge.sulfa.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.takehomechallenge.sulfa.database.CharacterFavorite
import com.takehomechallenge.sulfa.databinding.ItemFavoriteBinding
import com.takehomechallenge.sulfa.repository.FavoriteRepository

class FavoriteAdapter (
    private val favoriteList: List<CharacterFavorite>,
    private val favoriteRepository: FavoriteRepository
    ): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

        private lateinit var onItemClickCallback: OnItemClickCallback

        class FavoriteViewHolder(var favoriteBinding: ItemFavoriteBinding) :
            RecyclerView.ViewHolder(favoriteBinding.root)

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): FavoriteViewHolder {
            val favoriteBinding = ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return FavoriteViewHolder(favoriteBinding)
        }

        override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
            val favoriteUser = favoriteList[position]
            Glide.with(holder.itemView.context)
                .load(favoriteUser.image)
                .circleCrop()
                .into(holder.favoriteBinding.imgProfile)
            holder.favoriteBinding.imgProfile.loadImage(favoriteUser.image)
            holder.favoriteBinding.tvName.text = favoriteUser.name
            holder.favoriteBinding.btnDelete.setOnClickListener {
                favoriteRepository.deleteFavorite(favoriteUser)
            }
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(favoriteList[holder.adapterPosition])
            }
        }

        private fun ImageView.loadImage(avatarUrl: String?) {
            Glide.with(this.context) .load(avatarUrl) .apply(
                RequestOptions().override(200, 200)
            ) .centerCrop() .circleCrop() .into(this)
        }

        override fun getItemCount(): Int = favoriteList.size

        fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
            this.onItemClickCallback = onItemClickCallback
        }

        interface OnItemClickCallback {
            fun onItemClicked(data: CharacterFavorite)
        }
}