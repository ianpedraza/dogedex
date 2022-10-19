package com.ianpedraza.dogedex.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ianpedraza.dogedex.databinding.FragmentDogsListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogsListFragment : Fragment() {

    private var _binding: FragmentDogsListBinding? = null
    private val binding: FragmentDogsListBinding get() = _binding!!

    private val viewModel: DogsListViewModel by viewModels()

    private lateinit var adapter: DogsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeObservers()
    }

    private fun setupUI() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = DogsListAdapter()
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.recyclerViewDogsList.apply {
            layoutManager = manager
            this.adapter = this@DogsListFragment.adapter
        }
    }

    private fun subscribeObservers() {
        viewModel.dogsList.observe(viewLifecycleOwner) { dogs ->
            adapter.submitList(dogs)
        }
    }
}
