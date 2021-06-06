package com.tenorinho.magiccards.data.models.dto.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deck_table")
data class DBDeck(
    @PrimaryKey(autoGenerate = true)
    val idDeck:Long,
    val name:String,
    val quantity:Byte,
    val colors:Array<String>,
    val format:String,
)