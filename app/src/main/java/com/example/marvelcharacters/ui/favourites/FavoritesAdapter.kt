package com.example.marvelcharacters.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.databinding.ListItemCharacterBinding
import com.example.marvelcharacters.ui.viewpager.HomeViewPagerFragmentDirections


class FavoritesAdapter(
    val listItemClickListener: ListItemClickListener
): ListAdapter<CharactersEntity, FavoritesAdapter.CharactersViewHolder>(CharactersFavoritesDiffCallback()) {

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
            holder.btnDelete.setOnClickListener {
                listItemClickListener.onDeleteItem(character)
            }
        }
    }

    class CharactersViewHolder(
        private val binding: ListItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val btnDelete = binding.ivDelete
        init {
            binding.ivDelete.visibility = View.VISIBLE
            binding.setClickListener { view ->
                    navigateToDetail(binding.characterEntity!!.idCharacter, view)
            }
        }
        private fun navigateToDetail(characterId: Int, view: View) {
            val direction = HomeViewPagerFragmentDirections
                .actionViewPagerFragmentToDetailFavoritesFragment(characterId)
            view.findNavController().navigate(direction)
        }

        fun bind(item: CharactersEntity) {
            binding.apply {
                characterEntity = item
                Glide.with(ivPhoto.context).
                load(item.thumbnail_path.replace("http","https")).into(ivPhoto)
                executePendingBindings()
            }
        }
    }
    interface ListItemClickListener{
        fun onDeleteItem(charactersEntity: CharactersEntity)
    }

}

private class CharactersFavoritesDiffCallback : DiffUtil.ItemCallback<CharactersEntity>() {
    override fun areItemsTheSame(oldItem: CharactersEntity, newItem: CharactersEntity): Boolean {
        return oldItem.idCharacter == newItem.idCharacter
    }

    override fun areContentsTheSame(oldItem: CharactersEntity, newItem: CharactersEntity): Boolean {
        return oldItem == newItem
    }
}
