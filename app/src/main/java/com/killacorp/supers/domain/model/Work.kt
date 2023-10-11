package com.killacorp.supers.domain.model

import com.google.gson.annotations.SerializedName

data class Work(
    @SerializedName("base")
    val base: String? = null,
    @SerializedName("occupation")
    val occupation: String? = null
)