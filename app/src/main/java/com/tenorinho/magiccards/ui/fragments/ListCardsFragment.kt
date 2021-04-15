package com.tenorinho.magiccards.ui.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.tenorinho.magiccards.*
import com.tenorinho.magiccards.data.viewmodel.MainViewModel
import com.tenorinho.magiccards.data.viewmodel.MainViewModelFactory
import com.tenorinho.magiccards.databinding.FragmentListCardsBinding
import com.tenorinho.magiccards.ui.adapters.CardAdapter

class ListCardsFragment: Fragment(),
    View.OnClickListener,
    TextView.OnEditorActionListener,
    View.OnFocusChangeListener{
    private var duracaoCurta:Long = 0L
    private var duracaoMedia:Long = 0L
    private var duracaoLonga:Long = 0L
    private lateinit var binding:FragmentListCardsBinding
    private lateinit var adapter: CardAdapter
    private lateinit var imm: InputMethodManager
    private lateinit var viewModel: MainViewModel
    private lateinit var navController:NavController

    companion object{
        fun newInstance():ListCardsFragment{
            return ListCardsFragment()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        duracaoCurta = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        duracaoMedia = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        duracaoLonga = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
        adapter = CardAdapter(this)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_cards, container, false)
        navController = findNavController()
        getViewModel()
        binding.viewModel = viewModel
        binding.listCardsRecyclerView.adapter = adapter
        setBehaviors()
        viewModel.error.observe(requireActivity(), Observer { showLongToast(it.message)})
        viewModel.listCards.observe(requireActivity(), Observer{
            adapter.updateList(it)
            viewModel.progressBarVisibility.value = false
        })
        return binding.root
    }
    fun onItemClick(position:Int){
        val action = ListCardsFragmentDirections.actionListCardsFragmentToShowCardFragment(position)
        navController.navigate(action)
    }
    private fun getViewModel(){
        val app = activity?.application as App
        val factory = MainViewModelFactory(app.cardRepository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)
    }
    private fun showLongToast(message:String?){
        if(message.isNullOrEmpty()){
            return
        }
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
    private fun setBehaviors(){
        binding.listCardsBtnWhite.setOnClickListener(this)
        binding.listCardsBtnBlue.setOnClickListener(this)
        binding.listCardsBtnGreen.setOnClickListener(this)
        binding.listCardsBtnRed.setOnClickListener(this)
        binding.listCardsBtnBlack.setOnClickListener(this)
        binding.listCardsBtnSearch.setOnClickListener(this)
        binding.listCardsSearchEditText.setOnClickListener(this)
        binding.listCardsSearchEditText.setOnFocusChangeListener(this)
        binding.listCardsSearchEditText.setOnEditorActionListener(this)
    }
    private fun abrirTeclado(){
        imm.showSoftInput(binding.listCardsSearchEditText, 0)
    }
    private fun fecharTeclado(v: View){
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
    private fun onSearch(){
        viewModel.search()
    }
    override fun onResume(){
        super.onResume()
        if(viewModel.isExpanded){
            expandirToolbar()
            binding.listCardsSearchEditText.requestFocus()
        }
        if(viewModel.whiteSelected){ onButtonManaClicked("white", true) }
        else if(viewModel.blueSelected) { onButtonManaClicked("blue", true) }
        else if(viewModel.greenSelected) { onButtonManaClicked("green", true) }
        else if(viewModel.redSelected) { onButtonManaClicked("red", true) }
        else if(viewModel.blackSelected) { onButtonManaClicked("black", true) }
    }
    private fun expandirToolbar(){
        binding.listCardsSearchEditText.apply{
            alpha = 0F
            visibility = View.VISIBLE
            animate()
                .alpha(1F)
                .setDuration(duracaoMedia)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.listCardsSearchEditText.requestFocus()
                    }
                })
        }
        val c = ChangeBounds()
        c.setStartDelay(250L)
        c.setInterpolator(AnticipateOvershootInterpolator())
        c.setDuration(duracaoMedia)
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup, c)
        val p = binding.listCardsToolbar.layoutParams
        p.height = (resources.displayMetrics.density * 118).toInt()
        binding.listCardsToolbar.layoutParams = p
        binding.listCardsBtnSearch
            .animate()
            .alpha(0F)
            .setDuration(duracaoMedia)
            .setListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.listCardsBtnSearch.visibility = View.GONE
                }
            })

        binding.listCardsGroupBtnMana.apply{
            alpha = 0F
            animate()
                .alpha(1F)
                .setStartDelay(300L)
                .setDuration(duracaoLonga)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.listCardsGroupBtnMana.visibility = View.VISIBLE
                    }
                })
        }
    }
    private fun recolherToolbar(){
        binding.listCardsGroupBtnMana.apply{
            animate()
                .alpha(0F)
                .setDuration(0)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.listCardsGroupBtnMana.visibility = View.GONE
                    }
                })
        }
        binding.listCardsSearchEditText.apply{
            animate()
                .alpha(0F)
                .setDuration(duracaoMedia)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.listCardsSearchEditText.visibility = View.GONE
                    }
                })
        }

        val c = ChangeBounds()
        c.setStartDelay(250L)
        c.setInterpolator(AnticipateOvershootInterpolator())
        c.setDuration(duracaoLonga)
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup, c)

        val p = binding.listCardsToolbar.layoutParams
        p.height = (resources.displayMetrics.density * 64).toInt()
        binding.listCardsToolbar.layoutParams = p
        binding.listCardsBtnSearch.apply{
            alpha = 0F
            visibility = View.VISIBLE
            animate()
                .alpha(1F)
                .setDuration(duracaoMedia)
                .setListener(null)
        }
    }
    private fun onButtonManaClicked(mana:String, isResizing:Boolean){
        val m = mana.toLowerCase()
        when(m) {
            "white" -> {
                if(isResizing){
                    binding.listCardsBtnWhite.setBackgroundResource(R.drawable.bg_btn_white_selected)
                    binding.listCardsBtnWhite.setColorFilter(resources.getColor(R.color.white_mana))
                    return
                }
                viewModel.whiteSelected = !viewModel.whiteSelected
                if (viewModel.whiteSelected) {
                    binding.listCardsBtnWhite.setBackgroundResource(R.drawable.bg_btn_white_selected)
                    binding.listCardsBtnWhite.setColorFilter(resources.getColor(R.color.white_mana))
                    if (viewModel.blueSelected) {
                        viewModel.blueSelected = false
                        binding.listCardsBtnBlue.background = null
                        binding.listCardsBtnBlue.setColorFilter(null)
                    }
                    if (viewModel.greenSelected) {
                        viewModel.greenSelected = false
                        binding.listCardsBtnGreen.background = null
                        binding.listCardsBtnGreen.setColorFilter(null)
                    }
                    if (viewModel.redSelected) {
                        viewModel.redSelected = false
                        binding.listCardsBtnRed.background = null
                        binding.listCardsBtnRed.setColorFilter(null)
                    }
                    if (viewModel.blackSelected) {
                        viewModel.blackSelected = false
                        binding.listCardsBtnBlack.background = null
                        binding.listCardsBtnBlack.setColorFilter(null)
                    }
                } else {
                    binding.listCardsBtnWhite.background = null
                    binding.listCardsBtnWhite.setColorFilter(null)
                }
            }
            "blue" -> {
                if(isResizing){
                    binding.listCardsBtnBlue.setBackgroundResource(R.drawable.bg_btn_blue_selected)
                    binding.listCardsBtnBlue.setColorFilter(resources.getColor(R.color.blue_mana))
                    return
                }
                viewModel.blueSelected = !viewModel.blueSelected
                if (viewModel.blueSelected) {
                    binding.listCardsBtnBlue.setBackgroundResource(R.drawable.bg_btn_blue_selected)
                    binding.listCardsBtnBlue.setColorFilter(resources.getColor(R.color.blue_mana))
                    if (viewModel.whiteSelected) {
                        viewModel.whiteSelected = false
                        binding.listCardsBtnWhite.background = null
                        binding.listCardsBtnWhite.setColorFilter(null)
                    }
                    if (viewModel.greenSelected) {
                        viewModel.greenSelected = false
                        binding.listCardsBtnGreen.background = null
                        binding.listCardsBtnGreen.setColorFilter(null)
                    }
                    if (viewModel.redSelected) {
                        viewModel.redSelected = false
                        binding.listCardsBtnRed.background = null
                        binding.listCardsBtnRed.setColorFilter(null)
                    }
                    if (viewModel.blackSelected) {
                        viewModel.blackSelected = false
                        binding.listCardsBtnBlack.background = null
                        binding.listCardsBtnBlack.setColorFilter(null)
                    }
                } else {
                    binding.listCardsBtnBlue.background = null
                    binding.listCardsBtnBlue.setColorFilter(null)
                }
            }
            "green" -> {
                if(isResizing){
                    binding.listCardsBtnGreen.setBackgroundResource(R.drawable.bg_btn_green_selected)
                    binding.listCardsBtnGreen.setColorFilter(resources.getColor(R.color.green_mana))
                    return
                }
                viewModel.greenSelected = !viewModel.greenSelected
                if (viewModel.greenSelected) {
                    binding.listCardsBtnGreen.setBackgroundResource(R.drawable.bg_btn_green_selected)
                    binding.listCardsBtnGreen.setColorFilter(resources.getColor(R.color.green_mana))
                    if (viewModel.whiteSelected) {
                        viewModel.whiteSelected = false
                        binding.listCardsBtnWhite.background = null
                        binding.listCardsBtnWhite.setColorFilter(null)
                    }
                    if (viewModel.blueSelected) {
                        viewModel.blueSelected = false
                        binding.listCardsBtnBlue.background = null
                        binding.listCardsBtnBlue.setColorFilter(null)
                    }
                    if (viewModel.redSelected) {
                        viewModel.redSelected = false
                        binding.listCardsBtnRed.background = null
                        binding.listCardsBtnRed.setColorFilter(null)
                    }
                    if (viewModel.blackSelected) {
                        viewModel.blackSelected = false
                        binding.listCardsBtnBlack.background = null
                        binding.listCardsBtnBlack.setColorFilter(null)
                    }
                } else {
                    binding.listCardsBtnGreen.background = null
                    binding.listCardsBtnGreen.setColorFilter(null)
                }
            }
            "red" -> {
                if(isResizing){
                    binding.listCardsBtnRed.setBackgroundResource(R.drawable.bg_btn_red_selected)
                    binding.listCardsBtnRed.setColorFilter(resources.getColor(R.color.red_mana))
                    return
                }
                viewModel.redSelected = !viewModel.redSelected
                if (viewModel.redSelected) {
                    binding.listCardsBtnRed.setBackgroundResource(R.drawable.bg_btn_red_selected)
                    binding.listCardsBtnRed.setColorFilter(resources.getColor(R.color.red_mana))
                    if (viewModel.whiteSelected) {
                        viewModel.whiteSelected = false
                        binding.listCardsBtnWhite.background = null
                        binding.listCardsBtnWhite.setColorFilter(null)
                    }
                    if (viewModel.blueSelected) {
                        viewModel.blueSelected = false
                        binding.listCardsBtnBlue.background = null
                        binding.listCardsBtnBlue.setColorFilter(null)
                    }
                    if (viewModel.greenSelected) {
                        viewModel.greenSelected = false
                        binding.listCardsBtnGreen.background = null
                        binding.listCardsBtnGreen.setColorFilter(null)
                    }
                    if (viewModel.blackSelected) {
                        viewModel.blackSelected = false
                        binding.listCardsBtnBlack.background = null
                        binding.listCardsBtnBlack.setColorFilter(null)
                    }
                } else {
                    binding.listCardsBtnRed.background = null
                    binding.listCardsBtnRed.setColorFilter(null)
                }
            }
            "black" -> {
                if(isResizing){
                    binding.listCardsBtnBlack.setBackgroundResource(R.drawable.bg_btn_black_selected)
                    binding.listCardsBtnBlack.setColorFilter(resources.getColor(R.color.black_mana))
                    return
                }
                viewModel.blackSelected = !viewModel.blackSelected
                if (viewModel.blackSelected) {
                    binding.listCardsBtnBlack.setBackgroundResource(R.drawable.bg_btn_black_selected)
                    binding.listCardsBtnBlack.setColorFilter(resources.getColor(R.color.black_mana))
                    if (viewModel.whiteSelected) {
                        viewModel.whiteSelected = false
                        binding.listCardsBtnWhite.background = null
                        binding.listCardsBtnWhite.setColorFilter(null)
                    }
                    if (viewModel.blueSelected) {
                        viewModel.blueSelected = false
                        binding.listCardsBtnBlue.background = null
                        binding.listCardsBtnBlue.setColorFilter(null)
                    }
                    if (viewModel.greenSelected) {
                        viewModel.greenSelected = false
                        binding.listCardsBtnGreen.background = null
                        binding.listCardsBtnGreen.setColorFilter(null)
                    }
                    if (viewModel.redSelected) {
                        viewModel.redSelected = false
                        binding.listCardsBtnRed.background = null
                        binding.listCardsBtnRed.setColorFilter(null)
                    }
                } else {
                    binding.listCardsBtnBlack.background = null
                    binding.listCardsBtnBlack.setColorFilter(null)
                }
            }
            else ->{ return }
        }
    }
    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.list_cards_btn_search -> {
                    if(!viewModel.isExpanded){
                        expandirToolbar()
                    }
                    viewModel.isExpanded = !viewModel.isExpanded
                }
                R.id.list_cards_btn_white -> { onButtonManaClicked("white", false) }
                R.id.list_cards_btn_blue -> { onButtonManaClicked("blue", false) }
                R.id.list_cards_btn_green -> { onButtonManaClicked("green", false) }
                R.id.list_cards_btn_red -> { onButtonManaClicked("red", false) }
                R.id.list_cards_btn_black -> { onButtonManaClicked("black", false) }
            }
        }
    }
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if(v != null){
            when(v.id){
                R.id.list_cards_search_edit_text -> {
                    if(hasFocus){
                        abrirTeclado()
                        viewModel.isExpanded = true
                    }
                    else if(!hasFocus && binding.listCardsSearchEditText.text.isNullOrBlank()){
                        if(imm.isActive && imm.isAcceptingText){
                            fecharTeclado(binding.listCardsSearchEditText)
                        }
                        recolherToolbar()
                        viewModel.isExpanded = false
                    }
                }
            }
        }
    }
    override fun onEditorAction(v: TextView?, actionId: Int, keyEvent: KeyEvent?): Boolean {
        return when(actionId) {
            EditorInfo.IME_ACTION_SEARCH -> {
                //v?.apply{ fecharTeclado(this) }
                Thread.sleep(150)
                onSearch()
                true
            }
            EditorInfo.IME_ACTION_GO ->{
                //v?.apply{ fecharTeclado(this) }
                Thread.sleep(150)
                onSearch()
                true
            }
            EditorInfo.IME_ACTION_DONE ->{
                //v?.apply{ fecharTeclado(this) }
                Thread.sleep(150)
                onSearch()
                true
            }
            else -> false
        }
    }
}