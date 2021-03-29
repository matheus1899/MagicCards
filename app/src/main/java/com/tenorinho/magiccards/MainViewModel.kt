package com.tenorinho.magiccards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
    var whiteSelected : Boolean = false
    var blueSelected : Boolean = false
    var greenSelected : Boolean = false
    var redSelected : Boolean = false
    var blackSelected : Boolean = false

    init{
        btnWManaSelected.value = false
        btnUManaSelected.value = false
        btnGManaSelected.value = false
        btnRManaSelected.value = false
        btnBManaSelected.value = false
    }
    fun search(){
        val s = searchText.value
        if(!s.isNullOrBlank()){
            if(btnWManaSelected.value!!){
                s + ""
            }
            else if(btnUManaSelected.value!!){
                s + ""
            }
            else if(btnGManaSelected.value!!){
                s + ""
            }
            else if(btnRManaSelected.value!!){
                s + ""
            }
            else if(btnBManaSelected.value!!){
                s + ""
            }
            // usar o cardRepository para
            // pegar a lista de cards da API
        }
    }
}
class MainViewModelFactory(private val cardRepository: CardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(c: Class<T>): T {
        if (c.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(cardRepository) as T
        }
        throw IllegalArgumentException("Class ViewModel desconhecida")
    }
}