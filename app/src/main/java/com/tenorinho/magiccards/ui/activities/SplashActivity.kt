package com.tenorinho.magiccards.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.tenorinho.magiccards.R

class SplashActivity : AppCompatActivity(){
    private val KEY = "first"
    private var first = true
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
    override fun onResume() {
        super.onResume()
        if(first){
            //Handler().postDelayed({ navigateToShowRandomCardActivity() }, 2000)
            Handler().postDelayed({ navigateToMainActivity() }, 2000)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY, false)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState.containsKey(KEY)){
            first = savedInstanceState.getBoolean(KEY)
        }
    }
    private fun navigateToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    private fun navigateToShowRandomCardActivity(){
        startActivity(Intent(this, ShowRandomCardActivity::class.java))
        finish()
    }
}