package com.example.marvelcharacters.ui


import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.data.models.CharactersEntity
import com.example.marvelcharacters.databinding.ListItemCharacterBinding
import com.example.marvelcharacters.domain.models.Characters
import com.example.marvelcharacters.ui.home.HomeFragmentDirections

class DetailAdapter: PagingDataAdapter<CharactersEntity, DetailAdapter.CharactersViewHolder>(CharacterByIdDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            ListItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = getItem(position)
        if (character != null) {
            holder.bind(character)
        }
    }

    class CharactersViewHolder(
        private val binding: ListItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.tvName?.let { name ->
                navigateToDetail(binding.character!!.id, view)
                }
            }
        }
        private fun navigateToDetail(characterId: Int?, view: View) {
            val direction = characterId?.let {
                HomeFragmentDirections
                    .actionHomeFragmentToDetailFragment(it)
            }
            direction?.let { view.findNavController().navigate(it) }
        }

        fun bind(item: CharactersEntity) {
            binding.apply {
                character = item
                executePendingBindings()
            }
        }

    }
}

private class CharacterByIdDiffCallback : DiffUtil.ItemCallback<CharactersEntity>() {
    override fun areItemsTheSame(oldItem: CharactersEntity, newItem: CharactersEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharactersEntity, newItem: CharactersEntity): Boolean {
        return oldItem == newItem
    }
}
