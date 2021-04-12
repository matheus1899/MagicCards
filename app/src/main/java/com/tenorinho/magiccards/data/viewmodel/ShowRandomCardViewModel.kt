package com.tenorinho.magiccards.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tenorinho.magiccards.data.models.domain.Card
import com.tenorinho.magiccards.data.models.domain.CardLayout
import com.tenorinho.magiccards.data.repository.CardRepository
import kotlinx.coroutines.launch

class ShowRandomCardViewModel(private val repository:CardRepository) : ViewModel(){
    val randomCard = MutableLiveData<Card>()
    val progressBarVisibility = MutableLiveData<Boolean>()
    val error = MutableLiveData<Throwable>()
    val isTwoFacedOrFlip = MutableLiveData<Boolean>()
    val cardRotation = MutableLiveData<Float>()
    val textName = MutableLiveData<String>()
    val textOracle = MutableLiveData<String>()
    val textTypeLine = MutableLiveData<String>()
    val textPowerToughness = MutableLiveData<String>()
    val buttonsIsEnabled = MutableLiveData<Boolean>()
    val uriImg1 = MutableLiveData<String>()
    val uriImg2 = MutableLiveData<String>()
    var isCardChanged:Boolean
    private var cardID:Long?

    init{
        cardID =  null
        progressBarVisibility.value = false
        isTwoFacedOrFlip.value = false
        buttonsIsEnabled.value = true
        cardRotation.value = 0F
        isCardChanged = false
    }
    fun loadRandomCard(){
        if(progressBarVisibility.value == true){
            return
        }
        progressBarVisibility.value = true
        viewModelScope.launch {
            repository.loadRandomCard(::bindCard, ::bindError)
        }
    }
    fun flipCardHasRotate(){
        if(randomCard.value != null){
            if(isCardChanged){
                val cF2 = randomCard.value!!.card_faces?.get(1)
                textName.value = cF2?.name ?: ""
                textTypeLine.value =  cF2?.type_line ?: ""
                textOracle.value =  cF2?.oracle_text ?: ""
                var pt =  cF2?.power ?: ""
                pt += "/"
                pt +=  cF2?.toughness ?: ""
                textPowerToughness.value = if(!pt.isNullOrBlank() && pt.length > 2) pt else ""
            }
            else{
                val cF1 = randomCard.value!!.card_faces?.get(0)
                textName.value = cF1?.name ?: ""
                textTypeLine.value =  cF1?.type_line ?: ""
                textOracle.value =  cF1?.oracle_text ?: ""
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
        progressBarVisibility.value = true
        randomCard.value?.id = cardID
        viewModelScope.launch { repository.saveCard(randomCard.value, ::bindSuccessOnSaveCard, ::bindFailureOnSaveCard) }
    }
    private fun bindSuccessOnSaveCard(idCard:Long){
        this.cardID = idCard
        error.value = Throwable("Salvo com sucesso")
        buttonsIsEnabled.value = true
        progressBarVisibility.value = false
    }
    private fun bindFailureOnSaveCard(t: Throwable){
        error.value = t
        buttonsIsEnabled.value = true
        progressBarVisibility.value = false
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
                textName.value = cF1?.name ?: ""
                textTypeLine.value =  cF1?.type_line ?: ""
                textOracle.value =  cF1?.oracle_text ?: ""
                var pt =  cF1?.power ?: ""
                pt += "/"
                pt +=  cF1?.toughness ?: ""
                textPowerToughness.value = if(!pt.isNullOrBlank() && pt.length > 2) pt else ""
            }
            else if(card.layout == CardLayout.TRANSFORM ||
                    card.layout == CardLayout.DOUBLE_FACED_TOKEN ||
                    card.layout == CardLayout.MODAL_DFC){
                isTwoFacedOrFlip.value = true
                cardRotation.value = 0F
                uriImg1.value = card.card_faces?.get(0)?.image_uris?.normal ?: ""
                uriImg2.value = card.card_faces?.get(1)?.image_uris?.normal ?: ""
                textName.value = card.card_faces?.get(0)?.name ?: ""
                textTypeLine.value = card.card_faces?.get(0)?.type_line ?: ""
                textOracle.value = card.card_faces?.get(0)?.oracle_text ?: ""
                var pt = card.card_faces?.get(0)?.power
                pt += "/"
                pt += card.card_faces?.get(0)?.toughness
                textPowerToughness.value = if(pt!!.length > 2) pt else ""
            }
            else if(card.layout == CardLayout.PLANAR){
                isTwoFacedOrFlip.value = false
                cardRotation.value = 90F
                uriImg1.value = card.image_uris?.normal ?: ""
                uriImg2.value = ""
                textName.value = card.name ?: ""
                textTypeLine.value = card.type_line ?: ""
                textOracle.value = card.oracle_text ?: ""
                val pt = card.power + "/" + card.toughness
                textPowerToughness.value = if(pt.length > 2) pt else ""
            }
            else{
                isTwoFacedOrFlip.value = false
                cardRotation.value = 0F
                uriImg1.value = card.image_uris?.normal ?: ""
                uriImg2.value = ""
                textName.value = card.name ?: ""
                textTypeLine.value = card.type_line ?: ""
                textOracle.value = card.oracle_text ?: ""
                val pt = card.power + "/" + card.toughness
                textPowerToughness.value = if(pt.length > 2) pt else ""
            }
            randomCard.value = card
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
        progressBarVisibility.value = false
    }
    private fun bindError(t:Throwable){
        this.error.value = t
        progressBarVisibility.value = false
    }
    private fun bindCardId(id:Long?){
        cardID = id
    }
}
class ShowRandomCardViewModelFactory(private val cardRepository: CardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(c: Class<T>): T {
        if (c.isAssignableFrom(ShowRandomCardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShowRandomCardViewModel(cardRepository) as T
        }
        throw IllegalArgumentException("Class ViewModel desconhecida")
    }
}