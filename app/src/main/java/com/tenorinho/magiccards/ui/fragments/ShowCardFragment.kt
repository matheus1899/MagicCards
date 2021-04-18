package com.tenorinho.magiccards.ui.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tenorinho.magiccards.App
import com.tenorinho.magiccards.R
import com.tenorinho.magiccards.data.models.domain.CardLayout
import com.tenorinho.magiccards.data.viewmodel.MainViewModel
import com.tenorinho.magiccards.databinding.FragmentShowCardBinding

class ShowCardFragment : Fragment(), View.OnClickListener{
    private lateinit var binding:FragmentShowCardBinding
    lateinit var viewModel: MainViewModel

    companion object{
        fun newInstance():ShowCardFragment{
            return ShowCardFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_card, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.showCardToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.activity_main_title)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as App).appContainer.inject(this, requireActivity() as AppCompatActivity)
        val args = arguments
        if(args != null && args.containsKey("card_position")){
            viewModel.setSelectedCard(args.getInt("card_position"))
        }
        binding.viewModel = viewModel
        binding.showCardBtnLoad.setOnClickListener(this)
        binding.showCardBtnRotate.setOnClickListener(this)
        binding.showCardBtnSave.setOnClickListener(this)
        viewModel.error.observe(viewLifecycleOwner, Observer{showLongToast(it.message)})
        viewModel.textName.observe(viewLifecycleOwner, Observer{binding.showCardTxtName.text = it})
        viewModel.textOracle.observe(viewLifecycleOwner, Observer{binding.showCardTxtOracle.text = it})
        viewModel.textTypeLine.observe(viewLifecycleOwner, Observer{binding.showCardTxtTypeline.text = it})
        viewModel.textPowerToughness.observe(viewLifecycleOwner, Observer{binding.showCardTxtPowerToughness.text = it})
        viewModel.uriImg1.observe(viewLifecycleOwner, Observer{loadImages(binding.showCardImg1, it) })
        viewModel.uriImg2.observe(viewLifecycleOwner, Observer{loadImages(binding.showCardImg2, it) })
        viewModel.cardRotation.observe(viewLifecycleOwner, Observer{binding.showCardImg1.rotation = it })
        viewModel.buttonsIsEnabled.observe(viewLifecycleOwner, Observer{setButtonsIsEnabled(binding.showCardBtnLoad, binding.showCardBtnSave, it = it!!) })
        viewModel.isTwoFacedOrFlip.observe(viewLifecycleOwner, Observer{showOrHideView(binding.showCardBtnRotate, it) })
    }
    override fun onResume() {
        super.onResume()
        if(viewModel.isCardChanged){
            if(viewModel.selectedCard.value?.layout == CardLayout.FLIP){
                animateFlipCard(50L, true)
                viewModel.flipCardHasRotate()
            }
            if(viewModel.selectedCard.value?.layout == CardLayout.TRANSFORM ||
                viewModel.selectedCard.value?.layout == CardLayout.MODAL_DFC ||
                viewModel.selectedCard.value?.layout == CardLayout.DOUBLE_FACED_TOKEN){
                animateTransformCard(50L, true)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.isCardChanged = false
        viewModel.selectedCard.value = null
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
    private fun showOrHideView(v:View, b:Boolean){
        if(b){
            v.visibility = View.VISIBLE
        }
        else{
            v.visibility = View.GONE
        }
    }
    private fun showLongToast(message:String?){
        if(message.isNullOrEmpty()){
            return
        }
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.show_card_btn_load -> {
                    if(viewModel.isCardChanged){
                        if(viewModel.selectedCard.value!!.layout == CardLayout.FLIP){
                            rotateToHideFlipCard(100)
                        }
                        if(viewModel.selectedCard.value!!.layout == CardLayout.DOUBLE_FACED_TOKEN ||
                            viewModel.selectedCard.value!!.layout == CardLayout.TRANSFORM ||
                            viewModel.selectedCard.value!!.layout == CardLayout.MODAL_DFC){
                            binding.showCardImg1.rotationY = 0F
                            binding.showCardImg1.visibility = View.VISIBLE
                            binding.showCardImg2.visibility = View.GONE
                            binding.showCardImg2.rotationY = 90F
                        }
                        viewModel.isCardChanged = false
                    }
                    viewModel.loadRandomCard()
                }
                R.id.show_card_btn_save ->{
                    viewModel.saveCard()
                }
                R.id.show_card_btn_rotate -> {
                    rotateOrTransformCard()
                    viewModel.flipCardHasRotate()
                }
            }
        }
    }
    private fun rotateOrTransformCard() {
        if(viewModel.selectedCard.value != null){
            if(viewModel.selectedCard.value!!.layout == CardLayout.FLIP){
                animateFlipCard(1000L, false)
            }
            if(viewModel.selectedCard.value!!.layout == CardLayout.DOUBLE_FACED_TOKEN ||
                viewModel.selectedCard.value!!.layout == CardLayout.TRANSFORM ||
                viewModel.selectedCard.value!!.layout == CardLayout.MODAL_DFC){
                animateTransformCard(500L, false)
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
        val a: Animation = AnimationUtils.loadAnimation(activity, R.anim.card_flip_one)
        a.duration = duration
        a.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                binding.showCardBtnRotate.setOnClickListener(null)
            }
            override fun onAnimationEnd(p0: Animation?) {
                binding.showCardBtnRotate.setOnClickListener(this@ShowCardFragment)
            }
            override fun onAnimationRepeat(p0: Animation?) {}
        })
        binding.showCardImg1.startAnimation(a)
    }
    private fun rotateToHideFlipCard(duration: Long){
        val a: Animation = AnimationUtils.loadAnimation(activity, R.anim.card_flip_two)
        a.duration = duration
        a.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                binding.showCardBtnRotate.setOnClickListener(null)
            }
            override fun onAnimationEnd(p0: Animation?) {
                binding.showCardBtnRotate.setOnClickListener(this@ShowCardFragment)
            }
            override fun onAnimationRepeat(p0: Animation?) {}
        })
        binding.showCardImg1.startAnimation(a)
    }
    private fun hideBackTranformCard(duration: Long){
        val b: Animator = AnimatorInflater.loadAnimator(activity, R.animator.card_transform_reverse_one).apply {
            this.duration = duration
            setTarget(binding.showCardImg1)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?){}
                override fun onAnimationEnd(p0: Animator?) {
                    binding.showCardBtnRotate.setOnClickListener(this@ShowCardFragment)
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        val a: Animator = AnimatorInflater.loadAnimator(activity, R.animator.card_transform_reverse_two).apply {
            this.duration = duration
            setTarget(binding.showCardImg2)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    binding.showCardBtnRotate.setOnClickListener(null)
                }
                override fun onAnimationEnd(p0: Animator?) {
                    binding.showCardImg2.visibility = View.GONE
                    binding.showCardImg1.visibility = View.VISIBLE
                    b.start()
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        a.start()
    }
    private fun showBackTranformCard(duration: Long){
        val b: Animator = AnimatorInflater.loadAnimator(activity, R.animator.card_transform_two).apply {
            this.duration = duration
            setTarget(binding.showCardImg2)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?){}
                override fun onAnimationEnd(p0: Animator?) {
                    binding.showCardBtnRotate.setOnClickListener(this@ShowCardFragment)
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        val a: Animator = AnimatorInflater.loadAnimator(activity, R.animator.card_transform_one).apply {
            this.duration = duration
            setTarget(binding.showCardImg1)
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    binding.showCardBtnRotate.setOnClickListener(null)
                }
                override fun onAnimationEnd(p0: Animator?) {
                    binding.showCardImg2.visibility = View.VISIBLE
                    binding.showCardImg1.visibility = View.GONE
                    b.start()
                }
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
        }
        a.start()
    }
}