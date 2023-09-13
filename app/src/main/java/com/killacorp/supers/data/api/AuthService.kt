package com.killacorp.supers.data.api

import com.killacorp.supers.domain.model.HeroModel
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthService {

    @GET("{access-token}/{id}")
    suspend fun getHero(
        @Path("access-token") accessToken : String,
        @Path("id") id : String
    ) : HeroModel
}