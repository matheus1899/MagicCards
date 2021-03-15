package com.tenorinho.magiccards.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class Database:RoomDatabase() {
    abstract fun getCardDAO():CardDAO
    abstract fun getCardFaceDAO():CardFaceDAO
    abstract fun getImageURIsDAO():ImageURIsDAO
    companion object{
        @Volatile private var INSTANCIA: Database? = null
        fun getInstance(context: Context): Database {
            return INSTANCIA ?: synchronized(this){
                val instance: Database = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "local_database"
                ).build()
                INSTANCIA = instance
                instance
            }
        }
    }
}