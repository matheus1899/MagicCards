package com.tenorinho.magiccards.data.repository

import com.tenorinho.magiccards.data.db.CardDAO
import com.tenorinho.magiccards.data.db.CardFaceDAO
import com.tenorinho.magiccards.data.db.ImageURIsDAO
import com.tenorinho.magiccards.data.models.domain.Card
import com.tenorinho.magiccards.data.models.domain.CardLayout
import com.tenorinho.magiccards.data.models.dto.db.DBCard
import com.tenorinho.magiccards.data.models.dto.db.DBCardFace
import com.tenorinho.magiccards.data.models.dto.db.DBImageURIs
import com.tenorinho.magiccards.data.models.dto.network.NetworkCard
import com.tenorinho.magiccards.data.models.dto.network.NetworkListCards
import com.tenorinho.magiccards.net.IScryfallService
import com.tenorinho.magiccards.net.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardRepository(private val cardDAO: CardDAO,
                     private val cardFaceDAO: CardFaceDAO,
                     private val imageURIsDAO: ImageURIsDAO) {
    private val service = RetrofitConfig.getRetrofitScryfallService().create(IScryfallService::class.java)

    fun search(searchText: String, success: (ArrayList<Card>?) -> Unit, failure: (Throwable) -> Unit) {
        val listCards = ArrayList<Card>()
        val callback = service.getCardsByText(searchText)
        callback.enqueue(object : Callback<NetworkListCards> {
            override fun onResponse(
                call: Call<NetworkListCards>,
                response: Response<NetworkListCards>
            ) {
                try {
                    if (response.code() == 200) {
                        val body = response.body()
                        if (body?.list != null) {
                            for (i in body.list) {
                                listCards.add(i.toCard())
                            }
                            success(listCards)
                        }
                        else {
                            failure(Throwable("Body is null"))
                        }
                    }
                    else if (response.code() == 404) {
                        failure(Throwable("0 cards found with \"${searchText}\""))
                    }
                }
                catch (e: Exception) {
                    failure(Throwable(e.message.toString() + " | " + e.cause))
                }
            }

            override fun onFailure(call: Call<NetworkListCards>, t: Throwable) {
                failure(t)
            }
        })
    }
    suspend fun getAllCardsFromDatabase(success:(ArrayList<Card>) -> Unit, failure: (Throwable) -> Unit){
        val arrayListCard = ArrayList<Card>()
        val listCards = cardDAO.getAllCards()
        if(listCards != null){
            if(listCards.isEmpty()){
                failure(Throwable("Lista está vazia"))
            }
            else{
                for(dbcard in listCards){
                    var card:Card
                    if(dbcard.layout == CardLayout.DOUBLE_FACED_TOKEN.layout ||
                        dbcard.layout == CardLayout.TRANSFORM.layout ||
                        dbcard.layout == CardLayout.MODAL_DFC.layout){

                        var dbcardFace:DBCardFace? = null
                        var dbImgURIs:DBImageURIs? = null

                        if(dbcard.idCardFace != null){
                            dbcardFace = cardFaceDAO.getCardFaceByID(dbcard.idCardFace)
                        }
                        if(dbcardFace?.id_imgURIs != null){
                            dbImgURIs = imageURIsDAO.getImageURIsByID(dbcardFace.id_imgURIs!!)
                        }
                        card = DBCard.toDomain(dbcard, dbcardFace, dbImgURIs)
                    }
                    else if(dbcard.layout == CardLayout.FLIP.layout){
                        var dbcardFace:DBCardFace? = null
                        if(dbcard.idCardFace != null){
                            dbcardFace = cardFaceDAO.getCardFaceByID(dbcard.idCardFace)
                        }
                        card = DBCard.toDomain(dbcard, dbcardFace, null)
                    }
                    else{
                        card = DBCard.toDomain(dbcard, null, null)
                    }
                    arrayListCard.add(card)
                }
                success(arrayListCard)
            }
        }
        else{
            failure(Throwable("Lista está vazia"))
        }
    }
    suspend fun loadRandomCard(success:(Card?)->Unit, failure:(Throwable)->Unit) {
        val callback = service.getRandomCard()
        callback.enqueue(object : Callback<NetworkCard> {
            override fun onResponse(call: Call<NetworkCard>, response: Response<NetworkCard>) {
                try {
                    if (response.code() == 200) {
                        val body = response.body()
                        if (body != null) {
                            success(body.toCard())
                        } else {
                            failure(Throwable("retorno nulo"))
                        }
                    } else {
                        failure(Throwable("Response Code: @{response.code()}"))
                    }
                } catch (e: Exception) {
                    failure(Throwable(e.message.toString() + " | " + e.cause))
                }
            }
            override fun onFailure(call: Call<NetworkCard>, t: Throwable) {
                failure(t)
            }
        })
    }
    suspend fun saveCard(card:Card?, success:(Long)->Unit, failure:(Throwable)->Unit) {
        if (card != null) {
            val idCard = card.id
            try {
                if(idCard == null) {
                    if (card.layout == CardLayout.MODAL_DFC ||
                        card.layout == CardLayout.TRANSFORM ||
                        card.layout == CardLayout.DOUBLE_FACED_TOKEN
                    ) {
                        val dbImg = DBImageURIs.fromDomain(
                            card.card_faces?.get(0)?.image_uris,
                            card.card_faces?.get(1)?.image_uris
                        )
                        val dbImgID = imageURIsDAO.addImageURIs(dbImg)
                        card.card_faces?.get(0)?.image_uris?.id = dbImgID
                        card.card_faces?.get(1)?.image_uris?.id = dbImgID
                        val dbCardFace =
                            DBCardFace.fromDomain(card.card_faces?.get(0), card.card_faces?.get(1))
                        val idCardFace = cardFaceDAO.addCardFace(dbCardFace)
                        card.card_faces?.get(0)?.id = idCardFace
                        card.card_faces?.get(1)?.id = idCardFace
                        val id = cardDAO.addCard(DBCard.fromDomain(card))
                        success(id)
                    }
                    else if (card.layout == CardLayout.FLIP) {
                        val dbCardFace =
                            DBCardFace.fromDomain(card.card_faces?.get(0), card.card_faces?.get(1))
                        val idCardFace = cardFaceDAO.addCardFace(dbCardFace)
                        card.card_faces?.get(0)?.id = idCardFace
                        card.card_faces?.get(1)?.id = idCardFace
                        val id = cardDAO.addCard(DBCard.fromDomain(card))
                        success(id)
                    } else {
                        val id = cardDAO.addCard(DBCard.fromDomain(card))
                        success(id)
                    }
                }
                else if(!cardDAO.cardExistsByID(idCard)){
                    if (card.layout == CardLayout.MODAL_DFC ||
                        card.layout == CardLayout.TRANSFORM ||
                        card.layout == CardLayout.DOUBLE_FACED_TOKEN
                    ) {
                        val dbImg = DBImageURIs.fromDomain(
                            card.card_faces?.get(0)?.image_uris,
                            card.card_faces?.get(1)?.image_uris
                        )
                        val dbImgID = imageURIsDAO.addImageURIs(dbImg)
                        card.card_faces?.get(0)?.image_uris?.id = dbImgID
                        card.card_faces?.get(1)?.image_uris?.id = dbImgID
                        val dbCardFace =
                            DBCardFace.fromDomain(card.card_faces?.get(0), card.card_faces?.get(1))
                        val idCardFace = cardFaceDAO.addCardFace(dbCardFace)
                        card.card_faces?.get(0)?.id = idCardFace
                        card.card_faces?.get(1)?.id = idCardFace
                        val id = cardDAO.addCard(DBCard.fromDomain(card))
                        success(id)
                    }
                    else if (card.layout == CardLayout.FLIP) {
                        val dbCardFace =
                            DBCardFace.fromDomain(card.card_faces?.get(0), card.card_faces?.get(1))
                        val idCardFace = cardFaceDAO.addCardFace(dbCardFace)
                        card.card_faces?.get(0)?.id = idCardFace
                        card.card_faces?.get(1)?.id = idCardFace
                        val id = cardDAO.addCard(DBCard.fromDomain(card))
                        success(id)
                    } else {
                        val id = cardDAO.addCard(DBCard.fromDomain(card))
                        success(id)
                    }
                }
                else{
                    failure(Throwable("Card já está salvo"))
                }
            }
            catch (ex: Exception) {
                failure(Throwable(ex.message))
            }
        }
        else {
            failure(Throwable("Nenhum card para ser salvo"))
        }
    }
    suspend fun getIdByCardName(cardName:String, success:(Long?)->Unit){
        success(cardDAO.getIdByCardName(cardName))
    }
    /*suspend fun load(uuid: String): Card? {
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
                val cardFaceArray:ArrayList<CardFace> = ArrayList<CardFace>()
                cardFaceArray.add(cardFaceFront)
                cardFaceArray.add(cardFaceBack)
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
                                with(networkCard){
                                    card = Card(
                                        -1, uuid, lang, oracle_id, Uri.parse(print_search_uri), Uri.parse(rulings_uri),
                                        Uri.parse(scryfall_uri), Uri.parse(uri), cardFaceArrayList, cmc, color_identity, colors,
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
    }*/
    private fun isTwoFaced(layout:String):Boolean{
        return layout == CardLayout.MODAL_DFC.layout ||
                layout == CardLayout.DOUBLE_FACED_TOKEN.layout ||
                layout == CardLayout.TRANSFORM.layout
    }
    private suspend fun  saveCardInDatabase(card:Card){

    }
}