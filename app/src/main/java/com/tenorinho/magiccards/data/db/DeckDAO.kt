package com.tenorinho.magiccards.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.tenorinho.magiccards.data.models.dto.db.DBDeckWithCards

@Dao interface DeckDAO {
    @Transaction
    @Query("SELECT * FROM deck_table WHERE idDeck LIKE :id")
    fun getDeckById(id:Long):DBDeckWithCards
}