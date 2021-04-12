package com.tenorinho.magiccards.data.models.dto.db

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.tenorinho.magiccards.data.models.domain.Card
import com.tenorinho.magiccards.data.models.domain.CardFace
import com.tenorinho.magiccards.data.models.domain.CardLayout
import com.tenorinho.magiccards.data.models.domain.ImageURIs

@Entity(tableName="card_table",
    foreignKeys = arrayOf(ForeignKey(entity=DBCardFace::class, parentColumns = ["id_CardFace"], childColumns = ["idCardFace"])))
data class DBCard(
    @PrimaryKey(autoGenerate = true)
    val idCard:Long? = null,
    val idCardFace:Long? = null,
    val uuid:String,
    val lang:String,
    val oracle_id:String,
    val print_search_uri: String,
    val rulings_uri:String,
    val scryfall_uri:String,
    val uri:String,
    val cmc:Float,
    val color_identity:Array<String>?,              //criar uma unica string com todas as cores e usar split
    val colors:Array<String>?,                      //criar uma unica string com todas as cores e usar split
    val keywords:Array<String>?,                    //criar uma unica string com todas as cores e usar split
    val layout:String,
    val mana_cost:String,
    @ColumnInfo(name = "card_name")
    val name:String,
    val oracle_text:String = "",
    val power:String = "",
    @ColumnInfo(name = "prod_mana")
    val produced_mana:Array<String>?,               //criar uma unica string com todas as cores e usar split
    val toughness:String = "",
    val type_line:String,
    val artist:String,
    val border_color:String,
    @ColumnInfo(name = "high_img")
    val highres_image:Boolean,
    @ColumnInfo(name = "img_status")
    val image_status:String,
    val rarity:String,
    val textless:Boolean,
    //ImgURIs
    val small:String = "",
    val normal:String = "",
    val large:String = "",
    val png:String = "",
    val art_crop:String = "",
    val border_crop:String = "",
){
    companion object{
        fun toDomain(dbcard:DBCard, dbcardFace:DBCardFace?, dbimg_uris:DBImageURIs?) : Card{
            if(dbcard.layout == CardLayout.DOUBLE_FACED_TOKEN.layout ||
                dbcard.layout == CardLayout.TRANSFORM.layout ||
                dbcard.layout == CardLayout.MODAL_DFC.layout){
                var print_uri: Uri? = null
                var img_uri:ImageURIs? = null

                if(dbcard.print_search_uri.isNotEmpty()){
                    print_uri = Uri.parse(dbcard.print_search_uri)
                }
                val cfs = DBCardFace.toDomain(dbcardFace!!, dbimg_uris!!)
                val arrayListCF = ArrayList<CardFace>(cfs.size)
                for(i in cfs){
                    arrayListCF.add(i)
                }
                with(dbcard){
                    img_uri = DBImageURIs.toDomain(null, uuid, small, normal, large, png, art_crop, border_crop)
                }
                return Card(dbcard.idCard, dbcard.uuid, dbcard.lang, dbcard.oracle_id, print_uri, Uri.parse(dbcard.rulings_uri), Uri.parse(dbcard.scryfall_uri),
                    Uri.parse(dbcard.uri), arrayListCF, dbcard.cmc, dbcard.color_identity ?: arrayOf(""), dbcard.colors ?: arrayOf(""),
                    dbcard.keywords ?: arrayOf(""), CardLayout.getCardLayoutByString(dbcard.layout), dbcard.mana_cost, dbcard.name, dbcard.oracle_text,
                    dbcard.power,dbcard.produced_mana ?: arrayOf(""), dbcard.toughness, dbcard.type_line, dbcard.artist, dbcard.border_color, dbcard.highres_image,
                    dbcard.image_status, img_uri, dbcard.rarity, dbcard.textless)

            }
            else if(dbcard.layout == CardLayout.FLIP.layout){
                var print_uri: Uri? = null
                var img_uri:ImageURIs? = null

                if(dbcard.print_search_uri.isNotEmpty()){
                    print_uri = Uri.parse(dbcard.print_search_uri)
                }
                val cfs = DBCardFace.toDomain(dbcardFace!!, null)
                val arrayListCF = ArrayList<CardFace>(cfs.size)
                for(i in cfs){
                    arrayListCF.add(i)
                }
                with(dbcard){
                    img_uri = DBImageURIs.toDomain(null, uuid, small, normal, large, png, art_crop, border_crop)
                }
                return Card(dbcard.idCard, dbcard.uuid, dbcard.lang, dbcard.oracle_id, print_uri, Uri.parse(dbcard.rulings_uri), Uri.parse(dbcard.scryfall_uri),
                    Uri.parse(dbcard.uri), arrayListCF, dbcard.cmc, dbcard.color_identity ?: arrayOf(""), dbcard.colors ?: arrayOf(""),
                    dbcard.keywords ?: arrayOf(""), CardLayout.getCardLayoutByString(dbcard.layout), dbcard.mana_cost, dbcard.name, dbcard.oracle_text,
                    dbcard.power,dbcard.produced_mana ?: arrayOf(""), dbcard.toughness, dbcard.type_line, dbcard.artist, dbcard.border_color, dbcard.highres_image,
                    dbcard.image_status, img_uri, dbcard.rarity, dbcard.textless)
            }
            else{
                var print_uri: Uri? = null
                var img_uri:ImageURIs? = null

                if(dbcard.print_search_uri.isNotEmpty()){
                    print_uri = Uri.parse(dbcard.print_search_uri)
                }
                with(dbcard){
                    img_uri = DBImageURIs.toDomain(null, uuid, small, normal, large, png, art_crop, border_crop)
                }
                return Card(dbcard.idCard, dbcard.uuid, dbcard.lang, dbcard.oracle_id, print_uri, Uri.parse(dbcard.rulings_uri), Uri.parse(dbcard.scryfall_uri),
                Uri.parse(dbcard.uri), null, dbcard.cmc, dbcard.color_identity ?: arrayOf(""), dbcard.colors ?: arrayOf(""),
                dbcard.keywords ?: arrayOf(""), CardLayout.getCardLayoutByString(dbcard.layout), dbcard.mana_cost, dbcard.name, dbcard.oracle_text,
                dbcard.power,dbcard.produced_mana ?: arrayOf(""), dbcard.toughness, dbcard.type_line, dbcard.artist, dbcard.border_color, dbcard.highres_image,
                dbcard.image_status, img_uri, dbcard.rarity, dbcard.textless)
            }
        }
        fun fromDomain(card: Card) : DBCard{
            var cardFaceID:Long? = null
            if(card.card_faces?.get(0) != null &&
                card.card_faces.get(0).id != null){
                cardFaceID = card.card_faces.get(0).id
            }
            if(card.card_faces?.get(1) != null &&
                card.card_faces.get(1).id != null &&
                cardFaceID == null){
                cardFaceID = card.card_faces.get(1).id
            }
            return DBCard(card.id, cardFaceID, card.uuid, card.lang, card.oracle_id, card.print_search_uri.toString(), card.rulings_uri.toString(),
                card.scryfall_uri.toString(), card.uri.toString(), card.cmc, card.color_identity, card.colors, card.keywords, card.layout.toString(),
                card.mana_cost.toString(), card.name, card.oracle_text ?: "", card.power ?: "", card.produced_mana, card.toughness ?: "",
                card.type_line, card.artist ?: "", card.border_color, card.highres_image, card.image_status,card.rarity, card.textless,
                card.image_uris?.small ?: "",card.image_uris?.normal ?: "",card.image_uris?.large ?: "",card.image_uris?.png ?: "",
                card.image_uris?.art_crop ?: "",card.image_uris?.border_crop ?: "")
        }
    }
}
