package com.example.pablo.globalfood.Activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.pablo.globalfood.Adapters.PagerAdapter
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import kotlinx.android.synthetic.main.activity_menu.*


private const val SEARCH = "SEARCH"
private var tituloRecRes = "seEnviaTitulo"
private var tipoRecRes = "seEnviaTipo"
private var botonFav = "seEnviaFav"

class MainMenuActivity : AppCompatActivity(), OnButtonPressedListener {

    override fun onItemPressed(titulo: String, tipo: String, fav:String) {
        tituloRecRes = titulo
        tipoRecRes = tipo
        botonFav = fav
        openDetailRecipes()
    }

    override fun onButtonPressed(text: String) {
            val addNewRecipe = Intent(this, AddNewRecipeActivity::class.java)
            addNewRecipe.putExtra("palabraBusqueda", SEARCH)
            startActivity(addNewRecipe)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)

        val viewPager:ViewPager = findViewById(R.id.pager)
        val tabLayout:TabLayout = findViewById(R.id.tab_layout)
        val tabsAdapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount)

        viewPager.adapter = tabsAdapter

        tabLayout.setupWithViewPager(viewPager)

        fab.setOnClickListener {
            openAddNewRecipeActivity()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_settings -> Toast.makeText(this, "settings", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun openDetailRecipes(){
        val detailActivity = Intent(this, DetailActivity::class.java)
        detailActivity.putExtra("tituloRecRes", tituloRecRes)
        detailActivity.putExtra("tipoRecRes", tipoRecRes)
        detailActivity.putExtra("botonFav", botonFav)
        startActivity(detailActivity)
    }


    private fun openAddNewRecipeActivity(){
        val addNewRecipe = Intent(this, AddNewRecipeActivity::class.java)
        addNewRecipe.putExtra("addRecipe", "abrirAdd")
        startActivity(addNewRecipe)
    }

}
