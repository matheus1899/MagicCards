package com.tenorinho.magiccards.data.db

import androidx.room.*
import com.tenorinho.magiccards.data.models.dto.db.DBCard

@Dao interface CardDAO {
    @Transaction
    @Query("SELECT * FROM card_table WHERE uuid LIKE :uuid")
    suspend fun getCardByUUID(uuid:String):DBCard?

    @Transaction
    @Query("SELECT * FROM card_table WHERE idCard LIKE :id")
    suspend fun getCardByID(id:Long):DBCard?

    @Transaction
    @Query("SELECT * FROM card_table WHERE card_name LIKE :name")
    suspend fun searchCardsByName(name:String):List<DBCard>?

    @Query("SELECT EXISTS (SELECT COUNT(0) FROM card_table WHERE idCard = :id)")
    suspend fun cardExistsByID(id: Long):Boolean

    @Query("SELECT idCard FROM card_table WHERE card_name = :name")
    suspend fun getIdByCardName(name:String):Long?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCard(card:DBCard):Long

    @Query("SELECT * FROM card_table")
    suspend fun getAllCards():List<DBCard>?
}