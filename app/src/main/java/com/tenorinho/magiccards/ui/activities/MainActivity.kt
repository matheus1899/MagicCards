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
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.Group
import com.tenorinho.magiccards.ui.custom.SearchEditText

class MainActivity : AppCompatActivity(),
    View.OnClickListener,
    TextView.OnEditorActionListener,
    View.OnFocusChangeListener{
    private lateinit var imgBtnSearch: ImageButton
    private lateinit var txtSearch: SearchEditText
    private lateinit var imgMagicLogo: ImageView
    private lateinit var toolbar: Toolbar
    private lateinit var grupo_btn_mana: Group

    private lateinit var btn_mana_white: ImageButton
    private lateinit var btn_mana_blue: ImageButton
    private lateinit var btn_mana_green: ImageButton
    private lateinit var btn_mana_red: ImageButton
    private lateinit var btn_mana_black: ImageButton

    private var whiteSelected : Boolean = false
    private var blueSelected : Boolean = false
    private var greenSelected : Boolean = false
    private var redSelected : Boolean = false
    private var blackSelected : Boolean = false

    private var duracaoCurta:Long = 0L
    private var duracaoMedia:Long = 0L
    private var duracaoLonga:Long = 0L
    private var search_view_hide = true
    private lateinit var root: ViewGroup
    private lateinit var imm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init(){
        getAllNecessaryViews()
        setBehaviors()
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        duracaoCurta = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        duracaoMedia = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        duracaoLonga = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
    }
    private fun getAllNecessaryViews(){
        root = findViewById(R.id.main_root)
        toolbar = findViewById(R.id.main_toolbar)
        imgBtnSearch = findViewById(R.id.main_btn_search)
        txtSearch = findViewById(R.id.main_search_edit_text)
        imgMagicLogo = findViewById(R.id.main_magic_img)
        grupo_btn_mana = findViewById(R.id.main_group_btn_mana)
        btn_mana_white = findViewById(R.id.main_btn_white)
        btn_mana_blue = findViewById(R.id.main_btn_blue)
        btn_mana_green = findViewById(R.id.main_btn_green)
        btn_mana_red = findViewById(R.id.main_btn_red)
        btn_mana_black = findViewById(R.id.main_btn_black)
    }
    private fun setBehaviors(){
        btn_mana_white.setOnClickListener(this)
        btn_mana_blue.setOnClickListener(this)
        btn_mana_green.setOnClickListener(this)
        btn_mana_red.setOnClickListener(this)
        btn_mana_black.setOnClickListener(this)
        imgBtnSearch.setOnClickListener(this)
        txtSearch.setOnEditorActionListener(this)
        txtSearch.setOnFocusChangeListener(this)
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
                txtSearch.requestFocus()
            }
            else{
                search_view_hide = true
            }
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
    private fun expandirToolbar(){
        txtSearch.apply{
            alpha = 0F
            visibility = View.VISIBLE
            animate()
                .alpha(1F)
                .setDuration(duracaoMedia)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        txtSearch.requestFocus()
                    }
                })
        }
        val c = ChangeBounds()
        c.setStartDelay(250L)
        c.setInterpolator(AnticipateOvershootInterpolator())
        c.setDuration(duracaoMedia)
        TransitionManager.beginDelayedTransition(root, c)
        val p = toolbar.layoutParams
        p.height = (resources.displayMetrics.density * 118).toInt()
        toolbar.layoutParams = p
        imgBtnSearch
            .animate()
            .alpha(0F)
            .setDuration(duracaoMedia)
            .setListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    imgBtnSearch.visibility = View.GONE
                }
            })

        grupo_btn_mana.apply{
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
        grupo_btn_mana.apply{
            animate()
                .alpha(0F)
                .setDuration(duracaoCurta)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        grupo_btn_mana.visibility = View.GONE
                    }
                })
        }
        txtSearch.apply{
            animate()
                .alpha(0F)
                .setDuration(duracaoMedia)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        txtSearch.visibility = View.GONE
                    }
                })
        }

        val c = ChangeBounds()
        c.setStartDelay(250L)
        c.setInterpolator(AnticipateOvershootInterpolator())
        c.setDuration(duracaoMedia)
        TransitionManager.beginDelayedTransition(root, c)

        val p = toolbar.layoutParams
        p.height = (resources.displayMetrics.density * 64).toInt()
        toolbar.layoutParams = p
        imgBtnSearch.apply{
            alpha = 0F
            visibility = View.VISIBLE
            animate()
                .alpha(1F)
                .setDuration(duracaoMedia)
                .setListener(null)
        }
    }
    private fun abrirTeclado(){
        imm.showSoftInput(txtSearch, 0)
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
                        btn_mana_white.setBackgroundResource(R.drawable.bg_btn_white_selected)
                        btn_mana_white.setColorFilter(resources.getColor(R.color.white_mana))
                        if(blueSelected){
                            blueSelected = false
                            btn_mana_blue.background = null
                            btn_mana_blue.setColorFilter(null)
                        }
                        if(greenSelected){
                            greenSelected = false
                            btn_mana_green.background = null
                            btn_mana_green.setColorFilter(null)
                        }
                        if(redSelected){
                            redSelected = false
                            btn_mana_red.background = null
                            btn_mana_red.setColorFilter(null)
                        }
                        if(blackSelected){
                            blackSelected = false
                            btn_mana_black.background = null
                            btn_mana_black.setColorFilter(null)
                        }
                    }
                    else{
                        btn_mana_white.background = null
                        btn_mana_white.setColorFilter(null)
                    }
                }
                R.id.main_btn_blue -> {
                    blueSelected = !blueSelected
                    if(blueSelected){
                        btn_mana_blue.setBackgroundResource(R.drawable.bg_btn_blue_selected)
                        btn_mana_blue.setColorFilter(resources.getColor(R.color.blue_mana))
                        if(whiteSelected){
                            whiteSelected = false
                            btn_mana_white.background = null
                            btn_mana_white.setColorFilter(null)
                        }
                        if(greenSelected){
                            greenSelected = false
                            btn_mana_green.background = null
                            btn_mana_green.setColorFilter(null)
                        }
                        if(redSelected){
                            redSelected = false
                            btn_mana_red.background = null
                            btn_mana_red.setColorFilter(null)
                        }
                        if(blackSelected){
                            blackSelected = false
                            btn_mana_black.background = null
                            btn_mana_black.setColorFilter(null)
                        }
                    }
                    else{
                        btn_mana_blue.background = null
                        btn_mana_blue.setColorFilter(null)
                    }
                }
                R.id.main_btn_green -> {
                    greenSelected = !greenSelected
                    if(greenSelected){
                        btn_mana_green.setBackgroundResource(R.drawable.bg_btn_green_selected)
                        btn_mana_green.setColorFilter(resources.getColor(R.color.green_mana))
                        if(whiteSelected){
                            whiteSelected = false
                            btn_mana_white.background = null
                            btn_mana_white.setColorFilter(null)
                        }
                        if(blueSelected){
                            blueSelected = false
                            btn_mana_blue.background = null
                            btn_mana_blue.setColorFilter(null)
                        }
                        if(redSelected){
                            redSelected = false
                            btn_mana_red.background = null
                            btn_mana_red.setColorFilter(null)
                        }
                        if(blackSelected){
                            blackSelected = false
                            btn_mana_black.background = null
                            btn_mana_black.setColorFilter(null)
                        }
                    }
                    else{
                        btn_mana_green.background = null
                        btn_mana_green.setColorFilter(null)
                    }
                }
                R.id.main_btn_red -> {
                    redSelected = !redSelected
                    if(redSelected){
                        btn_mana_red.setBackgroundResource(R.drawable.bg_btn_red_selected)
                        btn_mana_red.setColorFilter(resources.getColor(R.color.red_mana))
                        if(whiteSelected){
                            whiteSelected = false
                            btn_mana_white.background = null
                            btn_mana_white.setColorFilter(null)
                        }
                        if(blueSelected){
                            blueSelected = false
                            btn_mana_blue.background = null
                            btn_mana_blue.setColorFilter(null)
                        }
                        if(greenSelected){
                            greenSelected = false
                            btn_mana_green.background = null
                            btn_mana_green.setColorFilter(null)
                        }
                        if(blackSelected){
                            blackSelected = false
                            btn_mana_black.background = null
                            btn_mana_black.setColorFilter(null)
                        }
                    }
                    else{
                        btn_mana_red.background = null
                        btn_mana_red.setColorFilter(null)
                    }
                }
                R.id.main_btn_black -> {
                    blackSelected = !blackSelected
                    if(blackSelected){
                        btn_mana_black.setBackgroundResource(R.drawable.bg_btn_black_selected)
                        btn_mana_black.setColorFilter(resources.getColor(R.color.black_mana))
                        if(whiteSelected){
                            whiteSelected = false
                            btn_mana_white.background = null
                            btn_mana_white.setColorFilter(null)
                        }
                        if(blueSelected){
                            blueSelected = false
                            btn_mana_blue.background = null
                            btn_mana_blue.setColorFilter(null)
                        }
                        if(greenSelected){
                            greenSelected = false
                            btn_mana_green.background = null
                            btn_mana_green.setColorFilter(null)
                        }
                        if(redSelected){
                            redSelected = false
                            btn_mana_red.background = null
                            btn_mana_red.setColorFilter(null)
                        }
                    }
                    else{
                        btn_mana_black.background = null
                        btn_mana_black.setColorFilter(null)
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
                    else if(!hasFocus && txtSearch.text.isNullOrBlank()){
                        if(imm.isActive && imm.isAcceptingText){
                            fecharTeclado(txtSearch)
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