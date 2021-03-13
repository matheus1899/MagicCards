package com.tenorinho.magiccards.models.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkImageURIs(
    @SerializedName("small")
    val small:String?,
    @SerializedName("normal")
    val normal:String?,
    @SerializedName("large")
    val large:String?,
    @SerializedName("png")
    val png:String?,
    @SerializedName("art_crop")
    val art_crop:String?,
    @SerializedName("border_crop")
    val border_crop:String?
)
