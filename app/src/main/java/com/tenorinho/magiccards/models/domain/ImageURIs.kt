package com.tenorinho.magiccards.models.domain

data class ImageURIs(
    val id:Int,
    val uuid_card:String,
    val small:String = "",
    val normal:String = "",
    val large:String = "",
    val png:String = "",
    val art_crop:String = "",
    val border_crop:String = ""
)