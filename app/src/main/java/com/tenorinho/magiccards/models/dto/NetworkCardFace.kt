package com.tenorinho.magiccards.models.dto

data class NetworkCardFace(
    val artist: String?,
    val color_indicator: Array<String>?,
    val colors: Array<String>?,
    val image_uris: NetworkImageURIs?,
    val mana_cost: String,
    val name: String,
    val obj: String = "card_face",
    val oracle_text: String?,
    val power: String?,
    val toughness: String?,
    val type_line: String
)
