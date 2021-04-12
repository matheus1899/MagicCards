package com.tenorinho.magiccards.data.db

import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Dao
import com.tenorinho.magiccards.data.models.dto.db.DBImageURIs

@Dao interface ImageURIsDAO {
    @Query("SELECT * FROM img_uris_table WHERE uuid_card LIKE :uuid_card")
    suspend fun getImageURIsByUUIDCard(uuid_card:String): DBImageURIs?
    @Query("SELECT * FROM img_uris_table WHERE id_imgURIs LIKE :id")
    suspend fun getImageURIsByID(id:Long): DBImageURIs?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImageURIs(imageURIs: DBImageURIs):Long
    @Query("DELETE FROM img_uris_table WHERE uuid_card LIKE :uuid_card")
    suspend fun deleteImageURIsByUUIDCard(uuid_card: String)
}