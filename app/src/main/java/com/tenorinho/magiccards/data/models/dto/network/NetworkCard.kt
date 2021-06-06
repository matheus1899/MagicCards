package com.tenorinho.magiccards.data.models.dto.network

import android.net.Uri
import com.google.gson.annotations.SerializedName
import com.tenorinho.magiccards.data.models.domain.Card
import com.tenorinho.magiccards.data.models.domain.CardFace
import com.tenorinho.magiccards.data.models.domain.CardLayout

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
    val print_search_uri: String?,
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
    @SerializedName("legalities")
    val legalities:NetworkLegalities?,
    @SerializedName("set")
    val set:String,
    @SerializedName("set_name")
    val setName:String,
    @SerializedName("set_type")
    val setType:String,
    @SerializedName("digital")
    val digital:Boolean,
    @SerializedName("mtgo_id")
    val mtgoId:Int?,
    @SerializedName("arena_id")
    val arenaId:Int?,
    @SerializedName("tcgplayer_id")
    val tcgplayerId:Int?,
    @SerializedName("games")
    val games:Array<String>?
){
    private fun isTwoFaced(layout:String):Boolean{
        return layout == CardLayout.MODAL_DFC.layout ||
                layout == CardLayout.DOUBLE_FACED_TOKEN.layout ||
                layout == CardLayout.TRANSFORM.layout
    }
    private fun isFlip(layout:String):Boolean{
        return layout == CardLayout.FLIP.layout
    }
    fun toCard():Card{
        val cardFaceArrayList = ArrayList<CardFace>(2)
        lateinit var card:Card
        if(isTwoFaced(layout)){
            if(this.card_faces != null){
                cardFaceArrayList.add(card_faces[0].toCardFace(uuid))
                cardFaceArrayList.add(card_faces[1].toCardFace(uuid))
            }
            var print:Uri? = null
            print_search_uri?.let{
                print = Uri.parse(it)
            }
            card = Card(
                null, uuid, lang, oracle_id, print, Uri.parse(rulings_uri),
                Uri.parse(scryfall_uri), Uri.parse(uri), cardFaceArrayList, cmc, color_identity, colors,
                keywords, CardLayout.getCardLayoutByString(layout), mana_cost ?: "", name,
                oracle_text?: "", power ?: "", produced_mana, toughness ?: "",
                type_line, artist ?: "", border_color, highres_image, image_status,
                image_uris?.toImageURIs(uuid), rarity, textless, legalities?.standard,legalities?.future,
                legalities?.historic, legalities?.gladiator, legalities?.pioneer, legalities?.modern,
                legalities?.legacy, legalities?.pauper, legalities?.vintage, legalities?.penny, legalities?.commander,
                legalities?.brawl, legalities?.duel, legalities?.oldschool, legalities?.premodern, set, setName, setType,
                digital, mtgoId ?: 0, arenaId ?: 0, tcgplayerId ?: 0, games
            )
        }
        else if(isFlip(layout)) {
            if(this.card_faces != null){
                cardFaceArrayList.add(card_faces[0].toCardFace(uuid))
                cardFaceArrayList.add(card_faces[1].toCardFace(uuid))
            }
            var print:Uri? = null
            print_search_uri?.let{
                print = Uri.parse(it)
            }
            card = Card(
                null, uuid, lang, oracle_id, print, Uri.parse(rulings_uri),
                Uri.parse(scryfall_uri), Uri.parse(uri), cardFaceArrayList, cmc, color_identity, colors,
                keywords, CardLayout.getCardLayoutByString(layout), mana_cost ?: "", name,
                oracle_text?: "", power ?: "", produced_mana, toughness ?: "",
                type_line, artist ?: "", border_color, highres_image, image_status,
                image_uris?.toImageURIs(uuid), rarity, textless, legalities?.standard,legalities?.future,
                legalities?.historic, legalities?.gladiator, legalities?.pioneer, legalities?.modern,
                legalities?.legacy, legalities?.pauper, legalities?.vintage, legalities?.penny, legalities?.commander,
                legalities?.brawl, legalities?.duel, legalities?.oldschool, legalities?.premodern, set, setName, setType,
                digital, mtgoId ?: 0, arenaId ?: 0, tcgplayerId ?: 0, games
            )
        }
        else{
            var print:Uri? = null
            print_search_uri?.let {
                print = Uri.parse(it)
            }
            card = Card(
                null, uuid, lang, oracle_id, print, Uri.parse(rulings_uri),
                Uri.parse(scryfall_uri), Uri.parse(uri), null, cmc, color_identity, colors,
                keywords, CardLayout.getCardLayoutByString(layout), mana_cost ?: "", name,
                oracle_text?: "", power ?: "", produced_mana, toughness ?: "",
                type_line, artist ?: "", border_color, highres_image, image_status,
                image_uris?.toImageURIs(uuid), rarity, textless, legalities?.standard,legalities?.future,
                legalities?.historic, legalities?.gladiator, legalities?.pioneer, legalities?.modern,
                legalities?.legacy, legalities?.pauper, legalities?.vintage, legalities?.penny, legalities?.commander,
                legalities?.brawl, legalities?.duel, legalities?.oldschool, legalities?.premodern, set, setName, setType,
                digital, mtgoId ?: 0, arenaId ?: 0, tcgplayerId ?: 0, games
            )
        }
        return card
    }
}