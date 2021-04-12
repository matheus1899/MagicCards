package com.tenorinho.magiccards.data.db

import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Dao
import com.tenorinho.magiccards.data.models.dto.db.DBCardFace

@Dao interface CardFaceDAO {
    @Query("SELECT * FROM card_face_table WHERE uuid_card LIKE :uuid_card")
    suspend fun getCardFaceByUUIDCard(uuid_card:String): DBCardFace?
    @Query("SELECT * FROM card_face_table WHERE id_CardFace LIKE :id")
    suspend fun getCardFaceByID(id:Long): DBCardFace?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCardFace(cardFace: DBCardFace):Long
    @Query("DELETE FROM card_face_table WHERE uuid_card LIKE :uuid_card")
    suspend fun deleteCardFaceByUUIDCard(uuid_card: String)
}