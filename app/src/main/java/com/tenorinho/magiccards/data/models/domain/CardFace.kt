package com.tenorinho.magiccards.data.models.domain

data class CardFace(
    var id:Long? = null,
    val uuid_card:String,
    val artist: String?,
    val color_indicator: Array<String>?,
    val colors: Array<String>?,
    val image_uris: ImageURIs?,
    val mana_cost: String,
    val name: String,
    val oracle_text: String?,
    val power: String?,
    val toughness: String?,
    val type_line: String,
)