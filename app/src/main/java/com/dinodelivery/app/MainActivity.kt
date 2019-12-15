package com.dinodelivery.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dinodelivery.app.fragments.MapFragment
import com.dinodelivery.app.fragments.ProfileFragment
import com.dinodelivery.app.fragments.RestaurantsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar_Colored)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_menu.uncheckAllItems()
        navigateToFragment(MapFragment())
        main_menu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.restaurants_item -> {
                    navigateToFragment(RestaurantsFragment())
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

    fun clearFragments() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            for (index in 0 until supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStack()
            }
        }
    }

    private fun BottomNavigationView.uncheckAllItems() {
        menu.setGroupCheckable(0, true, false)
        for (i in 0 until menu.size()) {
            menu.getItem(i).isChecked = false
        }
        menu.setGroupCheckable(0, true, true)
    }

}
