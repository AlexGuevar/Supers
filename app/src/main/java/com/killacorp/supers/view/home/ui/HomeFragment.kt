package com.killacorp.supers.view.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.killacorp.supers.R
import com.killacorp.supers.databinding.HomeFragmentLayoutBinding
import com.killacorp.supers.utils.Extras
import com.killacorp.supers.view.home.adapter.HeroAdapter
import com.killacorp.supers.view.home.vm.HereosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var initialSize = 20
    private val viewModel: HereosViewModel by viewModels()
    private lateinit var heroAdapter: HeroAdapter
    private lateinit var binding : HomeFragmentLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.findViewById<TextView>(R.id.title).visibility = View.VISIBLE
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)

        heroAdapter = HeroAdapter()
        binding.recyclerView.adapter = heroAdapter

        viewModel.isLoading.observe(viewLifecycleOwner){
            if(it){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        lifecycleScope.launch {
            viewModel.loadData(Extras.apiKey,initialSize)
            viewModel.heroes.observe(viewLifecycleOwner){
                heroAdapter.addAll(it)
                heroAdapter.onClick(object : HeroAdapter.OnHereoListener{
                    override fun call(id: String) {
                        val navDirections = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id)
                        findNavController(requireView()).navigate(navDirections)
                    }
                })
            }
        }



        // LOAD MORE DATA ON SCROLL
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (viewModel.isLoading.value == false) {
                        lifecycleScope.launch {
                            viewModel.loadData(Extras.apiKey, heroAdapter.itemCount + initialSize)
                            viewModel.heroes.observe(viewLifecycleOwner) { newHeroes ->
                                heroAdapter.addAll(newHeroes)
                                heroAdapter.onClick(object : HeroAdapter.OnHereoListener{
                                    override fun call(id: String) {
                                        val navDirections = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id)
                                        findNavController(requireView()).navigate(navDirections)
                                    }
                                })
                            }
                        }
                    }
                }
            }
        })


    }
}