package com.tenorinho.magiccards

import android.app.Application
import com.tenorinho.magiccards.db.LocalDatabase

class App:Application(){
    private val database: LocalDatabase by lazy { LocalDatabase.getInstance(this) }
    val cardRepository:CardRepository by lazy{
        with(database){
            CardRepository(getCardDAO(), getCardFaceDAO(), getImageURIsDAO())
        }
    }
}