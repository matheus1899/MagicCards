package com.tenorinho.magiccards

import android.app.Application
import com.tenorinho.magiccards.data.db.LocalDatabase
import com.tenorinho.magiccards.data.repository.CardRepository

class App:Application(){
    private val database: LocalDatabase by lazy { LocalDatabase.getInstance(this) }
    val cardRepository: CardRepository by lazy{
        with(database){
            CardRepository(getCardDAO(), getCardFaceDAO(), getImageURIsDAO())
        }
    }
}