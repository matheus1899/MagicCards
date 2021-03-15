package com.tenorinho.magiccards.models.domain

import android.net.Uri

data class Card(
    val id:Int,
    val uuid:String,
    val lang:String,
    val oracle_id:String,
    val print_search_uri: Uri,
    val rulings_uri: Uri,
    val scryfall_uri: Uri,
    val uri: Uri,
    val card_faces:Array<CardFace>?,
    val cmc:Float,
    val color_identity:Array<String>,
    val colors:Array<String>?,
    val keywords:Array<String>,
    val layout:CardLayout,
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
){
    val isDistinctFaces:Boolean
        get() = layout == CardLayout.SPLIT ||
                layout == CardLayout.FLIP ||
                layout == CardLayout.TRANSFORM ||
                layout == CardLayout.DOUBLE_FACED_TOKEN ||
                layout == CardLayout.MODAL_DFC

    val hasCardFaces:Boolean
        get() =  card_faces != null

    val isTwoFaced:Boolean
        get() = layout == CardLayout.MODAL_DFC ||
                layout == CardLayout.DOUBLE_FACED_TOKEN ||
                layout == CardLayout.TRANSFORM
}
enum class CardLayout(val layout:String) {
    NORMAL("normal"),
    SPLIT("split"),
    FLIP("flip"),
    TRANSFORM("transform"),
    MODAL_DFC("modal_dfc"),
    MELD("meld"),
    LEVELER("leveler"),
    SAGA("saga"),
    ADVENTURE("adventure"),
    PLANAR("planar"),
    SCHEME("scheme"),
    VANGUARD("vanguard"),
    TOKEN("token"),
    DOUBLE_FACED_TOKEN("double_faced_token"),
    EMBLEM("emblem"),
    AUGMENT("augment"),
    HOST("host"),
    ART_SERIES("art_series"),
    DOUBLE_SIZED("double_sized");

    companion object{
        fun getCardLayoutByString(s:String):CardLayout{
            when(s){
                NORMAL.layout -> return NORMAL
                SPLIT.layout -> return SPLIT
                FLIP.layout -> return FLIP
                TRANSFORM.layout -> return TRANSFORM
                MODAL_DFC.layout -> return MODAL_DFC
                MELD.layout -> return MELD
                LEVELER.layout -> return LEVELER
                SAGA.layout -> return SAGA
                ADVENTURE.layout -> return ADVENTURE
                PLANAR.layout -> return PLANAR
                SCHEME.layout -> return SCHEME
                VANGUARD.layout -> return VANGUARD
                TOKEN.layout -> return TOKEN
                DOUBLE_FACED_TOKEN.layout -> return DOUBLE_FACED_TOKEN
                EMBLEM.layout -> return EMBLEM
                AUGMENT.layout -> return AUGMENT
                HOST.layout -> return HOST
                ART_SERIES.layout -> return ART_SERIES
                DOUBLE_SIZED.layout -> return DOUBLE_SIZED
                else -> return  NORMAL
            }
        }
    }
}
