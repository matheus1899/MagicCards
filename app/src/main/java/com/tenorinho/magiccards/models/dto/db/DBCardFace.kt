package com.tenorinho.magiccards.models.dto.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_face_table")
data class DBCardFace(
    @PrimaryKey(autoGenerate = false) val id:Int,
    val uuid_card:String,
    val id_img_uris:Int,
    val layout:String,

    val artist_front: String,
    val artist_back:String,

    val color_indicator_front: String,
    val color_indicator_back: String,

    val colors_front: String,
    val colors_back: String,

    val mana_cost_front: String,
    val mana_cost_back: String,

    val name_front: String,
    val name_back: String,

    val oracle_text_front: String,
    val oracle_text_back: String,

    val power_front: String,
    val power_back: String,

    val toughness_front: String,
    val toughness_back: String,

    val type_line_front: String,
    val type_line_back: String
)