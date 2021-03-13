package com.tenorinho.magiccards.db

import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Delete
import androidx.room.Dao
import com.tenorinho.magiccards.models.dto.db.DBImageURIs

@Dao interface ImageURIsDAO {
    @Query("SELECT * FROM img_uris_table WHERE uuid_card LIKE :uuid_card")
    fun getImageURIsByUUIDCard(uuid_card:String): DBImageURIs

    @Query("SELECT * FROM img_uris_table WHERE id LIKE :id")
    fun getImageURIsByID(id:Int): DBImageURIs

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addImageURIs(imageURIs: DBImageURIs):Int

    @Query("DELETE FROM img_uris_table WHERE uuid_card LIKE :uuid_card")
    fun deleteImageURIsByUUIDCard(uuid_card: String)

    @Delete
    fun deleteImageURIsByID(id: Int)
}