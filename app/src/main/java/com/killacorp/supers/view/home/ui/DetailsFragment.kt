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
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
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

        activity!!.findViewById<TextView>(R.id.tvTitle).visibility = View.GONE
        val detailsFragmentArgs : DetailsFragmentArgs? = arguments?.let { DetailsFragmentArgs.fromBundle(it) }
        lifecycleScope.launch {
            if (detailsFragmentArgs != null) {
                viewModel.getHeroDetails(Extras.apiKey,detailsFragmentArgs.id)

                ///-------- General Info
                viewModel.details.observe(viewLifecycleOwner){ item ->
                    Glide.with(binding.image.context)
                        .load(item.image?.url)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(binding.image)
                    binding.btnBack.setOnClickListener {
                        findNavController().popBackStack()
                    }
                    binding.tvHeroName.text = item.name

                    ///--------powerStats
                    binding.powerStats.tvIntelligence.text = "Intelligence : "  + item.powerstats.intelligence
                    binding.powerStats.tvStrength.text = "Strength : " + item.powerstats.strength
                    binding.powerStats.tvSpeed.text = "Speed : " + item.powerstats.speed
                    binding.powerStats.tvDurability.text = "Durability : " + item.powerstats.durability


                    ///--------biography
                    binding.biography.tvFullName.text = "Full Name : " + item.biography.fullName
                    binding.biography.tvPlaceOfBirth.text = "Place Of Birth : " + item.biography.placeOfBirth
                    binding.biography.tvPublisher.text = "Publisher : " + item.biography.publisher
                    binding.biography.tvAlignment.text = "Alignment : " + item.biography.alignment


                    //--------appearance
                    binding.appearance.tvGender.text  = "Gender : " + item.appearance.gender
                    binding.appearance.tvRace.text  = "Race : " + item.appearance.race
                    binding.appearance.tvEyeColor.text  = "Eye Color : " + item.appearance.eyeColor
                    binding.appearance.tvHairColor.text  = "Hair Color : " + item.appearance.hairColor

                    //----------work
                    binding.work.tvOccupation.text  = "Occupation : " + item.work.occupation
                    binding.work.tvBase.text  = "Base : " + item.work.base

                    //----------connections
                    binding.connections.tvGroupAffiliation.text  = "Occupation : " + item.connections.groupAffiliation
                    binding.connections.tvRelatives.text  = "Base : " + item.connections.relatives

                }
            }
        }
    }
}