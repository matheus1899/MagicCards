package com.tenorinho.magiccards.net

import com.tenorinho.magiccards.data.models.dto.network.NetworkCard
import com.tenorinho.magiccards.data.models.dto.network.NetworkCatalog
import com.tenorinho.magiccards.data.models.dto.network.NetworkListCards
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IScryfallService {
    @GET("cards/search")
    fun getCardsByText(@Query("q") search:String):Call<NetworkListCards>
    @GET("cards/{id}")
    fun getCardsByUUId(@Path("id") cardId:String):Call<NetworkCard>
    @GET("cards/autocomplete")
    fun getSugestedCatalog(@Query("q") search: String):Call<NetworkCatalog>
    @GET("cards/random")
    fun getRandomCard():Call<NetworkCard>
}