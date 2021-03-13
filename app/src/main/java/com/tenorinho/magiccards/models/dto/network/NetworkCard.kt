package com.tenorinho.magiccards.models.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkCard(
    @SerializedName("id")
    val uuid:String,
    @SerializedName("lang")
    val lang:String,
    @SerializedName("object")
    val obj:String = "card",
    @SerializedName("oracle_id")
    val oracle_id:String,
    @SerializedName("print_search")
    val print_search_uri: String,
    @SerializedName("rulings_uri")
    val rulings_uri:String,
    @SerializedName("scryfall_uri")
    val scryfall_uri:String,
    @SerializedName("uri")
    val uri:String,
    @SerializedName("card_faces")
    val card_faces:Array<NetworkCardFace>?,
    @SerializedName("cmc")
    val cmc:Float,
    @SerializedName("color_identity")
    val color_identity:Array<String>,
    @SerializedName("colors")
    val colors:Array<String>?,
    @SerializedName("keywords")
    val keywords:Array<String>,
    @SerializedName("layout")
    val layout:String,
    @SerializedName("mana_cost")
    val mana_cost:String?,
    @SerializedName("name")
    val name:String,
    @SerializedName("oracle_text")
    val oracle_text:String?,
    @SerializedName("power")
    val power:String?,
    @SerializedName("produced_mana")
    val produced_mana:Array<String>?,
    @SerializedName("toughness")
    val toughness:String?,
    @SerializedName("type_line")
    val type_line:String,
    @SerializedName("artist")
    val artist:String?,
    @SerializedName("border_color")
    val border_color:String,
    @SerializedName("highres_image")
    val highres_image:Boolean,
    @SerializedName("image_status")
    val image_status:String,
    @SerializedName("image_uris")
    val image_uris: NetworkImageURIs?,
    @SerializedName("rarity")
    val rarity:String,
    @SerializedName("textless")
    val textless:Boolean,
)