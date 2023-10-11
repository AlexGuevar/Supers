package com.killacorp.supers.view.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.killacorp.supers.R
import com.killacorp.supers.databinding.HomeFragmentLayoutBinding
import com.killacorp.supers.utils.Extras
import com.killacorp.supers.view.home.adapter.HeroAdapter
import com.killacorp.supers.view.home.vm.HeroesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var initialSize = 20
    private val viewModel: HeroesViewModel by viewModels()
    private lateinit var heroAdapter: HeroAdapter
    private lateinit var binding : HomeFragmentLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<TextView>(R.id.tvTitle)?.visibility = View.VISIBLE

        handleLoading()
        handleError()
        setUpRecyclerView()
        loadData()
        loadMore()
    }

    private fun handleError() {
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                binding.bottomText.text = errorMessage
                binding.bottomText.visibility = View.VISIBLE
            } else {
                binding.bottomText.visibility = View.GONE
            }
        }
    }

    private fun handleLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        heroAdapter = HeroAdapter()
        binding.recyclerView.adapter = heroAdapter
    }

    private fun loadData() {
        viewModel.loadData(Extras.apiKey, initialSize)
        viewModel.superHero.observe(viewLifecycleOwner) { newHeroes ->
            heroAdapter.addAll(newHeroes)
            heroAdapter.onClick(object : HeroAdapter .OnHeroListener {
                override fun call(id: String?) {
                    val navDirections = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id)
                    findNavController(requireView()).navigate(navDirections)
                }
            })
        }
    }

    private fun loadMore() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && viewModel.isLoading.value == false) {
                    viewModel.loadData(Extras.apiKey, heroAdapter.itemCount.plus(initialSize))
                    viewModel.superHero.observe(viewLifecycleOwner) { newHeroes ->
                        heroAdapter.addAll(newHeroes)
                        heroAdapter.onClick(object : HeroAdapter.OnHeroListener {
                            override fun call(id: String?) {
                                val navDirections = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id)
                                findNavController(requireView()).navigate(navDirections)
                            }
                        })
                    }
                }
            }
        })
    }
}