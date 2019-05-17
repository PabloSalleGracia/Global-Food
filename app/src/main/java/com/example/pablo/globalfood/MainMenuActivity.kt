package com.example.pablo.globalfood

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.*

import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.content_menu.*

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

        //TextView txtMyTextBox = (TextView)getView().findViewById(R.id.my_text_box)

        //ListView() list = this.findViewById<ListView>(R.id.list_item)

        //ListView list = (ListView) this.findViewById<ListView>(R.id.list_item)

        lateinit var listView: ListView



        listView = findViewById(R.id.list_item)

        val listItems = arrayOfNulls<String>(5)

        //listItems = ["hola", "adios"]

        //adapter = ArrayAdapter.createFromResource(this, R.array.pruebaLista, android.R.layout.activity_list_item)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)

        listView.adapter = adapter


        if(savedInstanceState == null){
            val menuFragment = MainMenuFragment()
            supportFragmentManager.beginTransaction().
                    add(R.id.menu_container, menuFragment).
                    commit()
        }

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
        supportFragmentManager.beginTransaction().replace(R.id.menu_container, myRecipes).addToBackStack(null).commit()
    }

    private fun openRecipesFav(){
        val recipesFav = FavRecipes()
        supportFragmentManager.beginTransaction().replace(R.id.menu_container, recipesFav).addToBackStack(null).commit()
    }

    private fun openSearch(){
        val search = Search()
        supportFragmentManager.beginTransaction().replace(R.id.menu_container, search).addToBackStack(null).commit()
    }

    private fun openRestaurantFav(){
        val resFav = FavRestaurants()
        supportFragmentManager.beginTransaction().replace(R.id.menu_container, resFav).addToBackStack(null).commit()
    }

}
