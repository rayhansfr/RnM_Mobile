package com.example.rnmmobile.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.core.domain.model.CharacterDomain
import com.example.rnmmobile.R
import com.example.rnmmobile.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailActivityViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        val id = intent.getIntExtra("id", -1)
        if (id != -1) {
            detailViewModel.setDetailId(id)
            Log.d("Char ID : ", id.toString())

            detailViewModel.character.observe(this) { char ->
                if (char != null) {
                    detailViewModel.checkFavoriteStatus(char.id)
                    setDetail(char)
                    binding.favouriteButton.setOnClickListener{
                        val charFav = CharacterDomain(
                            id = char.id,
                            name = char.name,
                            image = char.image,
                            gender = char.gender,
                            species = char.species,
                            status = char.status
                            )
                        detailViewModel.toggleFavorite(charFav)
                    }
                } else {
                    Log.e("DetailActivity", "Data char null")
                }
            }

            detailViewModel.isLoading.observe(this) {
                showLoading(it)
            }

            detailViewModel.isErr.observe(this) {
                if (it) {
                    android.app.AlertDialog.Builder(this)
                        .setTitle(detailViewModel.errMsg.value)
                        .setMessage("Try again?")
                        .setPositiveButton("Retry") { _, _ ->
                            detailViewModel.setDetailId(id)
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                }
            }

            detailViewModel.isFav.observe(this) { isFav ->
                if (isFav) {
                    binding.favouriteButton.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.favouriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }

            }
        else {
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Invalid")
                .setMessage("Character ID not found")
                .setPositiveButton("OK") { _, _ ->
                    finish()
                }
                .setCancelable(false)
                .create()

            alertDialog.show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setDetail(char: CharacterDomain) {
        Glide.with(this)
            .load(char.image)
            .into(binding.charImage)
        binding.charName.text = char.name
        binding.charGender.text = char.gender
        binding.charStatus.text = char.status
        binding.charSpecies.text = char.species
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}