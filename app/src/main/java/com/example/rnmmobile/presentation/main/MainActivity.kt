package com.example.rnmmobile.presentation.main

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rnmmobile.R
import com.example.rnmmobile.databinding.ActivityMainBinding
import com.example.rnmmobile.presentation.main.adapter.CharacterListAdapter

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val characterAdapter by lazy { CharacterListAdapter() }
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupRecyclerView()
        observeData()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.isErr.observe(this) {
            showErrorDialog(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                val uri = Uri.parse("rnmmobile://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showErrorDialog(isErr: Boolean) {
        if (isErr) {
            AlertDialog.Builder(this)
                .setTitle("Error when getting Data")
                .setMessage("Try again?")
                .setPositiveButton("Retry") { _, _ ->
                    viewModel.fetchCharacters()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        binding.recyclerViewMember.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = characterAdapter
        }
    }

    private fun observeData() {
        viewModel.characters.observe(this) { characters ->
            Log.d("API Output", "observeData: $characters")
            characterAdapter.submitList(characters)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}