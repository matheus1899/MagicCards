package com.tenorinho.magiccards.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Delete
import com.tenorinho.magiccards.models.dto.db.DBCard

@Dao interface CardDAO {
    @Query("SELECT * FROM card_table WHERE uuid LIKE :uuid")
    fun getCardByUUID(uuid:String):DBCard

    @Query("SELECT * FROM card_table WHERE id LIKE :id")
    fun getCardByID(id:Int):DBCard

    @Query("SELECT * FROM card_table WHERE card_name LIKE :name")
    fun searchCardsByName(name:String):List<DBCard>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(card:DBCard):Int

    @Delete
    fun deleteCardByUUID(uuid: String)

    @Delete
    fun deleteCardByID(id: Int)
}