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
import com.tenorinho.magiccards.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
    View.OnClickListener,
    TextView.OnEditorActionListener,
    View.OnFocusChangeListener{
    private lateinit var binding:ActivityMainBinding

    private var whiteSelected : Boolean = false
    private var blueSelected : Boolean = false
    private var greenSelected : Boolean = false
    private var redSelected : Boolean = false
    private var blackSelected : Boolean = false

    private var duracaoCurta:Long = 0L
    private var duracaoMedia:Long = 0L
    private var duracaoLonga:Long = 0L
    private var search_view_hide = true
    private lateinit var imm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        setBehaviors()
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        duracaoCurta = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        duracaoMedia = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        duracaoLonga = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
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
        Toast.makeText(this, "onSearch", Toast.LENGTH_SHORT).show()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("toolbar_is_expand", !search_view_hide)
        super.onSaveInstanceState(outState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        if(savedInstanceState.containsKey("toolbar_is_expand")){
            if(savedInstanceState.getBoolean("toolbar_is_expand")){
                search_view_hide = false
                expandirToolbar()
                binding.mainSearchEditText.requestFocus()
            }
            else{
                search_view_hide = true
            }
        }
        super.onRestoreInstanceState(savedInstanceState)
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
                    if(search_view_hide){
                        expandirToolbar()
                    }
                    search_view_hide = false
                }
                R.id.main_btn_white -> {
                    whiteSelected = !whiteSelected
                    if(whiteSelected){
                        binding.mainBtnWhite.setBackgroundResource(R.drawable.bg_btn_white_selected)
                        binding.mainBtnWhite.setColorFilter(resources.getColor(R.color.white_mana))
                        if(blueSelected){
                            blueSelected = false
                            binding.mainBtnBlue.background = null
                            binding.mainBtnBlue.setColorFilter(null)
                        }
                        if(greenSelected){
                            greenSelected = false
                            binding.mainBtnGreen.background = null
                            binding.mainBtnGreen.setColorFilter(null)
                        }
                        if(redSelected){
                            redSelected = false
                            binding.mainBtnRed.background = null
                            binding.mainBtnRed.setColorFilter(null)
                        }
                        if(blackSelected){
                            blackSelected = false
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
                    blueSelected = !blueSelected
                    if(blueSelected){
                        binding.mainBtnBlue.setBackgroundResource(R.drawable.bg_btn_blue_selected)
                        binding.mainBtnBlue.setColorFilter(resources.getColor(R.color.blue_mana))
                        if(whiteSelected){
                            whiteSelected = false
                            binding.mainBtnWhite.background = null
                            binding.mainBtnWhite.setColorFilter(null)
                        }
                        if(greenSelected){
                            greenSelected = false
                            binding.mainBtnGreen.background = null
                            binding.mainBtnGreen.setColorFilter(null)
                        }
                        if(redSelected){
                            redSelected = false
                            binding.mainBtnRed.background = null
                            binding.mainBtnRed.setColorFilter(null)
                        }
                        if(blackSelected){
                            blackSelected = false
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
                    greenSelected = !greenSelected
                    if(greenSelected){
                        binding.mainBtnGreen.setBackgroundResource(R.drawable.bg_btn_green_selected)
                        binding.mainBtnGreen.setColorFilter(resources.getColor(R.color.green_mana))
                        if(whiteSelected){
                            whiteSelected = false
                            binding.mainBtnWhite.background = null
                            binding.mainBtnWhite.setColorFilter(null)
                        }
                        if(blueSelected){
                            blueSelected = false
                            binding.mainBtnBlue.background = null
                            binding.mainBtnBlue.setColorFilter(null)
                        }
                        if(redSelected){
                            redSelected = false
                            binding.mainBtnRed.background = null
                            binding.mainBtnRed.setColorFilter(null)
                        }
                        if(blackSelected){
                            blackSelected = false
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
                    redSelected = !redSelected
                    if(redSelected){
                        binding.mainBtnRed.setBackgroundResource(R.drawable.bg_btn_red_selected)
                        binding.mainBtnRed.setColorFilter(resources.getColor(R.color.red_mana))
                        if(whiteSelected){
                            whiteSelected = false
                            binding.mainBtnWhite.background = null
                            binding.mainBtnWhite.setColorFilter(null)
                        }
                        if(blueSelected){
                            blueSelected = false
                            binding.mainBtnBlue.background = null
                            binding.mainBtnBlue.setColorFilter(null)
                        }
                        if(greenSelected){
                            greenSelected = false
                            binding.mainBtnGreen.background = null
                            binding.mainBtnGreen.setColorFilter(null)
                        }
                        if(blackSelected){
                            blackSelected = false
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
                    blackSelected = !blackSelected
                    if(blackSelected){
                        binding.mainBtnBlack.setBackgroundResource(R.drawable.bg_btn_black_selected)
                        binding.mainBtnBlack.setColorFilter(resources.getColor(R.color.black_mana))
                        if(whiteSelected){
                            whiteSelected = false
                            binding.mainBtnWhite.background = null
                            binding.mainBtnWhite.setColorFilter(null)
                        }
                        if(blueSelected){
                            blueSelected = false
                            binding.mainBtnBlue.background = null
                            binding.mainBtnBlue.setColorFilter(null)
                        }
                        if(greenSelected){
                            greenSelected = false
                            binding.mainBtnGreen.background = null
                            binding.mainBtnGreen.setColorFilter(null)
                        }
                        if(redSelected){
                            redSelected = false
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
                        search_view_hide = false
                    }
                    else if(!hasFocus && binding.mainSearchEditText.text.isNullOrBlank()){
                        if(imm.isActive && imm.isAcceptingText){
                            fecharTeclado(binding.mainSearchEditText)
                        }
                        recolherToolbar()
                        search_view_hide = true
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
}