package com.example.pablo.globalfood

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_menu.*

private const val MYRECIPES = "MyRecipes"
private const val FAVRECIPES = "FavRecipes"
private const val SEARCH = "Search"
private const val FAVRESTAURANTS = "FavRestaurants"

class MainMenuActivity : AppCompatActivity(), OnButtonPressedListener {

    override fun onButtonPressed(text: String) {
        when(text){
            MYRECIPES -> openMyRecipes()
            FAVRECIPES -> openRecipesFav()
            SEARCH -> openSearch()
            FAVRESTAURANTS -> openRestaurantFav()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
        //return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_settings -> Toast.makeText(this, "settings", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openMyRecipes(){
        val myRecipes = MyRecipes()
        supportFragmentManager.beginTransaction().replace(R.id.fragment, myRecipes).addToBackStack(null).commit()
    }

    private fun openRecipesFav(){
        val recipesFav = FavRecipes()
        supportFragmentManager.beginTransaction().replace(R.id.fragment, recipesFav).addToBackStack(null).commit()
    }

    private fun openSearch(){
        val search = Search()
        supportFragmentManager.beginTransaction().replace(R.id.fragment, search).addToBackStack(null).commit()
    }

    private fun openRestaurantFav(){
        val resFav = FavRestaurants()
        supportFragmentManager.beginTransaction().replace(R.id.fragment, resFav).addToBackStack(null).commit()
    }

}
