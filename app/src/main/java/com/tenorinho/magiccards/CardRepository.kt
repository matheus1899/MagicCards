package com.tenorinho.magiccards

import android.net.Uri
import com.tenorinho.magiccards.db.CardDAO
import com.tenorinho.magiccards.db.CardFaceDAO
import com.tenorinho.magiccards.db.ImageURIsDAO
import com.tenorinho.magiccards.models.domain.Card
import com.tenorinho.magiccards.models.domain.CardFace
import com.tenorinho.magiccards.models.domain.CardLayout
import com.tenorinho.magiccards.models.domain.ImageURIs
import com.tenorinho.magiccards.models.dto.db.DBCardFace
import com.tenorinho.magiccards.models.dto.db.DBImageURIs
import com.tenorinho.magiccards.models.dto.network.NetworkCard
import com.tenorinho.magiccards.net.IScryfallService
import com.tenorinho.magiccards.net.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardRepository(val cardDAO: CardDAO,
                     val cardFaceDAO: CardFaceDAO,
                     val imageURIsDAO: ImageURIsDAO){

    val service = RetrofitConfig.getRetrofitScryfallService().create(IScryfallService::class.java)

    suspend fun load(uuid: String): Card? {
        var dbImgURIs : DBImageURIs? = null
        var dbCardFace : DBCardFace? = null

        val dbCard = cardDAO.getCardByUUID(uuid)
        if(dbCard != null){
            if(isTwoFaced(dbCard.layout)){
                dbCardFace = cardFaceDAO.getCardFaceByID(dbCard.id_card_faces)
                dbCardFace?.let {
                    dbImgURIs = imageURIsDAO.getImageURIsByID(it.id_img_uris)
                }
                if(dbCardFace == null || dbImgURIs == null){
                    return null
                }
                val imgURIsFront = ImageURIs(
                    dbImgURIs!!.id,
                    dbImgURIs!!.uuid_card,
                    dbImgURIs!!.small_front,
                    dbImgURIs!!.normal_front,
                    dbImgURIs!!.large_front,
                    dbImgURIs!!.png_front,
                    dbImgURIs!!.art_crop_front,
                    dbImgURIs!!.border_crop_front
                )
                val imgURIsBack = ImageURIs(
                    dbImgURIs!!.id,
                    dbImgURIs!!.uuid_card,
                    dbImgURIs!!.small_back,
                    dbImgURIs!!.normal_back,
                    dbImgURIs!!.large_back,
                    dbImgURIs!!.png_back,
                    dbImgURIs!!.art_crop_back,
                    dbImgURIs!!.border_crop_back
                )
                val cardFaceFront = CardFace(
                    dbCardFace.id,
                    dbCardFace.uuid_card,
                    dbCardFace.artist_front,
                    splitToArray(dbCardFace.color_indicator_front),
                    splitToArray(dbCardFace.colors_front),
                    imgURIsFront,
                    dbCardFace.mana_cost_front,
                    dbCardFace.name_front,
                    dbCardFace.oracle_text_front,
                    dbCardFace.power_front,
                    dbCardFace.toughness_front,
                    dbCardFace.type_line_front
                )
                val cardFaceBack = CardFace(
                    dbCardFace.id,
                    dbCardFace.uuid_card,
                    dbCardFace.artist_back,
                    splitToArray(dbCardFace.color_indicator_back),
                    splitToArray(dbCardFace.colors_back),
                    imgURIsFront,
                    dbCardFace.mana_cost_back,
                    dbCardFace.name_back,
                    dbCardFace.oracle_text_back,
                    dbCardFace.power_back,
                    dbCardFace.toughness_back,
                    dbCardFace.type_line_back
                )
                val cardFaceArray:Array<CardFace> = arrayOf(cardFaceFront, cardFaceBack)
                with(dbCard){
                    return Card(
                        id, uuid, lang, oracle_id, Uri.parse(print_search_uri), Uri.parse(rulings_uri),
                        Uri.parse(scryfall_uri), Uri.parse(uri), cardFaceArray, cmc, splitToArray(color_identity),
                        splitToArray(colors), splitToArray(keywords), CardLayout.getCardLayoutByString(layout), mana_cost, name, oracle_text,
                        power, splitToArray(produced_mana), toughness, type_line, artist, border_color, highres_image, image_status,
                        null, rarity, textless
                    )
                }
            }
            else{
                val dbImageURIs = imageURIsDAO.getImageURIsByID(dbCard.id_img_uri) ?: return null


                val imgURIsFront = ImageURIs(
                        dbImageURIs.id, dbImageURIs.uuid_card, dbImageURIs.small_front, dbImageURIs.normal_front,
                        dbImageURIs.large_front, dbImageURIs.png_front, dbImageURIs.art_crop_front, dbImageURIs.border_crop_front
                    )
                with(dbCard){
                    return Card(
                        id, uuid, lang, oracle_id, Uri.parse(print_search_uri), Uri.parse(rulings_uri),
                        Uri.parse(scryfall_uri), Uri.parse(uri), null, cmc, splitToArray(color_identity),
                        splitToArray(colors), splitToArray(keywords), CardLayout.getCardLayoutByString(layout), mana_cost, name, oracle_text,
                        power, splitToArray(produced_mana), toughness, type_line, artist, border_color, highres_image, image_status,
                        imgURIsFront, rarity, textless
                    )
                }
            }
        }
        else{
            val call = service.getCardsByUUId(uuid)
            var card:Card? = null

            call.enqueue(object : Callback<NetworkCard> {
                override fun onResponse(call: Call<NetworkCard>, response: Response<NetworkCard>) {
                    if(response.code() == 200){
                        val networkCard = response.body()
                        if(networkCard != null){
                            if(isTwoFaced(networkCard.layout)){
                                val cardFaceArrayList = ArrayList<CardFace>(2)
                                if(networkCard.card_faces != null ){
                                    cardFaceArrayList.add(networkCard.card_faces[0].toCardFace())
                                    cardFaceArrayList.add(networkCard.card_faces[1].toCardFace())
                                }

                                val cardFaceArray = Array(2){
                                    cardFaceArrayList[it]
                                }
                                with(networkCard){
                                    card = Card(
                                        -1, uuid, lang, oracle_id, Uri.parse(print_search_uri), Uri.parse(rulings_uri),
                                        Uri.parse(scryfall_uri), Uri.parse(uri), cardFaceArray, cmc, color_identity, colors,
                                        keywords, CardLayout.getCardLayoutByString(layout), mana_cost ?: "", name,
                                        oracle_text?: "", power ?: "", produced_mana, toughness ?: "",
                                        type_line, artist ?: "", border_color, highres_image, image_status,
                                        null, rarity, textless
                                    )
                                }
                            }
                            else{
                                with(networkCard){
                                    card = Card(
                                        -1, uuid, lang, oracle_id, Uri.parse(print_search_uri), Uri.parse(rulings_uri),
                                        Uri.parse(scryfall_uri), Uri.parse(uri), null, cmc, color_identity, colors,
                                        keywords, CardLayout.getCardLayoutByString(layout), mana_cost ?: "", name,
                                        oracle_text?: "", power ?: "", produced_mana, toughness ?: "",
                                        type_line, artist ?: "", border_color, highres_image, image_status,
                                        null, rarity, textless
                                    )
                                }
                            }
                        }
                        else{
                            onFailure(call, Throwable("Network == NULL"))
                        }
                    }
                    else{
                        onFailure(call, Throwable(response.message()))
                    }
                }
                override fun onFailure(call: Call<NetworkCard>, t: Throwable) {

                }

            })
            return card
        }
    }
    private fun isTwoFaced(layout:String):Boolean{
        return layout == CardLayout.MODAL_DFC.layout ||
                layout == CardLayout.DOUBLE_FACED_TOKEN.layout ||
                layout == CardLayout.TRANSFORM.layout
    }

    private fun splitToArray(s:String):Array<String>{
        val list = s.split(":")
        return Array(list.size){
            list[it]
        }
    }
}