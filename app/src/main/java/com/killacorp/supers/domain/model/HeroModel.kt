package com.killacorp.supers.domain.model

data class HeroModel(
    val appearance: Appearance,
    val biography: Biography,
    val connections: Connections,
    val id: String,
    val image: Image? = null ,
    val name: String,
    val powerstats: Powerstats,
    val response: String,
    val work: Work
)