package com.tenorinho.magiccards.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tenorinho.magiccards.R

class BindAdapter {
    companion object{
        @BindingAdapter("onClick")
        @JvmStatic fun onClick(view: View, onClick:() -> Unit){
            view.setOnClickListener{ onClick() }
        }
        @BindingAdapter("visibilityByBool")
        @JvmStatic fun visibilityByBoolean(view:View, b:Boolean){
            view.visibility = if(b) View.VISIBLE else View.GONE
        }
        @BindingAdapter("app:srcByString")
        @JvmStatic fun srcByString(img:ImageView, uri:String?){
            if(uri.isNullOrBlank()){
                img.setImageResource(R.drawable.ic_no_card_image)
            }
            else{
                Glide
                    .with(img.context)
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_no_card_image)
                    .into(img)
            }
        }
        @BindingAdapter("visibilityByString")
        @JvmStatic fun visibilityByString(view: TextView, txt:String?){
            if(txt.isNullOrEmpty() || txt.isNullOrBlank()){
                view.visibility = View.GONE
            }
            else{
                view.visibility = View.VISIBLE
                view.text = txt
            }
        }
    }
}