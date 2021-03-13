package com.tenorinho.magiccards.models.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkCardFace(
    @SerializedName("artist")
    val artist: String?,
    @SerializedName("color_indicator")
    val color_indicator: Array<String>?,
    @SerializedName("colors")
    val colors: Array<String>?,
    @SerializedName("image_uris")
    val image_uris: NetworkImageURIs?,
    @SerializedName("mana_cost")
    val mana_cost: String,
    @SerializedName("illustration_id")
    val illustration_id:String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("object")
    val obj: String = "card_face",
    @SerializedName("oracle_text")
    val oracle_text: String?,
    @SerializedName("power")
    val power: String?,
    @SerializedName("toughness")
    val toughness: String?,
    @SerializedName("type_line")
    val type_line: String
)
