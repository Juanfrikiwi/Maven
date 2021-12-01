package com.example.marvelcharacters.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.databinding.ListItemCharacterBinding
import com.example.marvelcharacters.ui.viewpager.HomeViewPagerFragmentDirections
import com.example.marvelcharacters.utilities.ImageUtils


class HomeAdapter: PagingDataAdapter<CharactersEntity, HomeAdapter.CharactersViewHolder>(CharactersDiffCallback()) {

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

        private fun navigateToDetail(characterId: Int, view: View) {
            val direction = HomeViewPagerFragmentDirections
                .actionViewPagerFragmentToDetailFragment(characterId)
            view.findNavController().navigate(direction)
        }

        fun bind(item: CharactersEntity) {
            binding.apply {
                binding.setClickListener { view ->
                    navigateToDetail(item.idCharacter, view)
                }
                tvName.text = item.name
                ImageUtils.loadImage(ivPhoto.context,item.thumbnail_path,binding.ivPhoto)
                executePendingBindings()
            }
        }

    }
}

private class CharactersDiffCallback : DiffUtil.ItemCallback<CharactersEntity>() {
    override fun areItemsTheSame(oldItem: CharactersEntity, newItem: CharactersEntity): Boolean {
        return oldItem.idCharacter == newItem.idCharacter
    }

    override fun areContentsTheSame(oldItem: CharactersEntity, newItem: CharactersEntity): Boolean {
        return oldItem == newItem
    }
}
