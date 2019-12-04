package com.dinodelivery.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar_Colored)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_menu.selectedItemId = R.id.restaurants_item
        navigateToFragment(MapFragment())
        main_menu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.restaurants_item -> {
                    navigateToFragment(MapFragment())
                    true
                }
                R.id.profile_item -> {
                    navigateToFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment, null).commit()
    }

    fun navigateToFragmentAndAddToStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment, null).addToBackStack(null).commit()
    }

}
