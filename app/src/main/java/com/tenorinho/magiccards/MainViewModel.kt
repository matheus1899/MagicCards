package com.tenorinho.magiccards

import androidx.lifecycle.*
import com.tenorinho.magiccards.models.domain.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val cardRepository: CardRepository) : ViewModel() {
    var isExpanded:Boolean = false
    val error = MutableLiveData<Throwable>()
    val listCards = MutableLiveData<ArrayList<Card>>()
    val searchText = MutableLiveData<String>()
    val progressBarVisibility = MutableLiveData<Boolean>()
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
        progressBarVisibility.value = false
        searchText.value = ""
    }
    fun search(){
        progressBarVisibility.value = true
        val s = searchText.value
        if(!s.isNullOrBlank()){
            viewModelScope.launch{
                cardRepository.search(s, ::onSearchResult, ::onFailure)
            }
        }
    }
    fun onSearchResult(list:ArrayList<Card>?){
        if(list != null){
            listCards.value = list
        }
        progressBarVisibility.value = false
    }
    fun onFailure(t:Throwable){
        error.value = t
        progressBarVisibility.value = false
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
