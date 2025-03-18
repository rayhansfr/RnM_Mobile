package com.example.rnmmobile.presentation.main.adapter

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.core.domain.model.CharacterDomain
import com.example.rnmmobile.R
import com.example.rnmmobile.databinding.CharCardBinding
import com.example.rnmmobile.presentation.detail.DetailActivity

class CharacterListAdapter :
    ListAdapter<CharacterDomain, CharacterListAdapter.CharacterViewHolder>(DIFF_CALLBACK) {

    class CharacterViewHolder(private val binding: CharCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterDomain) {
            binding.charName.text = character.name
            character.image.let {
                Glide.with(binding.charImage.context)
                    .load(it)
                    .timeout(5000)
                    .error(R.drawable.baseline_image_not_supported_24)
                    .listener(object : RequestListener<Drawable> {

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.progressBar.visibility = View.GONE
                            return false
                        }
                    })
                    .into(binding.charImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
        holder.itemView.setOnClickListener {

            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("id", character.id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CharacterDomain>() {
            override fun areItemsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean {
                return oldItem == newItem
            }
        }
    }
}

