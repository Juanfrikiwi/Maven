package com.example.marvelcharacters.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelcharacters.data.network.models.CharactersResponse
import com.example.marvelcharacters.databinding.ListItemCharacterBinding
import com.example.marvelcharacters.ui.viewpager.HomeViewPagerFragmentDirections


class HomeAdapter: PagingDataAdapter<CharactersResponse, HomeAdapter.CharactersViewHolder>(CharactersDiffCallback()) {

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
                    binding.character!!.id?.let { navigateToDetail(it, view) }
                }
            }
        }
        private fun navigateToDetail(characterId: Int, view: View) {
            val direction = HomeViewPagerFragmentDirections
                .actionViewPagerFragmentToDetailFragment(characterId)
            view.findNavController().navigate(direction)
        }

        fun bind(item: CharactersResponse) {
            binding.apply {
                character = item
                Glide.with(ivPhoto.context).
                load(item.thumbnail!!.path.replace("http","https")+"."+ item.thumbnail.extension).into(ivPhoto)
                executePendingBindings()
            }
        }

    }
}

private class CharactersDiffCallback : DiffUtil.ItemCallback<CharactersResponse>() {
    override fun areItemsTheSame(oldItem: CharactersResponse, newItem: CharactersResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharactersResponse, newItem: CharactersResponse): Boolean {
        return oldItem == newItem
    }
}
