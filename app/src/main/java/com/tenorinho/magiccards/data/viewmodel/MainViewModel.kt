package com.tenorinho.magiccards.data.viewmodel

import androidx.lifecycle.*
import com.tenorinho.magiccards.data.repository.CardRepository
import com.tenorinho.magiccards.data.models.domain.Card
import com.tenorinho.magiccards.data.models.domain.CardLayout
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CardRepository) : ViewModel() {
    var isExpanded:Boolean = false
    val error = MutableLiveData<Throwable>()
    val listCards = MutableLiveData<ArrayList<Card>>()
    val searchText = MutableLiveData<String>()
    val progressBarVisibility = MutableLiveData<Boolean>()
    var whiteSelected : Boolean = false
    var blueSelected : Boolean = false
    var greenSelected : Boolean = false
    var redSelected : Boolean = false
    var blackSelected : Boolean = false
    //ShowCard
    val selectedCard = MutableLiveData<Card>()
    val isTwoFacedOrFlip = MutableLiveData<Boolean>()
    val cardRotation = MutableLiveData<Float>()
    val textName = MutableLiveData<String>()
    val textOracle = MutableLiveData<String>()
    val textTypeLine = MutableLiveData<String>()
    val textPowerToughness = MutableLiveData<String>()
    val buttonsIsEnabled = MutableLiveData<Boolean>()
    val uriImg1 = MutableLiveData<String>()
    val uriImg2 = MutableLiveData<String>()
    val manaCost = MutableLiveData<String>()
    var isCardChanged:Boolean
    private var cardID:Long?

    init{
        progressBarVisibility.value = false
        searchText.value = ""
        //ShowCard
        cardID =  null
        isTwoFacedOrFlip.value = false
        buttonsIsEnabled.value = true
        cardRotation.value = 0F
        isCardChanged = false
        manaCost.value = ""
    }
    fun search(){
        progressBarVisibility.value = true
        var txt = searchText.value
        val txtInitial = txt
        if(!txt.isNullOrBlank()){
            if(whiteSelected || blueSelected || greenSelected || redSelected || blackSelected){
                txt = "m:{"
                if(whiteSelected){ txt+="W" }
                if(blueSelected){ txt+="U" }
                if(greenSelected){ txt+="G" }
                if(redSelected){ txt+="R" }
                if(blackSelected){ txt+="B" }
                txt += "} "
                txt += txtInitial
            }
            viewModelScope.launch{
                repository.search(txt, txtInitial!!, ::onSearchResult, ::onFailure)
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
        bindError(t)
        progressBarVisibility.value = false
    }
    fun setSelectedCard(position:Int?){
        if(position == null || listCards.value == null){
            return
        }
        val card = listCards.value!!.get(position)
        bindCard(card)
    }
    //ShowCard
    fun loadRandomCard(){
        viewModelScope.launch {
            repository.loadRandomCard(::bindCard, ::bindError)
        }
    }
    fun flipCardHasRotate(){
        if(selectedCard.value != null){
            if(isCardChanged){
                val cF2 = selectedCard.value!!.card_faces?.get(1)
                textName.value = cF2?.printed_name ?: cF2?.name ?: ""
                textTypeLine.value =  cF2?.printed_type_line ?: cF2?.type_line ?: ""
                textOracle.value =  cF2?.printed_text ?: cF2?.oracle_text ?: ""
                var pt =  cF2?.power ?: ""
                pt += "/"
                pt +=  cF2?.toughness ?: ""
                textPowerToughness.value = if(!pt.isNullOrBlank() && pt.length > 2) pt else ""
            }
            else{
                val cF1 = selectedCard.value!!.card_faces?.get(0)
                textName.value = cF1?.printed_name ?: cF1?.name ?: ""
                textTypeLine.value =  cF1?.printed_type_line ?: cF1?.type_line ?: ""
                textOracle.value =  cF1?.printed_text ?: cF1?.oracle_text ?: ""
                var pt =  cF1?.power ?: ""
                pt += "/"
                pt +=  cF1?.toughness ?: ""
                textPowerToughness.value = if(!pt.isNullOrBlank() && pt.length > 2) pt else ""
            }
        }
    }
    fun saveCard(){
        if(buttonsIsEnabled.value == false){
            return
        }
        buttonsIsEnabled.value = false
        selectedCard.value?.id = cardID
        viewModelScope.launch { repository.saveCard(selectedCard.value, ::bindSuccessOnSaveCard, ::bindFailureOnSaveCard) }
    }
    private fun bindSuccessOnSaveCard(idCard:Long){
        this.cardID = idCard
        buttonsIsEnabled.value = true
    }
    private fun bindFailureOnSaveCard(t: Throwable){
        bindError(t)
        buttonsIsEnabled.value = true
    }
    private fun bindCard(card:Card?){
        if(card != null){
            viewModelScope.launch {
                repository.getIdByCardName(card.name, ::bindCardId)
            }
            if(card.layout == CardLayout.FLIP){
                isTwoFacedOrFlip.value = true
                cardRotation.value = 0F
                uriImg1.value = card.image_uris?.normal ?: ""
                uriImg2.value = ""
                val cF1 = card.card_faces?.get(0)
                textName.value = cF1?.printed_name ?: cF1?.name ?: ""
                textTypeLine.value =  cF1?.printed_type_line ?: cF1?.type_line ?: ""
                textOracle.value =  cF1?.printed_text ?: cF1?.oracle_text ?: ""
                manaCost.value = card.mana_cost ?: ""
                var pt =  cF1?.power?.replace(".5","½") ?: ""
                pt += "|"
                pt +=  cF1?.toughness?.replace(".5","½") ?: ""
                textPowerToughness.value = if(!pt.isNullOrBlank() && pt.length > 2) pt else ""
            }
            else if(card.layout == CardLayout.TRANSFORM ||
                card.layout == CardLayout.DOUBLE_FACED_TOKEN ||
                card.layout == CardLayout.MODAL_DFC){
                isTwoFacedOrFlip.value = true
                cardRotation.value = 0F
                manaCost.value = card.mana_cost ?: ""
                uriImg1.value = card.card_faces?.get(0)?.image_uris?.normal ?: ""
                uriImg2.value = card.card_faces?.get(1)?.image_uris?.normal ?: ""
                textName.value = card.card_faces?.get(0)?.printed_name ?: card.card_faces?.get(0)?.name ?: ""
                textTypeLine.value = card.card_faces?.get(0)?.printed_type_line ?: card.card_faces?.get(0)?.type_line ?: ""
                textOracle.value = card.card_faces?.get(0)?.printed_text ?: card.card_faces?.get(0)?.oracle_text ?: ""
                var pt = card.card_faces?.get(0)?.power?.replace(".5","½")
                pt += "|"
                pt += card.card_faces?.get(0)?.toughness?.replace(".5","½")
                textPowerToughness.value = if(pt!!.length > 2) pt else ""
            }
            else if(card.layout == CardLayout.PLANAR){
                isTwoFacedOrFlip.value = false
                cardRotation.value = 90F
                manaCost.value = card.mana_cost ?: ""
                uriImg1.value = card.image_uris?.normal ?: ""
                uriImg2.value = ""
                textName.value = card.printed_name ?: card.name ?: ""
                textTypeLine.value = card.printed_type_line ?: card.type_line ?: ""
                textOracle.value = card.printed_text ?: card.oracle_text ?: ""
                val pt = card.power?.replace(".5","½") + "|" + card.toughness?.replace(".5","½")
                textPowerToughness.value = if(pt.length > 2) pt else ""
            }
            else{
                isTwoFacedOrFlip.value = false
                cardRotation.value = 0F
                uriImg1.value = card.image_uris?.normal ?: ""
                uriImg2.value = ""
                manaCost.value = card.mana_cost ?: ""
                textName.value = card.printed_name ?: card.name ?: ""
                textTypeLine.value = card.printed_type_line ?: card.type_line ?: ""
                textOracle.value = card.printed_text ?: card.oracle_text ?: ""
                val pt = card.power?.replace(".5","½") + "|" + card.toughness?.replace(".5","½")
                textPowerToughness.value = if(pt.length > 2) pt else ""
            }
            selectedCard.value = card
        }
        else{
            textName.value = ""
            textTypeLine.value = ""
            textOracle.value = ""
            textPowerToughness.value = ""
            cardRotation.value = 0F
            uriImg1.value = ""
            uriImg2.value = ""
        }
    }
    private fun bindError(t:Throwable){
        this.error.value = t
        this.error.value = Throwable("")
    }
    private fun bindCardId(id:Long?){
        cardID = id
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
