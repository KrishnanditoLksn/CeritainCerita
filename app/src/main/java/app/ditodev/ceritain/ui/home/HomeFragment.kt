package app.ditodev.ceritain.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import app.ditodev.ceritain.databinding.FragmentHomeBinding
import app.ditodev.ceritain.ui.adapter.StoriesListAdapter
import app.ditodev.ceritain.ui.viewmodels.DisplayStoryViewModel
import app.ditodev.ceritain.ui.viewmodels.factories.StoryViewModelFactory
import app.ditodev.ceritain.utils.Utils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel by viewModels<DisplayStoryViewModel> {
        StoryViewModelFactory.getInstance(requireActivity())
    }
    private val homeAdapter = StoriesListAdapter()
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayList()
        binding.rvStory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }
        Utils.showLoading(false, binding.progressBar)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayList() {
        homeViewModel.getStoriesPaging().observe(viewLifecycleOwner) { result ->
            homeAdapter.submitData(lifecycle, result)
        }

        homeAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.Loading -> {
                    Utils.showLoading(true, binding.progressBar)
                }

                is LoadState.NotLoading -> {
                    Utils.showLoading(false, binding.progressBar)
                }

                is LoadState.Error -> {
                    Utils.showLoading(false, binding.progressBar)
                    Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getStoriesPaging()
    }
}