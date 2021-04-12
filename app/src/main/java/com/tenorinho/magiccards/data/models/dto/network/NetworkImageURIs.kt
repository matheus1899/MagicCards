package com.tenorinho.magiccards.data.models.dto.network

import com.google.gson.annotations.SerializedName
import com.tenorinho.magiccards.data.models.domain.ImageURIs

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
){
    fun toImageURIs():ImageURIs{
        return ImageURIs(null,
            "",
            small ?: "",
            normal ?: "",
            large ?: "",
            png ?: "",
            art_crop ?: "",
            border_crop ?: "")
    }
    fun toImageURIs(uuid:String?):ImageURIs{
        return ImageURIs(null,
            uuid?: "",
            small ?: "",
            normal ?: "",
            large ?: "",
            png ?: "",
            art_crop ?: "",
            border_crop ?: "")
    }
}
