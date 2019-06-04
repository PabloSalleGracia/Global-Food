package com.example.pablo.globalfood.Activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.pablo.globalfood.*
import com.example.pablo.globalfood.Adapters.PagerAdapter
import com.example.pablo.globalfood.Fragments.*

import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu.view.*

private const val DETAILRECIPES = "ListaMyRecipes"
private var datosEnviados = "seEnvia"

class MainMenuActivity : AppCompatActivity(), OnButtonPressedListener {
    override fun onItemPressed(text: Any) {

            datosEnviados = text.toString()

        //recibes el titulo y lo pasas al
    }

    override fun onButtonPressed(text: String) {
        when(text){
            DETAILRECIPES -> {
                val intent2 = Intent(this, DetailActivity::class.java)
                intent2.putExtra("id", datosEnviados)
                //startActivityForResult(intent2, MainMenuActivity.REQUEST_CODE)
                startActivity(intent2)
            }
                //openDetailRecipes()
        }
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
        /*fun onTabSelected(tab: TabLayout.Tab) {
            viewPager.setCurrentItem(tab.position)
        }*/
        //tabLayout.addOnTabSelectedListener(onTabSelected(TabLayout(context.tab) tab))
        //viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

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


    private fun openDetailRecipes(){


        /*val detRecip = RecipesDetail.newInstance(datosEnviados)
        supportFragmentManager.beginTransaction().replace(R.id.menu_container, detRecip).addToBackStack(null).commit()*/
    }

}
