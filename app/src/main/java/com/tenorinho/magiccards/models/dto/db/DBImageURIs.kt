package com.tenorinho.magiccards.models.dto.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "img_uris_table")
data class DBImageURIs(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val uuid_card:String,
    val is_card_face:Boolean,

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
    val border_crop_back:String,
)
