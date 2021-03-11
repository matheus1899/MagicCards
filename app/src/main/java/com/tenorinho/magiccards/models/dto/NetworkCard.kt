package com.tenorinho.magiccards.models.dto

import android.net.Uri

data class Card(
    //Core Card Fields
    //val arena_id:Int?,
    val id:String,              //uuid
    val lang:String,
    //val mtgo_id:Int?,
    //val mtgo_foil_id:Int?,
    //val multiverse_ids:Array<Any>?,
    //val tcgplayer_id:Int?,
    //val cardmarket_id:Int?,
    val obj:String = "card",
    val oracle_id:String,
    val print_search_uri: Uri,
    val rulings_uri:Uri,
    val scryfall_uri:Uri,
    val uri:Uri,

    //Gameplays Fields
    //val all_parts:Array<Any>?,
    val card_faces:Array<NetworkCardFace>?,
    val cmc:Float,
    val color_identity:Array<String>,     //Colors
    //val color_indicator:Any?,   //Colors
    val colors:Array<String>?,
    //val edhrec_rank:Int?,
    //val foil:Boolean,
    //val hand_modifier:String?,
    val keywords:Array<String>, //Flying, Cumulative upkeep
    val layout:String,
    //val legatities:Any,
    //val life_modifier:String?,
    //val loyalty:String?,
    val mana_cost:String?,
    val name:String,
    //val non_foil:Boolean,
    val oracle_text:String?,
    //val oversized:Boolean,
    val power:String?,
    val produced_mana:Any?,     //Colors
    //val reserved:Boolean,
    val toughness:String?,
    val type_line:String,

    //Print Fields
    val artist:String?,
    //val booster:Boolean,
    val border_color:String,
    //val card_back_id:String,    // uuid
    //val collector_number:String,
    //val content_warning:Boolean?,
    //val digital:Boolean,
    //val flavor_name:String?,
    //val flavor_text:String?,
    //val frame_effects:Array<Any>?,
    //val frame:String,
    //val full_art:Boolean,
    //val games:Array<String>,
    val highres_image:Boolean,
    //val illustration_id:String?,//uuid
    val image_status:String,
    val image_uris:NetworkImageURIs?,        //ImageUris
    //val printed_name:String?,
    //val printed_type_line:String?,
    //val promo:Boolean,
    //val promo_types:Array<Any>?,
    //val purchase_uris:Any,
    val rarity:String,
    //val related_uris:Any,
    //val release_at:Any,         //date
    //val reprint:Boolean,
    //val scryfall_set_uri:Uri,
    //val set_name:String,
    //val set_search:String,
    //val set_type:String,
    //val set_uri:Uri,
    //val set:String,
    //val story_spotlight:Boolean,
    val textless:Boolean,
    //val variation:Boolean,
    //val variation_of:String?,   //uuid
    //val watermark:String?,
    //val previewed_at:Any?,      //date
    //val previewed_source_uri:Uri?,
    //val preview_source:String?,
    //val artist_ids:Array<String>?
)