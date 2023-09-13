package com.killacorp.supers.view.home.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.killacorp.supers.data.repository.HereosRepository
import com.killacorp.supers.domain.model.HeroModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HereosViewModel @Inject constructor(
    private val hereosRepository: HereosRepository
) : ViewModel(){


    private var savedIndex = 1
    private var currentIndex = 1

    // SHOW / HIDE PROGRESS BAR
    val isLoading : MutableLiveData<Boolean> = MutableLiveData(false)
    private val _isLoading  get() = isLoading


    // TODO : LOAD ONLY 1 ITEM FOR DETAILS
    var details : MutableLiveData<HeroModel> = MutableLiveData()
    private  val _details get() = details

    // POPULATE UI WITH NEW DATA
    private var tempList = ArrayList<HeroModel>()
    private var heroesList : ArrayList<HeroModel> = ArrayList<HeroModel>()
    var heroes : MutableLiveData<ArrayList<HeroModel>> = MutableLiveData()
    private val _heroes get() = heroes


    // GET SINGLE DATA
    suspend fun getHeroDetails(accessToken : String, id : String?){
        hereosRepository.getHero(accessToken, id!!).collectLatest {
            _details.value = it
        }
    }

    // LOAD DATA IN CHUNKS
    suspend fun loadData(accessToken : String,initialSize : Int){
        _isLoading.value = true
        heroesList.clear()
        for(i in currentIndex..initialSize){
            hereosRepository.getHero(accessToken, i.toString()).collectLatest {
                heroesList.add(it)
                savedIndex = i
            }
        }
        tempList.addAll(heroesList)
        _heroes.value = tempList
        currentIndex = (savedIndex + 1)
        _isLoading.value = false
    }

}