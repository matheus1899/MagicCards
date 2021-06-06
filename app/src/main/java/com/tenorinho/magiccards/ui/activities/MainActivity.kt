package com.tenorinho.magiccards.ui.activities

import android.os.Bundle
import com.tenorinho.magiccards.R
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation

class MainActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.main_nav_host).navigateUp()
                || super.onSupportNavigateUp()
    }
}