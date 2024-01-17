package com.takehomechallenge.sulfa.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.takehomechallenge.sulfa.R
import com.takehomechallenge.sulfa.api.response.ResultsItem
import com.takehomechallenge.sulfa.databinding.ActivityDetailPageBinding
import com.takehomechallenge.sulfa.ui.viewmodel.DetailViewModel
import com.takehomechallenge.sulfa.utils.ViewModelFactory

class DetailPage : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPageBinding
    private val factory: ViewModelFactory by lazy {
        ViewModelFactory(this)
    }
    private val detailViewModel by lazy {
        ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }
    private val user: String by lazy {
        intent.extras?.getString("id", "") ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initObserver()
    }

    private fun initObserver() {
        detailViewModel.character.observe(this@DetailPage, { itemsUser ->
            getUser(itemsUser)
        })

        detailViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.cv.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.cv.visibility = View.VISIBLE
            }
        }
        detailViewModel.getDataUser(user)
    }

    private fun getUser(itemsUser: ResultsItem) {

        binding.imgProfile.loadImage(itemsUser.image)
        binding.tvName.text = itemsUser.name
        binding.tvSpecies.text = itemsUser.species
        binding.tvGender.text = itemsUser.gender
        binding.tvOrigin.text = itemsUser.origin?.name
        binding.tvLocation.text = itemsUser.location?.name
        binding.apply {
            detailViewModel.favoriteCharacter.observe(this@DetailPage){favorite ->
                if (favorite.isNotEmpty()){
                    val tempUser = favorite.find { it.name == user }
                    if (tempUser != null){
                        btnFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                            this@DetailPage,R.drawable.favorite_red))
                        btnFavorite.setOnClickListener{
                            detailViewModel.deleteFavoriteUser(tempUser)
                        }
                    }else{
                        btnFavorite.setImageDrawable(
                            ContextCompat.getDrawable(
                            this@DetailPage,R.drawable.baseline_favorite_border_24))
                        btnFavorite.setOnClickListener{
                            detailViewModel.insertFavoriteUser(
                                itemsUser)
                        }
                    }
                }else{
                    btnFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                        this@DetailPage,R.drawable.baseline_favorite_border_24))
                    btnFavorite.setOnClickListener{
                        detailViewModel.insertFavoriteUser(
                            itemsUser
                        )
                    }

                }
            }
        }
    }

    private fun ImageView.loadImage(avatarUrl: String?) {
        Glide.with(this.context).load(avatarUrl).apply(
            RequestOptions().override(200, 200)
        ).centerCrop().circleCrop().into(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}

