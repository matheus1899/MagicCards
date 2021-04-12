package com.tenorinho.magiccards.data.models.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkListCards(
    @SerializedName("object")
    val obj:String,
    @SerializedName("total_cards")
    val total:Int,
    @SerializedName("has_more")
    val has_more:Boolean,
    @SerializedName("next_page")
    val next_page: String,
    @SerializedName("data")
    val list:List<NetworkCard>?
)