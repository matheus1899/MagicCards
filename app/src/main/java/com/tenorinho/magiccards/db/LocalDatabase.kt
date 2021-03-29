package com.tenorinho.magiccards.db

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tenorinho.magiccards.models.dto.db.DBCard
import com.tenorinho.magiccards.models.dto.db.DBCardFace
import com.tenorinho.magiccards.models.dto.db.DBImageURIs

@Database(entities=arrayOf(DBCard::class, DBCardFace::class, DBImageURIs::class), version = 1, exportSchema=false)
abstract class LocalDatabase:RoomDatabase() {
    abstract fun getCardDAO():CardDAO
    abstract fun getCardFaceDAO():CardFaceDAO
    abstract fun getImageURIsDAO():ImageURIsDAO
    companion object{
        @Volatile private var INSTANCIA: LocalDatabase? = null
        fun getInstance(context: Context): LocalDatabase {
            return INSTANCIA ?: synchronized(this){
                val instance: LocalDatabase = Room.databaseBuilder(
                    context,
                    LocalDatabase::class.java,
                    "local_database"
                ).build()
                INSTANCIA = instance
                instance
            }
        }
    }
}