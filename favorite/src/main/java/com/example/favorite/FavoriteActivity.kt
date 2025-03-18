package com.example.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favorite.databinding.ActivityFavoriteBinding
import com.example.favorite.di.favoriteModule
import com.example.rnmmobile.presentation.main.adapter.CharacterListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val characterAdapter by lazy { CharacterListAdapter() }
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(favoriteModule)

        setupRecyclerView()
        observeData()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.isErr.observe(this) {
            if (it) {
                android.app.AlertDialog.Builder(this)
                    .setTitle("Failed Getting Data")
                    .setMessage("Try again?")
                    .setPositiveButton("Retry") { _, _ ->
                        observeData()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        binding.recyclerViewMember.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = characterAdapter
        }
    }

    private fun observeData() {
        viewModel.characters.observe(this) { characters ->
            Log.d("Databaase Output", "observeData: $characters")
            characterAdapter.submitList(characters)
        }
    }


}