package com.takehomechallenge.sulfa.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.takehomechallenge.sulfa.R
import com.takehomechallenge.sulfa.api.response.ResultsItem
import com.takehomechallenge.sulfa.databinding.ActivityHomePageBinding
import com.takehomechallenge.sulfa.ui.adapter.CharacterAdapter
import com.takehomechallenge.sulfa.ui.viewmodel.HomeViewModel

class HomePage : AppCompatActivity() {

    private lateinit var binding : ActivityHomePageBinding
    private val homeViewModel by viewModels<HomeViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel.listUser.observe(this) { listUser ->
            val adapter = CharacterAdapter(listUser)
            binding.rvUsers.adapter = adapter
            adapter.setOnItemCallback(object :CharacterAdapter.OnItemCallback{
                override fun onitemclick(data: ResultsItem) {
                    val intent= Intent(
                        this@HomePage,DetailPage::class.java)
                    intent.putExtra("id",data.id)
                    startActivity(intent)
                }
            })
        }

        homeViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvUsers.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvUsers.visibility = View.VISIBLE
            }
        }
        rvSetting()
    }
    private fun rvSetting() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.appbar, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                homeViewModel.getDataUser(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.favorite -> {
                val favoriteIntent = Intent(
                    this@HomePage,
                    FavoriteActivity::class.java
                )
                startActivity(favoriteIntent)
                true
            }
            else -> true
        }
    }
}