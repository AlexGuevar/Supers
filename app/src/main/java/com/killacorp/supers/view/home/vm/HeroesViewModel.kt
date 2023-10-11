package com.killacorp.supers.view.home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.killacorp.supers.data.repository.HeroesRepository
import com.killacorp.supers.domain.model.HeroModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor(
    private val heroesRepository: HeroesRepository
) : ViewModel(){


    private var tempList = ArrayList<HeroModel>()
    private var heroesList : ArrayList<HeroModel> = ArrayList<HeroModel>()

    private var savedIndex = 1
    private var currentIndex = 1

    val isLoading : MutableLiveData<Boolean> = MutableLiveData(false)
    private val _isLoading  get() = isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    var details : MutableLiveData<HeroModel?> = MutableLiveData()
    private  val _details get() = details

    val superHero : MutableLiveData<ArrayList<HeroModel>> = MutableLiveData()
    private val _heroes get() = superHero


    fun getHeroDetails(accessToken : String, id : String?) = viewModelScope.launch{
        try {
            _isLoading.value = true
            heroesRepository.getSuperHero(accessToken, id!!).collectLatest {
                _details.value = it
            }
            _isLoading.value = false
        }
        catch (e : Exception){
            _error.value = e.message
        }
        finally {
            _isLoading.value = false
        }
    }


    fun loadData(accessToken: String, initialSize: Int) = viewModelScope.launch {
        _isLoading.value = true
        heroesList.clear()
        try {
            for (i in currentIndex..initialSize) {
                heroesRepository.getSuperHero(accessToken, i.toString()).collectLatest {
                    heroesList.add(it)
                    savedIndex = i
                }
            }
            tempList.addAll(heroesList)
            _heroes.value = tempList
            currentIndex = savedIndex + 1
        }
        catch (e: Exception) {
            _error.value = "An error occurred: ${e.message}"
        }
        finally {
            _isLoading.value = false
        }
    }

}