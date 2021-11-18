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
import com.example.marvelcharacters.data.network.models.CharactersResponse
import com.example.marvelcharacters.data.network.models.ImageResponse
import com.example.marvelcharacters.databinding.FragmentDetailBinding
import com.example.marvelcharacters.utilities.DateUtils
import com.example.marvelcharacters.utilities.Mappers
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@AndroidEntryPoint

class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
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
                        detailViewModel.addFavourite(it) } == true) {

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
            characterToAdd = characterSelected?.let {
                Mappers.mapperToEntity(characterResponse = it)
            }
            characterSelected.let { itemCharacter ->
                bindingData(itemCharacter)
                detailViewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
                    if (isFavorite) {
                        binding.btnFavorite.visibility = View.GONE
                    }else{
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

    private fun bindingData(itemCharacter: CharactersResponse?) {
        binding.apply {
            loadingState.tvMessageLoading.visibility = View.GONE
            loadingState.progressBar.visibility = View.GONE
            loadingState.ivReload.visibility = View.GONE
            tvName.text = itemCharacter!!.name
            loadImage(itemCharacter.thumbnail)
            tvDescription.text = if(itemCharacter.description != "") itemCharacter.description else getString(R.string.character_without_description)
            tvModified.text = getString(R.string.updated_on)+" "+ DateUtils.getDateFormatted(itemCharacter.modified.time)
        }
    }

    private fun loadImage(thumbnail: ImageResponse) {
        Glide.with(requireContext()).load(
            thumbnail.path.replace(
                getString(R.string.http_string),
                getString(R.string.https_string)
            ) + "." + thumbnail.extension
        ).into(binding.ivDetailImage)
    }


    fun interface Callback {
        fun add(character: CharactersEntity?)
    }
}