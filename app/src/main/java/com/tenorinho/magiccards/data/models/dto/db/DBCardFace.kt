package com.tenorinho.magiccards.data.models.dto.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.tenorinho.magiccards.data.models.domain.Card
import com.tenorinho.magiccards.data.models.domain.CardFace

@Entity(tableName = "card_face_table",
    foreignKeys = arrayOf(ForeignKey(entity = DBImageURIs::class, parentColumns = ["id_imgURIs"], childColumns = ["id_imgURIs"])))
data class DBCardFace(
    @PrimaryKey(autoGenerate = true)
    var id_CardFace:Long? = null,
    var id_imgURIs:Long? = null,
    val uuid_card:String,

    val artist_front: String = "",
    val artist_back:String = "",

    val color_indicator_front: Array<String>?,
    val color_indicator_back: Array<String>?,

    val colors_front: Array<String>?,
    val colors_back: Array<String>?,

    val mana_cost_front: String = "",
    val mana_cost_back: String = "",

    val name_front: String = "",
    val name_back: String = "",

    val oracle_text_front: String = "",
    val oracle_text_back: String = "",

    val power_front: String = "",
    val power_back: String = "",

    val toughness_front: String = "",
    val toughness_back: String = "",

    val type_line_front: String = "",
    val type_line_back: String = "",


    val printed_name_front:String = "",
    val printed_name_back:String = "",

    val printed_text_front:String = "",
    val printed_text_back:String = "",

    val printed_type_line_front:String = "",
    val printed_type_line_back:String = "",
){
    companion object{
        fun toDomain(dbcardFace:DBCardFace, dbImageURIs: DBImageURIs?) : Array<CardFace>{
            var cf1 : CardFace
            var cf2 : CardFace

            val arrayImg = DBImageURIs.toDomain(dbImageURIs, dbcardFace.uuid_card)
            with(dbcardFace){
                cf1 = CardFace(id_CardFace, uuid_card, artist_front, color_indicator_front, colors_front,arrayImg?.get(0), mana_cost_front, name_front,
                oracle_text_front, power_front, toughness_front, type_line_front, printed_name_front, printed_text_front, printed_type_line_front)
                cf2 = CardFace(id_CardFace, uuid_card, artist_back, color_indicator_back, colors_back,arrayImg?.get(1), mana_cost_back, name_back,
                    oracle_text_back, power_back, toughness_back, type_line_back, printed_name_back, printed_text_back, printed_type_line_back)
            }
            return arrayOf(cf1, cf2)
        }
        fun fromDomain(front: CardFace?, back:CardFace?) : DBCardFace{
            val imgURIFront = front?.image_uris
            val imgURIBack = back?.image_uris
            var cardFaceID:Long? = null
            var imgURI_ID:Long? = null
            if(front?.id != null){
                cardFaceID = front.id
            }
            if(back?.id != null && cardFaceID == null){
                cardFaceID = back.id
            }
            if(imgURIFront?.id != null){
                imgURI_ID = imgURIFront.id
            }
            if(imgURIBack?.id != null && imgURI_ID == null){
                imgURI_ID = imgURIBack.id
            }
            return DBCardFace(cardFaceID, imgURI_ID, front?.uuid_card ?: "", front?.artist ?: "", back?.artist ?: "",
                front?.color_indicator, back?.color_indicator, front?.colors, back?.colors, front?.mana_cost ?: "", back?.mana_cost ?: "",
                front?.name ?: "", back?.name ?: "", front?.oracle_text ?: "", back?.oracle_text ?: "",
                front?.power ?: "", back?.power ?: "", front?.toughness ?: "", back?.toughness?: "",
                front?.type_line ?: "", back?.type_line ?: "", front?.printed_name ?: "", back?.printed_name ?: "",
                front?.printed_text ?: "", back?.printed_text ?: "", front?.printed_type_line ?: "", back?.printed_type_line ?: ""
            )
        }
    }
}

