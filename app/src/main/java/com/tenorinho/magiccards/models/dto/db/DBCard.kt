package com.tenorinho.magiccards.models.dto.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="card_table")
data class DBCard(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val uuid:String,
    val lang:String,
    val oracle_id:String,
    val print_search_uri: String,
    val rulings_uri:String,
    val scryfall_uri:String,
    val uri:String,
    val cmc:Float,
    val id_card_faces:Int = -1,
    val id_img_uri:Int = -1,
    val color_identity:String,              //criar uma unica string com todas as cores e usar split
    val colors:String,                      //criar uma unica string com todas as cores e usar split
    val keywords:String,                    //criar uma unica string com todas as cores e usar split
    val layout:String,
    val mana_cost:String,
    @ColumnInfo(name = "card_name")
    val name:String,
    val oracle_text:String,
    val power:String,
    @ColumnInfo(name = "prod_mana")
    val produced_mana:String,               //criar uma unica string com todas as cores e usar split
    val toughness:String,
    val type_line:String,
    val artist:String,
    val border_color:String,
    @ColumnInfo(name = "high_img")
    val highres_image:Boolean,
    @ColumnInfo(name = "img_status")
    val image_status:String,
    val rarity:String,
    val textless:Boolean,
)