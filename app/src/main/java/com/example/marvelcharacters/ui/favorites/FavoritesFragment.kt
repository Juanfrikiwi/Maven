package com.example.marvelcharacters.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.marvelcharacters.R
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.databinding.FragmentHomeBinding
import com.example.marvelcharacters.ui.FavoritesAdapter
import com.example.marvelcharacters.ui.dialogs.GenericDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint

class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var chartersJob: Job? = null
    lateinit var adapter: FavoritesAdapter
    lateinit var listCharacters: List<CharactersEntity>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FavoritesAdapter(
            object : FavoritesAdapter.ListItemClickListener {
                override fun onDeleteItem(charactersEntity: CharactersEntity) {
                    val dialog = GenericDialog
                    dialog.open(
                        onAccept = {
                            chartersJob?.cancel()
                            chartersJob = lifecycleScope.launch {
                                if (viewModel.deleteCharacter(charactersEntity)) {
                                    Snackbar.make(binding.root, getString(R.string.favorite_character_deleted), Snackbar.LENGTH_LONG)
                                        .show()
                                } else {
                                    Snackbar.make(binding.root, getString(R.string.error_ocurred), Snackbar.LENGTH_LONG)
                                        .show()
                                }
                            }
                        }
                    ).show(childFragmentManager,tag)

                }
            }
        )
        binding.characterList.adapter = adapter
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: FavoritesAdapter) {
        viewModel.characters.observe(viewLifecycleOwner) { characters ->
            listCharacters = characters
            if (characters.isNotEmpty()) {
                binding.tvEmptyList.visibility = View.GONE
                adapter.submitList(characters)
            } else {
                adapter.submitList(emptyList())
                binding.tvEmptyList.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}