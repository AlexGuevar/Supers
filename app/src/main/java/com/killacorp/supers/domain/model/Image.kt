package com.killacorp.supers.domain.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("url")
    val url: String? = null
)