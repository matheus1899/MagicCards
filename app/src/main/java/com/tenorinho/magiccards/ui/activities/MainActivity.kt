package com.tenorinho.magiccards.ui.activities

import android.os.Bundle
import android.view.Menu
import com.tenorinho.magiccards.R
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.marginBottom

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val t = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(t)
    }
}