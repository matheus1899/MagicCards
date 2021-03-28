package com.tenorinho.magiccards

import android.app.Application
import com.tenorinho.magiccards.db.Database

class App:Application(){
    private val database: Database by lazy { Database.getInstance(this) }
    private val cardRepository:CardRepository by lazy{
        with(database){
            CardRepository(getCardDAO(), getCardFaceDAO(), getImageURIsDAO())
        }
    }
}