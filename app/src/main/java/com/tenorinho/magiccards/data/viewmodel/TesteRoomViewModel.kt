package com.tenorinho.magiccards.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tenorinho.magiccards.data.models.domain.Card
import com.tenorinho.magiccards.data.repository.CardRepository
import kotlinx.coroutines.launch

class TesteRoomViewModel(private val repository: CardRepository): ViewModel(){
    val listCards = MutableLiveData<ArrayList<Card>>()
    val error = MutableLiveData<Throwable>()

    init {
        viewModelScope.launch {
            repository.getAllCardsFromDatabase(::bindSuccess, ::bindError)
        }
    }
    private fun bindError(t:Throwable){
        error.value = t
    }
    private fun bindSuccess(list:ArrayList<Card>){
        listCards.value = list
    }
}
class TesteRoomViewModelFactory(private val cardRepository: CardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(c: Class<T>): T {
        if (c.isAssignableFrom(TesteRoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TesteRoomViewModel(cardRepository) as T
        }
        throw IllegalArgumentException("Class ViewModel desconhecida")
    }
}