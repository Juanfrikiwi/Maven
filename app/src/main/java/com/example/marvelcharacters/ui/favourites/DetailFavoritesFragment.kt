package com.example.marvelcharacters.ui.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.marvelcharacters.R
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.databinding.FragmentDetailFavoritesBinding
import com.example.marvelcharacters.ui.detail.DetailFragmentArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailFavoritesFragment : Fragment() {
    lateinit var binding: FragmentDetailFavoritesBinding
    private val detailFavoritesViewModel: DetailFavoritesViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailFavoritesViewModel.getCharacter(args.characterId).observe(viewLifecycleOwner) { characters ->
            if(characters != null){
                bindingData(characters)
            }else{
                Snackbar.make(binding.root, getString(R.string.error_ocurred), Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        initListeners()
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }

    fun bindingData(characters: CharactersEntity) {
        detailFavoritesViewModel.getCharacter(args.characterId)
        binding.apply {
            tvName.text = characters.name
            loadImage(characters.thumbnail_path)
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

    }


}