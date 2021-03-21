package com.tenorinho.magiccards.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText

class SearchEditText : AppCompatEditText {
    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet):super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int):super(context, attrs, defStyleAttr)

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if(event != null){
            if(event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if(imm.isActive && imm.isAcceptingText){
                    this.clearFocus()
                }
            }
        }
        return super.onKeyPreIme(keyCode, event)
    }
}