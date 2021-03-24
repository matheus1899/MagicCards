package com.tenorinho.magiccards.ui.activities

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tenorinho.magiccards.R

class ShowCardActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var root:ViewGroup
    private lateinit var txt:TextView
    private lateinit var imgCard: ImageView
    private lateinit var imgCard2: ImageView
    private lateinit var btnCabecaBaixo:Button
    private lateinit var btnMostraAtrasCard:Button
    private var imgCardPivotX:Float = 0F
    private var imgCardPivotY:Float = 0F
    private var isChanged = false
    private var cardLayout:String = ""

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_card)
        txt = findViewById(R.id.txt)
        root = findViewById(R.id.show_card_root)
        imgCard = findViewById(R.id.show_card_img_1)
        imgCard2 = findViewById(R.id.show_card_img_2)
        btnCabecaBaixo = findViewById(R.id.btn_cabeca_baixo)
        btnCabecaBaixo.setOnClickListener(this)
        btnMostraAtrasCard = findViewById(R.id.btn_mostra_atras)
        btnMostraAtrasCard.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.btn_cabeca_baixo -> {
                    cardLayout = "split"
                    animateFlipCard(1000L, false)
                }
                R.id.btn_mostra_atras -> {
                    cardLayout = "transform"
                    animateTransformCard(500L, false)
                }
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isChanged", isChanged)
        outState.putString("cardLayout", cardLayout)
        if(imgCardPivotX != 0F && imgCardPivotY != 0F){
            outState.putFloat("imgCardPivotX", imgCardPivotX)
            outState.putFloat("imgCardPivotY", imgCardPivotY)
        }
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState.containsKey("isChanged")){
            isChanged = savedInstanceState.getBoolean("isChanged")
        }
        if(savedInstanceState.containsKey("cardLayout")){
            cardLayout = savedInstanceState.getString("cardLayout", "")
        }
        if(savedInstanceState.containsKey("imgCardPivotX")){
            imgCardPivotX = savedInstanceState.getFloat("imgCardPivotX")
        }
        if(savedInstanceState.containsKey("imgCardPivotY")){
            imgCardPivotY = savedInstanceState.getFloat("imgCardPivotY")
        }
    }
    override fun onPostResume() {
        super.onPostResume()
        if(isChanged){
            if(cardLayout == "split"){
                animateFlipCard(50L, true)
            }
            if(cardLayout == "transform"){
                animateTransformCard(50L, true)
            }
        }
    }
    private fun animateFlipCard(duration: Long, isResizing: Boolean){
        if(isChanged){
            if(isResizing){
                txt.text = "TRUE"
                rotateToShowFlipCard(duration)
            }
            else{
                isChanged = !isChanged
                txt.text = "FALSE"
                rotateToHideFlipCard(duration)
            }
        }
        else{
            txt.text = "TRUE"
            isChanged = !isChanged
            rotateToShowFlipCard(duration)
        }
    }
    private fun animateTransformCard(duration: Long, isResizing: Boolean){
        if(isChanged){
            if(isResizing){
                txt.text = "TRUE"
                showBackTranformCard(duration)
            }
            else{
                isChanged = !isChanged
                txt.text = "FALSE"
                hideBackTranformCard(duration)
            }
        }
        else{
            isChanged = !isChanged
            txt.text = "TRUE"
            showBackTranformCard(duration)
        }
    }
    private fun rotateToShowFlipCard(duration: Long){
        val a:Animation = AnimationUtils.loadAnimation(this, R.anim.card_flip_one)
        a.duration = duration
        a.setAnimationListener(object:Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                btnCabecaBaixo.setOnClickListener(null)
            }
            override fun onAnimationEnd(p0: Animation?) {
                btnCabecaBaixo.setOnClickListener(this@ShowCardActivity)
            }
            override fun onAnimationRepeat(p0: Animation?) {}
        })
        imgCard.startAnimation(a)
    }
    private fun rotateToHideFlipCard(duration: Long){
        val a:Animation = AnimationUtils.loadAnimation(this, R.anim.card_flip_two)
        a.duration = duration
        a.setAnimationListener(object:Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                btnCabecaBaixo.setOnClickListener(null)
            }
            override fun onAnimationEnd(p0: Animation?) {
                btnCabecaBaixo.setOnClickListener(this@ShowCardActivity)
            }
            override fun onAnimationRepeat(p0: Animation?) {}
        })
        imgCard.startAnimation(a)
    }
    private fun hideBackTranformCard(duration: Long){
        val b:Animator = AnimatorInflater.loadAnimator(this, R.animator.card_transform_reverse_one).apply {
            this.duration = duration
            setTarget(imgCard)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?){}
                override fun onAnimationEnd(p0: Animator?) {
                    btnMostraAtrasCard.setOnClickListener(this@ShowCardActivity)
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        val a:Animator = AnimatorInflater.loadAnimator(this, R.animator.card_transform_reverse_two).apply {
            this.duration = duration
            setTarget(imgCard2)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    btnMostraAtrasCard.setOnClickListener(null)
                }
                override fun onAnimationEnd(p0: Animator?) {
                    imgCard2.visibility = View.GONE
                    imgCard.visibility = View.VISIBLE
                    b.start()
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        a.start()
    }
    private fun showBackTranformCard(duration: Long){
        val b:Animator = AnimatorInflater.loadAnimator(this, R.animator.card_transform_two).apply {
            this.duration = duration
            setTarget(imgCard2)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?){}
                override fun onAnimationEnd(p0: Animator?) {
                    btnMostraAtrasCard.setOnClickListener(this@ShowCardActivity)
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        val a:Animator = AnimatorInflater.loadAnimator(this, R.animator.card_transform_one).apply {
            this.duration = duration
            setTarget(imgCard)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    btnMostraAtrasCard.setOnClickListener(null)
                }
                override fun onAnimationEnd(p0: Animator?) {
                    imgCard2.visibility = View.VISIBLE
                    imgCard.visibility = View.GONE
                    b.start()
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        a.start()
    }
}