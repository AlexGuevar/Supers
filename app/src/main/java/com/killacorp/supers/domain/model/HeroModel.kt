package com.killacorp.supers.domain.model

import com.google.gson.annotations.SerializedName

data class HeroModel(
    @SerializedName("appearance")
    val appearance: Appearance? = null,
    @SerializedName("biography")
    val biography: Biography? = null,
    @SerializedName("connections")
    val connections: Connections? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("image")
    val image: Image? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("powerstats")
    val powerStats: PowerStats? = null,
    @SerializedName("response")
    val response: String? = null,
    @SerializedName("work")
    val work: Work? = null
)