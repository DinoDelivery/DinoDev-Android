package com.dinodelivery.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar_Colored)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
