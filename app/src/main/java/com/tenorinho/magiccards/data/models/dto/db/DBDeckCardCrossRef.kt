package com.tenorinho.magiccards.data.models.dto.db

import androidx.room.Entity

@Entity(tableName = "DeckCardCrossRefTable", primaryKeys = arrayOf("idDeck","idCard"))
data class DBDeckCardCrossRef(
    val idDeck:Long,
    val idCard:Long
)