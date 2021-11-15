package com.example.marvelcharacters.ui.favourites

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.marvelcharacters.R
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.data.network.models.CharactersResponse
import com.example.marvelcharacters.databinding.FragmentDetailBinding
import com.example.marvelcharacters.databinding.FragmentDetailFavoritesBinding
import com.example.marvelcharacters.databinding.FragmentHomeBinding
import com.example.marvelcharacters.ui.FavoritesAdapter
import com.example.marvelcharacters.ui.detail.DetailFragmentArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint

class DetailFavoritesFragment : Fragment() {
    private var _binding: FragmentDetailFavoritesBinding? = null
    private val detailFavoritesViewModel: DetailFavoritesViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailFavoritesBinding.inflate(inflater, container, false)
        bindingData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun bindingData(){
        detailFavoritesViewModel.getCharacter(args.characterId)
        detailFavoritesViewModel.character.observe(viewLifecycleOwner) { characters ->
            if(characters != null){
                binding.apply {
                    tvName.text = characters.name
                    loadImage(characters.thumbnail_path)
                }
            }else{
                Snackbar.make(binding.root, getString(R.string.error_ocurred), Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun loadImage(thumbnailPath: String) {
        Glide.with(requireContext()).load(
            thumbnailPath.replace(
                "http",
                "https"
            )
        ).into(binding.ivDetailImage)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}