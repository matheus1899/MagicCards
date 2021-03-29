package com.tenorinho.magiccards.db

import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Delete
import androidx.room.Dao
import com.tenorinho.magiccards.models.dto.db.DBImageURIs

@Dao interface ImageURIsDAO {
    @Query("SELECT * FROM img_uris_table WHERE uuid_card LIKE :uuid_card")
    suspend fun getImageURIsByUUIDCard(uuid_card:String): DBImageURIs
    @Query("SELECT * FROM img_uris_table WHERE id LIKE :id")
    suspend fun getImageURIsByID(id:Int): DBImageURIs
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImageURIs(imageURIs: DBImageURIs)
    @Query("DELETE FROM img_uris_table WHERE uuid_card LIKE :uuid_card")
    suspend fun deleteImageURIsByUUIDCard(uuid_card: String)
    //@Delete
    //suspend fun deleteImageURIsByID(id: Int)
    //ERRO: A failure occurred while executing org.jetbrains.kotlin.gradle.internal.KaptExecution
}