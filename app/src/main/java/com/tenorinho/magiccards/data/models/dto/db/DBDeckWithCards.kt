package com.tenorinho.magiccards.data.models.dto.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class DBDeckWithCards(
    @Embedded val deck:DBDeck,
    @Relation(
        entity = DBCard::class,
        parentColumn = "idDeck",
        entityColumn = "idCard",
        associateBy = Junction(DBDeckCardCrossRef::class, parentColumn = "idDeck", entityColumn = "idCard")
    )
    val cards:List<DBCard>
)
