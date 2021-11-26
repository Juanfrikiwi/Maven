package com.example.marvelcharacters.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.marvelcharacters.R
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.databinding.FragmentDetailBinding
import com.example.marvelcharacters.ui.ComicsAdapter
import com.example.marvelcharacters.ui.FavoritesAdapter
import com.example.marvelcharacters.utilities.Mappers
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import android.net.Uri

import android.content.Intent
import android.widget.ImageView
import com.example.marvelcharacters.utilities.ImageUtils


@AndroidEntryPoint

class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    lateinit var adapter: ComicsAdapter
    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModels()
    private var chartersJob: Job? = null
    var characterToAdd: CharactersEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        ).apply {
            viewModel = detailViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        getCharacter()
        initComicAdapter()
        binding.loadingState.apply {
            ivReload.setOnClickListener {
                ivReload.visibility = View.GONE
                getCharacter()
            }
        }
        binding.callback = Callback {
            chartersJob?.cancel()
            chartersJob = lifecycleScope.launch {
                if (characterToAdd?.let {
                        binding.btnFavorite.isEnabled = false
                        detailViewModel.addFavourite(it)
                    } == true) {

                    Snackbar.make(
                        binding.root,
                        getString(R.string.charter_added),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                    binding.btnFavorite.isEnabled = false

                } else {
                    binding.btnFavorite.isEnabled = true
                    Snackbar.make(
                        binding.root,
                        getString(R.string.error_ocurred),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }
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

    override fun onDestroyView() {
        super.onDestroyView()

    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        binding.loadingState.ivReload.setOnClickListener {
            it.visibility = View.GONE
            getCharacter()
        }
    }

    private fun initObservers() {

        // Observer that runs when there is a correct response in the getCharacter call
        detailViewModel.successResponse.observe(viewLifecycleOwner) { characters ->
            val characterSelected = characters.firstOrNull()
            characterSelected?.let {
                characterToAdd = Mappers.mapperToEntity(characterResponse = it)
            }
            characterSelected.let {
                bindingData(characterToAdd)
                detailViewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
                    if (isFavorite) {
                        binding.btnFavorite.visibility = View.GONE
                    } else {
                        binding.btnFavorite.visibility = View.VISIBLE
                    }
                }
            }
        }

        //Observer that runs when there is a failed response on the getCharacter call
        detailViewModel.errorResponse.observe(viewLifecycleOwner) {
            binding.apply {
                loadingState.tvMessageLoading.text =
                    getString(R.string.message_error_connection)
                loadingState.tvMessageLoading.visibility = View.VISIBLE
                loadingState.ivReload.visibility = View.VISIBLE
                loadingState.progressBar.visibility = View.GONE
            }
        }
    }


    private fun getCharacter() {
        binding.apply {
            loadingState.progressBar.visibility = View.VISIBLE
            loadingState.tvMessageLoading.text = getString(R.string.message_loading)
            loadingState.tvMessageLoading.visibility = View.VISIBLE
        }
        lifecycleScope.launch {
            detailViewModel.getCharacter(args.characterId)
        }
    }

    private fun bindingData(itemCharacter: CharactersEntity?) {
        binding.apply {
            loadingState.tvMessageLoading.visibility = View.GONE
            loadingState.progressBar.visibility = View.GONE
            loadingState.ivReload.visibility = View.GONE
            tvName.text = itemCharacter!!.name
            ImageUtils.loadImage(requireContext(),itemCharacter.thumbnail_path,binding.ivDetailImage)
            tvDescription.text =
                if (itemCharacter.description != "") itemCharacter.description else getString(R.string.character_without_description)
            tvModified.text = getString(R.string.updated_on) + " " + itemCharacter.modified
            (comicsList.adapter as ComicsAdapter).submitList(itemCharacter.comics)
        }
    }

    fun interface Callback {
        fun add(character: CharactersEntity?)
    }
}