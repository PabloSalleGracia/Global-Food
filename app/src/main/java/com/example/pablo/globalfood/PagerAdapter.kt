package com.example.pablo.globalfood

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter


class PagerAdapter(fm: FragmentManager, private val numOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        return when (position) {
            0 -> {
                FavRecipes()
            }
            1 -> {
                MyRecipes()
            }
            2 -> {
                MainMenuFragment()
            }
            else -> null
        }
    }

    private val tabTitles = arrayOf("Mis recetas", "Recetas fav", "Restaurantes fav")
    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun getCount(): Int {
        return numOfTabs
    }
}