package com.tenorinho.magiccards.ui.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.tenorinho.magiccards.R
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tenorinho.magiccards.App
import com.tenorinho.magiccards.MainViewModel
import com.tenorinho.magiccards.MainViewModelFactory
import com.tenorinho.magiccards.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
    View.OnClickListener,
    TextView.OnEditorActionListener,
    View.OnFocusChangeListener{
    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel:MainViewModel
    private var duracaoCurta:Long = 0L
    private var duracaoMedia:Long = 0L
    private var duracaoLonga:Long = 0L
    private lateinit var imm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }
    private fun init(){
        getViewModel()
        binding.viewModel = viewModel
        viewModel.error.observe(this, Observer { showLongToast(it.message)})
        setBehaviors()
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        duracaoCurta = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        duracaoMedia = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        duracaoLonga = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
    }
    private fun getViewModel(){
        val app = application as App
        val factory = MainViewModelFactory(app.cardRepository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }
    private fun setBehaviors(){
        binding.mainBtnWhite.setOnClickListener(this)
        binding.mainBtnBlue.setOnClickListener(this)
        binding.mainBtnGreen.setOnClickListener(this)
        binding.mainBtnRed.setOnClickListener(this)
        binding.mainBtnBlack.setOnClickListener(this)
        binding.mainBtnSearch.setOnClickListener(this)
        binding.mainSearchEditText.setOnClickListener(this)
        binding.mainSearchEditText.setOnFocusChangeListener(this)
    }
    private fun onSearch(){
        viewModel.search()
    }
    override fun onResume(){
        super.onResume()
        if(viewModel.isExpanded){
            expandirToolbar()
            binding.mainSearchEditText.requestFocus()
        }
    }
    private fun expandirToolbar(){
        binding.mainSearchEditText.apply{
            alpha = 0F
            visibility = View.VISIBLE
            animate()
                .alpha(1F)
                .setDuration(duracaoMedia)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.mainSearchEditText.requestFocus()
                    }
                })
        }
        val c = ChangeBounds()
        c.setStartDelay(250L)
        c.setInterpolator(AnticipateOvershootInterpolator())
        c.setDuration(duracaoMedia)
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup, c)
        val p = binding.mainToolbar.layoutParams
        p.height = (resources.displayMetrics.density * 118).toInt()
        binding.mainToolbar.layoutParams = p
        binding.mainBtnSearch
            .animate()
            .alpha(0F)
            .setDuration(duracaoMedia)
            .setListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.mainBtnSearch.visibility = View.GONE
                }
            })

        binding.mainGroupBtnMana.apply{
            alpha = 0F
            visibility = View.VISIBLE
            animate()
                .alpha(1F)
                .setStartDelay(500L)
                .setDuration(duracaoLonga)
                .setListener(null)
        }
    }
    private fun recolherToolbar(){
        binding.mainGroupBtnMana.apply{
            animate()
                .alpha(0F)
                .setDuration(duracaoCurta)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.mainGroupBtnMana.visibility = View.GONE
                    }
                })
        }
        binding.mainSearchEditText.apply{
            animate()
                .alpha(0F)
                .setDuration(duracaoMedia)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.mainSearchEditText.visibility = View.GONE
                    }
                })
        }

        val c = ChangeBounds()
        c.setStartDelay(250L)
        c.setInterpolator(AnticipateOvershootInterpolator())
        c.setDuration(duracaoMedia)
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup, c)

        val p = binding.mainToolbar.layoutParams
        p.height = (resources.displayMetrics.density * 64).toInt()
        binding.mainToolbar.layoutParams = p
        binding.mainBtnSearch.apply{
            alpha = 0F
            visibility = View.VISIBLE
            animate()
                .alpha(1F)
                .setDuration(duracaoMedia)
                .setListener(null)
        }
    }
    private fun abrirTeclado(){
        imm.showSoftInput(binding.mainSearchEditText, 0)
    }
    private fun fecharTeclado(v: EditText){
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.main_btn_search -> {
                    if(!viewModel.isExpanded){
                        expandirToolbar()
                    }
                    viewModel.isExpanded = !viewModel.isExpanded
                }
                R.id.main_btn_white -> {
                    viewModel.whiteSelected = !viewModel.whiteSelected
                    if(viewModel.whiteSelected){
                        binding.mainBtnWhite.setBackgroundResource(R.drawable.bg_btn_white_selected)
                        binding.mainBtnWhite.setColorFilter(resources.getColor(R.color.white_mana))
                        if(viewModel.blueSelected){
                            viewModel.blueSelected = false
                            binding.mainBtnBlue.background = null
                            binding.mainBtnBlue.setColorFilter(null)
                        }
                        if(viewModel.greenSelected){
                            viewModel.greenSelected = false
                            binding.mainBtnGreen.background = null
                            binding.mainBtnGreen.setColorFilter(null)
                        }
                        if(viewModel.redSelected){
                            viewModel.redSelected = false
                            binding.mainBtnRed.background = null
                            binding.mainBtnRed.setColorFilter(null)
                        }
                        if(viewModel.blackSelected){
                            viewModel.blackSelected = false
                            binding.mainBtnBlack.background = null
                            binding.mainBtnBlack.setColorFilter(null)
                        }
                    }
                    else{
                        binding.mainBtnWhite.background = null
                        binding.mainBtnWhite.setColorFilter(null)
                    }
                }
                R.id.main_btn_blue -> {
                    viewModel.blueSelected = !viewModel.blueSelected
                    if(viewModel.blueSelected){
                        binding.mainBtnBlue.setBackgroundResource(R.drawable.bg_btn_blue_selected)
                        binding.mainBtnBlue.setColorFilter(resources.getColor(R.color.blue_mana))
                        if(viewModel.whiteSelected){
                            viewModel.whiteSelected = false
                            binding.mainBtnWhite.background = null
                            binding.mainBtnWhite.setColorFilter(null)
                        }
                        if(viewModel.greenSelected){
                            viewModel.greenSelected = false
                            binding.mainBtnGreen.background = null
                            binding.mainBtnGreen.setColorFilter(null)
                        }
                        if(viewModel.redSelected){
                            viewModel.redSelected = false
                            binding.mainBtnRed.background = null
                            binding.mainBtnRed.setColorFilter(null)
                        }
                        if(viewModel.blackSelected){
                            viewModel.blackSelected = false
                            binding.mainBtnBlack.background = null
                            binding.mainBtnBlack.setColorFilter(null)
                        }
                    }
                    else{
                        binding.mainBtnBlue.background = null
                        binding.mainBtnBlue.setColorFilter(null)
                    }
                }
                R.id.main_btn_green -> {
                    viewModel.greenSelected = !viewModel.greenSelected
                    if(viewModel.greenSelected){
                        binding.mainBtnGreen.setBackgroundResource(R.drawable.bg_btn_green_selected)
                        binding.mainBtnGreen.setColorFilter(resources.getColor(R.color.green_mana))
                        if(viewModel.whiteSelected){
                            viewModel.whiteSelected = false
                            binding.mainBtnWhite.background = null
                            binding.mainBtnWhite.setColorFilter(null)
                        }
                        if(viewModel.blueSelected){
                            viewModel.blueSelected = false
                            binding.mainBtnBlue.background = null
                            binding.mainBtnBlue.setColorFilter(null)
                        }
                        if(viewModel.redSelected){
                            viewModel.redSelected = false
                            binding.mainBtnRed.background = null
                            binding.mainBtnRed.setColorFilter(null)
                        }
                        if(viewModel.blackSelected){
                            viewModel.blackSelected = false
                            binding.mainBtnBlack.background = null
                            binding.mainBtnBlack.setColorFilter(null)
                        }
                    }
                    else{
                        binding.mainBtnGreen.background = null
                        binding.mainBtnGreen.setColorFilter(null)
                    }
                }
                R.id.main_btn_red -> {
                    viewModel.redSelected = !viewModel.redSelected
                    if(viewModel.redSelected){
                        binding.mainBtnRed.setBackgroundResource(R.drawable.bg_btn_red_selected)
                        binding.mainBtnRed.setColorFilter(resources.getColor(R.color.red_mana))
                        if(viewModel.whiteSelected){
                            viewModel.whiteSelected = false
                            binding.mainBtnWhite.background = null
                            binding.mainBtnWhite.setColorFilter(null)
                        }
                        if(viewModel.blueSelected){
                            viewModel.blueSelected = false
                            binding.mainBtnBlue.background = null
                            binding.mainBtnBlue.setColorFilter(null)
                        }
                        if(viewModel.greenSelected){
                            viewModel.greenSelected = false
                            binding.mainBtnGreen.background = null
                            binding.mainBtnGreen.setColorFilter(null)
                        }
                        if(viewModel.blackSelected){
                            viewModel.blackSelected = false
                            binding.mainBtnBlack.background = null
                            binding.mainBtnBlack.setColorFilter(null)
                        }
                    }
                    else{
                        binding.mainBtnRed.background = null
                        binding.mainBtnRed.setColorFilter(null)
                    }
                }
                R.id.main_btn_black -> {
                    viewModel.blackSelected = !viewModel.blackSelected
                    if(viewModel.blackSelected){
                        binding.mainBtnBlack.setBackgroundResource(R.drawable.bg_btn_black_selected)
                        binding.mainBtnBlack.setColorFilter(resources.getColor(R.color.black_mana))
                        if(viewModel.whiteSelected){
                            viewModel.whiteSelected = false
                            binding.mainBtnWhite.background = null
                            binding.mainBtnWhite.setColorFilter(null)
                        }
                        if(viewModel.blueSelected){
                            viewModel.blueSelected = false
                            binding.mainBtnBlue.background = null
                            binding.mainBtnBlue.setColorFilter(null)
                        }
                        if(viewModel.greenSelected){
                            viewModel.greenSelected = false
                            binding.mainBtnGreen.background = null
                            binding.mainBtnGreen.setColorFilter(null)
                        }
                        if(viewModel.redSelected){
                            viewModel.redSelected = false
                            binding.mainBtnRed.background = null
                            binding.mainBtnRed.setColorFilter(null)
                        }
                    }
                    else{
                        binding.mainBtnBlack.background = null
                        binding.mainBtnBlack.setColorFilter(null)
                    }
                }
            }
        }
    }
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if(v != null){
            when(v.id){
                R.id.main_search_edit_text -> {
                    if(hasFocus){
                        abrirTeclado()
                        viewModel.isExpanded = true
                    }
                    else if(!hasFocus && binding.mainSearchEditText.text.isNullOrBlank()){
                        if(imm.isActive && imm.isAcceptingText){
                            fecharTeclado(binding.mainSearchEditText)
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
                onSearch()
                true
            }
            else -> false
        }
    }
    private fun showLongToast(message:String?){
        Toast.makeText(this, message ?: "NO MESSAGE", Toast.LENGTH_LONG).show()
    }
}