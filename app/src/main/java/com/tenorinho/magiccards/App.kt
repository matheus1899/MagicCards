package com.tenorinho.magiccards

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.tenorinho.magiccards.data.db.LocalDatabase
import com.tenorinho.magiccards.data.repository.CardRepository
import com.tenorinho.magiccards.data.viewmodel.MainViewModel
import com.tenorinho.magiccards.data.viewmodel.MainViewModelFactory
import com.tenorinho.magiccards.ui.fragments.ListCardsFragment
import com.tenorinho.magiccards.ui.fragments.ShowCardFragment

class App:Application(){
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
class AppContainer(app: App) {
    private lateinit var database: LocalDatabase
    private lateinit var cardRepository: CardRepository
    init{
        database = LocalDatabase.getInstance(app)
        cardRepository = CardRepository(database.getCardDAO(), database.getCardFaceDAO(), database.getImageURIsDAO())
    }
    fun getMainViewModel(viewModelStore: ViewModelStore):MainViewModel{
        val factory = MainViewModelFactory(cardRepository)
        return ViewModelProvider(viewModelStore, factory).get(MainViewModel::class.java)
    }
    fun getMainViewModel(viewModelStoreOwner: ViewModelStoreOwner):MainViewModel{
        val factory = MainViewModelFactory(cardRepository)
        return ViewModelProvider(viewModelStoreOwner, factory).get(MainViewModel::class.java)
    }
    fun inject(listCardsFragment: ListCardsFragment, activity:AppCompatActivity){
        listCardsFragment.viewModel = getMainViewModel(activity)
    }
    fun inject(showCardFragment: ShowCardFragment, activity:AppCompatActivity){
        showCardFragment.viewModel = getMainViewModel(activity)
    }

}