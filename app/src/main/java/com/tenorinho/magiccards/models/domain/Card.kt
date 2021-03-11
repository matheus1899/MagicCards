package com.tenorinho.magiccards.models.domain

import android.net.Uri

data class Card(
    val id:String,
    val lang:String,
    val obj:String = "card",
    val oracle_id:String,
    val print_search_uri: Uri,
    val rulings_uri: Uri,
    val scryfall_uri: Uri,
    val uri: Uri,

    val card_faces:Array<CardFace>?,
    val cmc:Float,
    val color_identity:Array<String>,
    val colors:Array<String>?,
    val hand_modifier:String?,
    val keywords:Array<String>,
    val layout:String,
    val mana_cost:String?,
    val name:String,
    val oracle_text:String?,
    val power:String?,
    val produced_mana:Array<String>?,
    val toughness:String?,
    val type_line:String,
    val artist:String?,
    val border_color:String,
    val highres_image:Boolean,
    val image_status:String,
    val image_uris: ImageURIs?,
    val rarity:String,
    val textless:Boolean,
)