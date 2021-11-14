package com.example.marvelcharacters.ui.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.marvelcharacters.databinding.FragmentHomeBinding
import com.example.marvelcharacters.ui.FavoritesAdapter
import com.example.marvelcharacters.ui.HomeAdapter
import com.example.marvelcharacters.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint

class FavoritesFragment : Fragment() {
    private val adapter = FavoritesAdapter()
    private val viewModel: FavoritesViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.characterList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun subscribeUi(adapter: FavoritesAdapter) {
        viewModel.characters.observe(viewLifecycleOwner) { characters ->
            adapter.submitList(characters)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}