package com.tenorinho.magiccards.data.models.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkLegalities(
    @SerializedName("standard")
    val standard:String,
    @SerializedName("future")
    val future:String,
    @SerializedName("historic")
    val historic:String,
    @SerializedName("gladiator")
    val gladiator:String,
    @SerializedName("pioneer")
    val pioneer:String,
    @SerializedName("modern")
    val modern:String,
    @SerializedName("legacy")
    val legacy:String,
    @SerializedName("pauper")
    val pauper:String,
    @SerializedName("vintage")
    val vintage:String,
    @SerializedName("penny")
    val penny:String,
    @SerializedName("commander")
    val commander:String,
    @SerializedName("brawl")
    val brawl:String,
    @SerializedName("duel")
    val duel:String,
    @SerializedName("oldschool")
    val oldschool:String,
    @SerializedName("premodern")
    val premodern:String
)