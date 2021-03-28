package com.tenorinho.magiccards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tenorinho.magiccards.models.domain.Card

class MainViewModel(private val cardRepository: CardRepository) : ViewModel() {
    var isExpanded:Boolean = false
    val error = MutableLiveData<Throwable>()
    val listCards = MutableLiveData<List<Card>>()
    val searchText = MutableLiveData<String>()
    val btnWManaSelected = MutableLiveData<Boolean>()
    val btnUManaSelected = MutableLiveData<Boolean>()
    val btnGManaSelected = MutableLiveData<Boolean>()
    val btnRManaSelected = MutableLiveData<Boolean>()
    val btnBManaSelected = MutableLiveData<Boolean>()
    fun search(){
    }
}