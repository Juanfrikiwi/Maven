package com.example.marvelcharacters.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.marvelcharacters.R
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.data.network.models.CharactersResponse
import com.example.marvelcharacters.data.network.models.ImageResponse
import com.example.marvelcharacters.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
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
        getCharacter()
        binding.loadingState.apply {
            ivReload.setOnClickListener {
                ivReload.visibility = View.GONE
                getCharacter()
            }
        }
        binding.callback = Callback { character ->
            chartersJob?.cancel()
            chartersJob = lifecycleScope.launch {
                if (characterToAdd?.let { detailViewModel.addFavourite(it) } == true) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.charter_added),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                } else {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.error_ocurred),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }
        }

        initListeners()
        initObservers()
        getCharacter()
    }

    private fun initListeners() {
        binding.loadingState.ivReload.setOnClickListener {
            it.visibility = View.GONE
            getCharacter()
        }
    }

    private fun initObservers() {
        // Observer that runs when there is a correct response in the getCharacter call
        detailViewModel.successResponse.observe(viewLifecycleOwner) { characters ->
            val characterSelected = characters.firstOrNull()
            characterToAdd = characterSelected?.let { mapperToEntity(characterResponse = it) }
            characterSelected.let { itemCharacter ->
                bindingData(itemCharacter)
            }
        }
        detailViewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite){
                binding.btnFavorite.visibility = View.GONE
            }
        }

        //Observer that runs when there is a failed response on the getCharacter call
        detailViewModel.errorResponse.observe(viewLifecycleOwner) { msg ->
            binding.apply {
                loadingState.tvMessageLoading.text =
                    getString(R.string.message_error_connection)
                loadingState.tvMessageLoading.visibility = View.VISIBLE
                loadingState.ivReload.visibility = View.VISIBLE
                loadingState.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

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
            tvDescription.text = itemCharacter.description
        }
    }

    private fun loadImage(thumbnail: ImageResponse) {
        Glide.with(requireContext()).load(
            thumbnail!!.path.replace(
                "http",
                "https"
            ) + "." + thumbnail.extension
        ).into(binding.ivDetailImage)
    }

    fun mapperToEntity(characterResponse: CharactersResponse): CharactersEntity {
        characterResponse.let {
            return CharactersEntity(
                it.id,
                it.name,
                it.description,
                it.modified.toString(),
                it.resourceURI,
                it.thumbnail.path + "." + it.thumbnail.extension,
                listOf("2", "3")
            )
        }
    }

    fun interface Callback {
        fun add(character: CharactersEntity?)
    }
}