package com.killacorp.supers.view.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.killacorp.supers.R
import com.killacorp.supers.databinding.DetailsFragmentLayoutBinding
import com.killacorp.supers.utils.Extras
import com.killacorp.supers.view.home.vm.HereosViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val viewModel: HereosViewModel by viewModels()
    private lateinit var binding : DetailsFragmentLayoutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DetailsFragmentLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.findViewById<TextView>(R.id.title).visibility = View.GONE
        val detailsFragmentArgs : DetailsFragmentArgs? = arguments?.let { DetailsFragmentArgs.fromBundle(it) }
        lifecycleScope.launch {
            if (detailsFragmentArgs != null) {
                viewModel.getHeroDetails(Extras.apiKey,detailsFragmentArgs.id)
                viewModel.details.observe(viewLifecycleOwner){ item ->
                    Picasso.get().load(item.image?.url).into(binding.image)

                    binding.powerStats.intelligence.text = "Intelligence : "  + item.powerstats.intelligence
                    binding.powerStats.strength.text = "Strength :" + item.powerstats.strength
                    binding.powerStats.speed.text = "Speed : " + item.powerstats.speed
                    binding.powerStats.durability.text = "Durability " + item.powerstats.durability


                    ///--------
                    binding.biography.fullName.text = "Full Name : " + item.biography.fullName
                    binding.biography.placeOfBirth.text = "Place Of Birth : " + item.biography.placeOfBirth
                    binding.biography.publisher.text = "Publisher : " + item.biography.publisher
                    binding.biography.alignment.text = "Alignment : " + item.biography.alignment


                    //--------
                    binding.appearance.gender.text  = "Gender : " + item.appearance.gender
                    binding.appearance.race.text  = "Race : " + item.appearance.gender
                    binding.appearance.eyeColor.text  = "Eye Color : " + item.appearance.gender
                    binding.appearance.hairColor.text  = "Hair Color : " + item.appearance.gender

                    //----------
                    binding.work.occupation.text  = "Occupation : " + item.work.occupation
                    binding.work.base.text  = "Base : " + item.work.base
                }
            }
        }
    }
}