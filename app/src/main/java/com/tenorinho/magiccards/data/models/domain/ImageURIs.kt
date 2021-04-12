package com.tenorinho.magiccards.data.models.domain

data class ImageURIs(
    var id:Long? = null,
    val uuid_card:String,
    val small:String = "",
    val normal:String = "",
    val large:String = "",
    val png:String = "",
    val art_crop:String = "",
    val border_crop:String = ""
)