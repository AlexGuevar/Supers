package com.killacorp.supers.view.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.killacorp.supers.R
import com.killacorp.supers.databinding.DetailsFragmentLayoutBinding
import com.killacorp.supers.domain.model.HeroModel
import com.killacorp.supers.utils.Extras
import com.killacorp.supers.view.home.vm.HeroesViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val superHeroViewModel: HeroesViewModel by viewModels()
    private lateinit var binding : DetailsFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<TextView>(R.id.tvTitle)?.visibility = View.GONE

        loadData()
        handleError()
        handleLoading()

    }

    private fun loadData() {
        val args : DetailsFragmentArgs? = arguments?.let { DetailsFragmentArgs.fromBundle(it) }
        superHeroViewModel.getHeroDetails(Extras.apiKey, args?.id)
        superHeroViewModel.details.observe(viewLifecycleOwner) { item ->
            getTopBarInfo(item)
            getPowerStats(item)
            getBiography(item)
            getAppearance(item)
            getWork(item)
            getConnections(item)
        }
    }
    private fun getTopBarInfo(item: HeroModel?){
        if (item != null) {
            Glide.with(binding.image.context)
                .load(item.image?.url)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.image)
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvHeroName.text = item?.name
    }

    private fun handleError() {
        superHeroViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                binding.bottomText.text = errorMessage
                binding.image.setPadding(70)
                binding.image.scaleType = ImageView.ScaleType.FIT_CENTER
                binding.scrollView.visibility = View.GONE
                binding.bottomText.visibility = View.VISIBLE
            } else {
                binding.bottomText.visibility = View.GONE
            }
        }
    }

    private fun handleLoading() {
        superHeroViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.bottomProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getWork(item: HeroModel?) {
        binding.work.tvOccupation.text = getString(R.string.occupation) + item?.work?.occupation
        binding.work.tvBase.text = getString(R.string.base) + item?.work?.base
    }

    @SuppressLint("SetTextI18n")
    private fun getAppearance(item: HeroModel?) {
        binding.appearance.tvGender.text = getString(R.string.gender) + item?.appearance?.gender
        binding.appearance.tvRace.text = getString(R.string.race) + item?.appearance?.race
        binding.appearance.tvEyeColor.text = getString(R.string.eyeColor) + item?.appearance?.eyeColor
        binding.appearance.tvHairColor.text = getString(R.string.hairColor) + item?.appearance?.hairColor
    }

    @SuppressLint("SetTextI18n")
    private fun getBiography(item: HeroModel?) {
        binding.biography.tvFullName.text = getString(R.string.fullName) + item?.biography?.fullName
        binding.biography.tvPlaceOfBirth.text = getString(R.string.placeOfBirth) + item?.biography?.placeOfBirth
        binding.biography.tvPublisher.text = getString(R.string.publisher) + item?.biography?.publisher
        binding.biography.tvAlignment.text = getString(R.string.alignment) + item?.biography?.alignment
    }

    @SuppressLint("SetTextI18n")
    private fun getPowerStats(item: HeroModel?) {
        binding.powerStats.tvIntelligence.text = getString(R.string.intelligence) + item?.powerStats?.intelligence
        binding.powerStats.tvStrength.text = getString(R.string.Strength) + item?.powerStats?.strength
        binding.powerStats.tvSpeed.text = getString(R.string.speed) + item?.powerStats?.speed
        binding.powerStats.tvDurability.text = getString(R.string.durability) + item?.powerStats?.durability
    }

    @SuppressLint("SetTextI18n")
    private fun getConnections(item: HeroModel?) {
        binding.connections.tvGroupAffiliation.text = getString(R.string.GroupAffiliation) + item?.connections?.groupAffiliation
        binding.connections.tvRelatives.text = getString(R.string.Relatives) + item?.connections?.relatives
    }
}