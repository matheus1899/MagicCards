package com.tenorinho.magiccards.ui.custom

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.tenorinho.magiccards.R

class ManaIconTextView : AppCompatTextView {
    constructor(context: Context):super(context)
    constructor(context:Context, attrs: AttributeSet):super(context, attrs)
    constructor(context:Context, attrs:AttributeSet, defStyleAttr:Int):super(context, attrs, defStyleAttr)

    fun setTextAndAddIcons(text:String){
        val span = getSpannableString(text)
        val regex = Regex("([{][A-Z0-9½∞]+[{/}]?[A-Z]?)[}]")
        regex.findAll(text, 0).iterator().forEach {
            val match = it.value.replace('{',' ').replace('}',' ').trim()
            addImageSpan(match, it, span)
        }
        this.text = span
    }
    private fun addImageSpan(match:String, it:MatchResult, span: SpannableString){
        when(match){
            "W" -> {
                val d = ResourcesCompat.getDrawable(resources, R.drawable.ic_white_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "U" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_blue_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "G" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_green_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "R" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_red_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "B" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_black_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "T" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_tap, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "Q" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_untap, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "E" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_energy_counter, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "PW" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_planeswalker, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "CHAOS" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_chaos, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "A" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_acorn_counter, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "X" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_x_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "Y" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_y_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "Z" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_z_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "0" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_zero_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "∞" -> {
                val d = ResourcesCompat.getDrawable(resources, R.drawable.ic_infinity_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "½" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_half_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.first, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "1" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "2" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_two_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "3" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_three_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "4" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_four_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "5" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_five_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "6" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_six_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "7" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_seven_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "8" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_eight_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "9" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_nine_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "10" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_ten_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "11" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_eleven_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "12" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_twelve_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "13" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_thirteen_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "14" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_fourteen_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "15" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_fifteen_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "16" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_sixteen_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "17" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_seventeen_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "18" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_eighteen_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "19" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_nineteen_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "20" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_twenty_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "100" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_hundred_generic_mana, null)
                d!!.setBounds(0, 0, (this.lineHeight*1.8).toInt(), this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "1000000" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_million_generic_mana, null)
                d!!.setBounds(0, 0, this.lineHeight*5, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "W/U" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_white_or_blue_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "W/B" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_white_or_black_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "B/R" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_black_or_red_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "B/G" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_black_or_green_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "U/B" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_blue_or_black_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "U/R" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_blue_or_red_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "R/G" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_red_or_green_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "R/W" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_red_or_white_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "G/W" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_green_or_white_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "G/U" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_green_or_blue_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "2/W" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_two_generic_or_one_white, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "2/U" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_two_generic_or_one_blue, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "2/G" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_two_generic_or_one_green, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "2/R" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_two_generic_or_one_red, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "2/B" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_two_generic_or_one_black, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "P" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_colored_mana_or_two_life, null)
                d!!.setBounds(0, 0, this.lineHeight, (this.lineHeight*1.5).toInt())
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "W/P" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_white_mana_or_two_life, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "U/P" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_blue_mana_or_two_life, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "G/P" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_green_mana_or_two_life, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "R/P" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_red_mana_or_two_life, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "B/P" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_black_mana_or_two_life, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "HW" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_one_half_white_mana, null)
                d!!.setBounds(0, 0, (this.lineHeight/2).toInt(), this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "HR" -> {
                val d = ResourcesCompat.getDrawable(resources, R.drawable.ic_one_half_red_mana, null)
                d!!.setBounds(0, 0, (this.lineHeight/2).toInt(), this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "C" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_colorless_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
            "S" -> {
                val d = ResourcesCompat.getDrawable(resources,R.drawable.ic_snow_mana, null)
                d!!.setBounds(0, 0, this.lineHeight, this.lineHeight)
                span.setSpan(ImageSpan(d!!), it.range.start, it.range.last+1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
        }
    }
    private fun getSpannableString(texto:String):SpannableString{
        if(texto.length > 24){
            val r = Regex("[}]")
            var i = 0
            var t1 = ""
            r.findAll(texto, 0).iterator().forEach {
                if(i == 7){
                    t1 = texto.subSequence(0,it.range.last).toString()
                    t1 += "\n"
                    t1 += texto.subSequence(it.range.last+1, texto.length)
                }
                i++
            }
            if(t1.isNotEmpty()){
                return SpannableString(t1)
            }
            else{
                return SpannableString(texto)
            }
        }
        else{
            return SpannableString(texto)
        }
    }
}