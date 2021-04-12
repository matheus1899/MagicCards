package com.tenorinho.magiccards.ui.activities

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tenorinho.magiccards.App
import com.tenorinho.magiccards.R
import com.tenorinho.magiccards.data.models.domain.CardLayout
import com.tenorinho.magiccards.data.viewmodel.ShowRandomCardViewModel
import com.tenorinho.magiccards.data.viewmodel.ShowRandomCardViewModelFactory
import com.tenorinho.magiccards.databinding.ActivityShowRandomCardBinding

class ShowRandomCardActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel:ShowRandomCardViewModel
    private lateinit var binding:ActivityShowRandomCardBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_random_card)
        init()
    }
    private fun init(){
        getViewModel()
        binding.viewModel = viewModel
        binding.showRandomCardBtnLoadRandom.setOnClickListener(this)
        binding.showRandomCardBtnRotate.setOnClickListener(this)
        binding.showRandomCardBtnSaveCard.setOnClickListener(this)
        viewModel.error.observe(this, Observer{showLongToast(it.message)})
        viewModel.textName.observe(this, Observer{binding.showRandomCardTxtName.text = it})
        viewModel.textOracle.observe(this, Observer{binding.showRandomCardTxtOracle.text = it})
        viewModel.textTypeLine.observe(this, Observer{binding.showRandomCardTxtTypeline.text = it})
        viewModel.textPowerToughness.observe(this, Observer{binding.showRandomCardTxtPowerToughness.text = it})
        viewModel.uriImg1.observe(this, Observer { loadImages(binding.showRandomCardImg1, it) })
        viewModel.uriImg2.observe(this, Observer { loadImages(binding.showRandomCardImg2, it) })
        viewModel.cardRotation.observe(this, Observer { binding.showRandomCardImg1.rotation = it })
        viewModel.buttonsIsEnabled.observe(this, Observer { setButtonsIsEnabled(binding.showRandomCardBtnLoadRandom, binding.showRandomCardBtnSaveCard, it = it!!) })
        viewModel.progressBarVisibility.observe(this, Observer { showOrHideView(binding.showRandomCardProgressBar, it) })
        viewModel.isTwoFacedOrFlip.observe(this, Observer { showOrHideView(binding.showRandomCardBtnRotate, it) })
    }
    private fun setButtonsIsEnabled(vararg btn:View, it: Boolean) {
        for(i in btn){
            i.isEnabled = it
        }
    }
    private fun loadImages(imgView: ImageView, it:String?) {
        if(it != null && it.isNotEmpty()){
            Glide
                .with(this)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_no_card_image)
                .into(imgView)
        }
        else{
            imgView.setImageResource(R.drawable.ic_no_card_image)
        }
    }
    private fun getViewModel() {
        val app = application as App
        val factory = ShowRandomCardViewModelFactory(app.cardRepository)
        viewModel = ViewModelProvider(this, factory).get(ShowRandomCardViewModel::class.java)
    }
    private fun showOrHideView(v:View, b:Boolean){
        if(b){
           v.visibility = View.VISIBLE
        }
        else{
            v.visibility = View.GONE
        }
    }
    private fun showLongToast(message:String?){
        Toast.makeText(this, message ?: "NO MESSAGE", Toast.LENGTH_LONG).show()
    }
    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.show_random_card_btn_load_random -> {
                    if(viewModel.isCardChanged){
                        if(viewModel.randomCard.value!!.layout == CardLayout.FLIP){
                            rotateToHideFlipCard(100)
                        }
                        if(viewModel.randomCard.value!!.layout == CardLayout.DOUBLE_FACED_TOKEN ||
                            viewModel.randomCard.value!!.layout == CardLayout.TRANSFORM ||
                            viewModel.randomCard.value!!.layout == CardLayout.MODAL_DFC){
                            binding.showRandomCardImg1.rotationY = 0F
                            binding.showRandomCardImg1.visibility = View.VISIBLE
                            binding.showRandomCardImg2.visibility = View.GONE
                            binding.showRandomCardImg2.rotationY = 90F
                        }
                        viewModel.isCardChanged = false
                    }
                    viewModel.loadRandomCard()
                }
                R.id.show_random_card_btn_save_card ->{
                    viewModel.saveCard()
                }
                R.id.show_random_card_btn_rotate -> {
                    rotateOrTransformCard()
                    viewModel.flipCardHasRotate()
                }
            }
        }
    }
    private fun rotateOrTransformCard() {
        if(viewModel.randomCard.value != null){
            if(viewModel.randomCard.value!!.layout == CardLayout.FLIP){
                animateFlipCard(1000L, false)
            }
            if(viewModel.randomCard.value!!.layout == CardLayout.DOUBLE_FACED_TOKEN ||
                viewModel.randomCard.value!!.layout == CardLayout.TRANSFORM ||
                viewModel.randomCard.value!!.layout == CardLayout.MODAL_DFC){
                animateTransformCard(500L, false)
            }
        }

    }
    override fun onPostResume() {
        super.onPostResume()
        if(viewModel.isCardChanged){
            if(viewModel.randomCard.value?.layout == CardLayout.FLIP){
                animateFlipCard(50L, true)
                viewModel.flipCardHasRotate()
            }
            if(viewModel.randomCard.value?.layout == CardLayout.TRANSFORM ||
                viewModel.randomCard.value?.layout == CardLayout.MODAL_DFC ||
                viewModel.randomCard.value?.layout == CardLayout.DOUBLE_FACED_TOKEN){
                animateTransformCard(50L, true)
            }
        }
    }
    private fun animateFlipCard(duration: Long, isResizing: Boolean){
        if(viewModel.isCardChanged){
            if(isResizing){
                rotateToShowFlipCard(duration)
            }
            else{
                viewModel.isCardChanged = !viewModel.isCardChanged
                rotateToHideFlipCard(duration)
            }
        }
        else{
            viewModel.isCardChanged = !viewModel.isCardChanged
            rotateToShowFlipCard(duration)
        }
    }
    private fun animateTransformCard(duration: Long, isResizing: Boolean){
        if(viewModel.isCardChanged){
            if(isResizing){
                showBackTranformCard(duration)
            }
            else{
                viewModel.isCardChanged = !viewModel.isCardChanged
                hideBackTranformCard(duration)
            }
        }
        else{
            viewModel.isCardChanged = !viewModel.isCardChanged
            showBackTranformCard(duration)
        }
    }
    private fun rotateToShowFlipCard(duration: Long){
        val a: Animation = AnimationUtils.loadAnimation(this, R.anim.card_flip_one)
        a.duration = duration
        a.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                binding.showRandomCardBtnRotate.setOnClickListener(null)
            }
            override fun onAnimationEnd(p0: Animation?) {
                binding.showRandomCardBtnRotate.setOnClickListener(this@ShowRandomCardActivity)
            }
            override fun onAnimationRepeat(p0: Animation?) {}
        })
        binding.showRandomCardImg1.startAnimation(a)
    }
    private fun rotateToHideFlipCard(duration: Long){
        val a: Animation = AnimationUtils.loadAnimation(this, R.anim.card_flip_two)
        a.duration = duration
        a.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                binding.showRandomCardBtnRotate.setOnClickListener(null)
            }
            override fun onAnimationEnd(p0: Animation?) {
                binding.showRandomCardBtnRotate.setOnClickListener(this@ShowRandomCardActivity)
            }
            override fun onAnimationRepeat(p0: Animation?) {}
        })
        binding.showRandomCardImg1.startAnimation(a)
    }
    private fun hideBackTranformCard(duration: Long){
        val b: Animator = AnimatorInflater.loadAnimator(this, R.animator.card_transform_reverse_one).apply {
            this.duration = duration
            setTarget(binding.showRandomCardImg1)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?){}
                override fun onAnimationEnd(p0: Animator?) {
                    binding.showRandomCardBtnRotate.setOnClickListener(this@ShowRandomCardActivity)
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        val a: Animator = AnimatorInflater.loadAnimator(this, R.animator.card_transform_reverse_two).apply {
            this.duration = duration
            setTarget(binding.showRandomCardImg2)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    binding.showRandomCardBtnRotate.setOnClickListener(null)
                }
                override fun onAnimationEnd(p0: Animator?) {
                    binding.showRandomCardImg2.visibility = View.GONE
                    binding.showRandomCardImg1.visibility = View.VISIBLE
                    b.start()
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        a.start()
    }
    private fun showBackTranformCard(duration: Long){
        val b: Animator = AnimatorInflater.loadAnimator(this, R.animator.card_transform_two).apply {
            this.duration = duration
            setTarget(binding.showRandomCardImg2)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?){}
                override fun onAnimationEnd(p0: Animator?) {
                    binding.showRandomCardBtnRotate.setOnClickListener(this@ShowRandomCardActivity)
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        val a: Animator = AnimatorInflater.loadAnimator(this, R.animator.card_transform_one).apply {
            this.duration = duration
            setTarget(binding.showRandomCardImg1)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    binding.showRandomCardBtnRotate.setOnClickListener(null)
                }
                override fun onAnimationEnd(p0: Animator?) {
                    binding.showRandomCardImg2.visibility = View.VISIBLE
                    binding.showRandomCardImg1.visibility = View.GONE
                    b.start()
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        a.start()
    }
}