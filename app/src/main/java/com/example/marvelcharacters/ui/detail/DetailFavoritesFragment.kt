package com.example.marvelcharacters.ui.detail

import android.content.Intent
import android.net.Uri
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
import com.example.marvelcharacters.databinding.FragmentDetailBinding
import com.example.marvelcharacters.ui.ComicsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailFavoritesFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    private val detailFavoritesViewModel: DetailFavoritesViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    lateinit var adapter: ComicsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailFavoritesViewModel.getCharacter(args.characterId)
            .observe(viewLifecycleOwner) { characters ->
                if (characters != null) {
                    bindingData(characters)
                } else {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.error_ocurred),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }
        initComicAdapter()
        initListeners()
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }

    fun initComicAdapter() {
        adapter = ComicsAdapter(
            object : ComicsAdapter.ListItemComicClickListener {
                override fun onClickItem(nameComic: String) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/search?q=$nameComic")
                    )
                    startActivity(browserIntent)
                }
            }
        )
        binding.comicsList.adapter = adapter
    }

    fun bindingData(characters: CharactersEntity) {
        detailFavoritesViewModel.getCharacter(args.characterId)
        binding.apply {
            tvName.text = characters.name
            loadImage(characters.thumbnail_path)
            tvDescription.text =
                if (characters.description != "") characters.description else getString(R.string.character_without_description)
            tvModified.text = getString(R.string.updated_on) + " " + characters.modified
            (comicsList.adapter as ComicsAdapter).submitList(characters.comics)
        }
    }

    private fun loadImage(thumbnailPath: String) {
        Glide.with(requireContext()).load(
            thumbnailPath.replace(
                getString(R.string.http_string),
                getString(R.string.https_string)
            )
        ).into(binding.ivDetailImage)
    }


    override fun onDestroyView() {
        super.onDestroyView()

    }


}