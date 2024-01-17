package com.takehomechallenge.sulfa.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.takehomechallenge.sulfa.database.CharacterFavorite
import com.takehomechallenge.sulfa.databinding.ActivityFavoriteBinding
import com.takehomechallenge.sulfa.repository.FavoriteRepository
import com.takehomechallenge.sulfa.ui.adapter.FavoriteAdapter
import com.takehomechallenge.sulfa.ui.viewmodel.FavoriteViewModel
import com.takehomechallenge.sulfa.utils.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private val favoriteBinding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }
    private val favoriteFactory: ViewModelFactory by lazy {
        ViewModelFactory(this)
    }

    private val favoriteViewModel by lazy {
        ViewModelProvider(
            this,
            favoriteFactory
        )[FavoriteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(favoriteBinding.root)

        initView()
        initObserver()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {
//        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        favoriteBinding.apply {

        }
    }

    private fun initObserver() {
        favoriteViewModel.apply {
            favUserList.observe(this@FavoriteActivity) { favorite ->
                favoriteBinding.progressBar.visibility = View.VISIBLE
                if (favorite.isEmpty()) {
                    favoriteBinding.progressBar.visibility = View.GONE
                } else {
                    favoriteBinding.progressBar.visibility = View.GONE
                }
                showFavorite(favorite)
            }
        }
    }

    private fun showFavorite(favorite: List<CharacterFavorite>) {
        val favoriteAdapter = FavoriteAdapter(
            favorite,
            FavoriteRepository(this)
        )
        favoriteBinding.rvfavorite.layoutManager = LinearLayoutManager(this)
        favoriteBinding.rvfavorite.adapter = favoriteAdapter
        favoriteAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: CharacterFavorite) {
                val favoriteIntent = Intent(
                    this@FavoriteActivity,
                    DetailPage::class.java
                )
                favoriteIntent.putExtra("id", data.id)
                favoriteIntent.putExtra("data", data)
                startActivity(favoriteIntent)
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}