package com.tenorinho.magiccards.db

import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Delete
import androidx.room.Dao
import com.tenorinho.magiccards.models.dto.db.DBCardFace

@Dao interface CardFaceDAO {
    @Query("SELECT * FROM card_face_table WHERE uuid_card LIKE :uuid_card")
    suspend fun getCardFaceByUUIDCard(uuid_card:String): DBCardFace?
    @Query("SELECT * FROM card_face_table WHERE id LIKE :id")
    suspend fun getCardFaceByID(id:Int): DBCardFace?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCardFace(cardFace: DBCardFace)
    @Query("DELETE FROM card_face_table WHERE uuid_card LIKE :uuid_card")
    suspend fun deleteCardFaceByUUIDCard(uuid_card: String)
}