package com.example.pablo.globalfood.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.pablo.globalfood.Fragments.FavRecipes
import com.example.pablo.globalfood.Fragments.FavRestaurants
import com.example.pablo.globalfood.Fragments.MyRecipes


class PagerAdapter(fm: FragmentManager, private val numOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        return when (position) {
            0 -> {
                MyRecipes()
            }
            1 -> {
                FavRecipes()
            }
            2 -> {
                FavRestaurants()
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