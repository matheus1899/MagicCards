package com.tenorinho.magiccards.data.models.dto.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tenorinho.magiccards.data.models.domain.ImageURIs

@Entity(tableName = "img_uris_table")
data class DBImageURIs(
    @PrimaryKey(autoGenerate = true)
    var id_imgURIs:Long? = null,
    val uuid_card:String,

    val small_front:String,
    val small_back:String,

    val normal_front:String,
    val normal_back:String,

    val large_front:String,
    val large_back:String,

    val png_front:String,
    val png_back:String,

    val art_crop_front:String,
    val art_crop_back:String,

    val border_crop_front:String,
    val border_crop_back:String
){
    companion object{
        fun toDomain(id:Long?, uuid_card: String, small:String?, normal:String?, large:String?,
                     png:String?, art_crop: String?, border_crop:String?):ImageURIs{
            return ImageURIs(id, uuid_card, small ?: "", normal ?: "", large ?: "",
                png ?: "", art_crop ?: "", border_crop ?: "")
        }
        fun toDomain(dbImgURIs: DBImageURIs?, uuid_card: String):Array<ImageURIs>?{
            if(dbImgURIs == null){
                return null
            }
            var img1 : ImageURIs
            var img2 : ImageURIs
            img1 = ImageURIs(dbImgURIs?.id_imgURIs, uuid_card, dbImgURIs?.small_front ?: "", dbImgURIs?.normal_front ?: "",
                dbImgURIs?.large_front ?: "",dbImgURIs?.png_front ?: "", dbImgURIs?.art_crop_front ?: "",
                dbImgURIs?.border_crop_front ?: "")
            img2 = ImageURIs(dbImgURIs?.id_imgURIs, uuid_card, dbImgURIs?.small_back ?: "", dbImgURIs?.normal_back ?: "",
                dbImgURIs?.large_back ?: "", dbImgURIs?.png_back ?: "", dbImgURIs?.art_crop_back ?: "",
                dbImgURIs?.border_crop_back ?: "")

            return arrayOf(img1, img2)
        }
        fun fromDomain(imgFront:ImageURIs?, imgBack:ImageURIs?):DBImageURIs{
            var id:Long? = null
            var uuid = ""
            if(imgFront?.id != null){
                id = imgFront.id
            }
            if(imgBack?.id != null && id == null){
                id = imgBack.id
            }
            if(imgFront != null && imgFront.uuid_card.isNotEmpty()){
                uuid = imgFront.uuid_card
            }
            if(imgBack != null && imgBack.uuid_card.isNotEmpty() && uuid.isEmpty()){
                uuid = imgBack.uuid_card
            }

            return DBImageURIs(id, uuid, imgFront?.small ?: "", imgBack?.small ?: "",
                imgFront?.normal ?: "", imgBack?.normal ?: "", imgFront?.large ?: "", imgBack?.large ?: "",
                imgFront?.png ?: "", imgBack?.png ?: "", imgFront?.art_crop ?: "", imgBack?.art_crop ?: "",
                imgFront?.border_crop ?: "", imgBack?.border_crop ?: ""
            )
        }
    }
}

