package com.killacorp.supers.data.repository

import com.killacorp.supers.data.api.AuthService
import com.killacorp.supers.domain.model.HeroModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HeroesRepository @Inject constructor(
    private var authService: AuthService) {

    suspend fun getSuperHero(accessToken : String, id : String?) : Flow<HeroModel>{
        return flow {
            emit(authService.getHero(accessToken,id))
        }
    }
}