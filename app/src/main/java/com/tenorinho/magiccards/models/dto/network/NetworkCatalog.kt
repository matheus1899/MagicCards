package com.tenorinho.magiccards.models.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkCatalog(
    @SerializedName("object")
    val obj:String,
    @SerializedName("total_values")
    val total:Int,
    @SerializedName("data")
    val data:Array<String>?
)
