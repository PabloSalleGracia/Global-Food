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
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.example.pablo.globalfood.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*


private var tituloRecRes = "seEnvia"

class MainMenuActivity : AppCompatActivity(), OnButtonPressedListener {

    override fun onItemPressed(text: Any) {
        tituloRecRes = text.toString()
        openDetailRecipes()
    }

    override fun onButtonPressed(text: String) {

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

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

    }

    fun pruebaFirebaseInsert(){
        val db = FirebaseFirestore.getInstance()

        // Add a new document with a generated id.
        val data = HashMap<String, String>()
        data.put("titulo", "TituloFBPrueba")
        data.put("tipo", "PlatoFBPrueba")

        db.collection("Recetas")
                .add(data)
                .addOnSuccessListener { documentReference -> Log.d("MainMenuActivity", "DocumentSnapshot written with ID: " + documentReference.id) }
                .addOnFailureListener { e -> Log.w("MainMenuActivity", "Error adding document", e) }
    }

    fun pruebaFireBaseSelect(){
        val db = FirebaseFirestore.getInstance()

        db.collection("Recetas")
                .whereEqualTo("titulo", "TituloReceta")
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                        if (p1 != null) {
                            Log.w("MainMenuActivity", "Listen failed.", p1)
                            return
                        }


                        val recetas = ArrayList<String?>()
                        if (p0 != null) {
                            for (doc in p0) {
                                if (doc.get("titulo") != null) {
                                    recetas.add(doc.getString("titulo"))
                                }
                            }
                        }
                        Log.d("MainMenuActivity", "Current cites in CA: $recetas")
                    }
                })

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
        val detailActivity = Intent(this, DetailActivity::class.java)
        detailActivity.putExtra("tituloRecRes", tituloRecRes)
        //startActivityForResult(intent2, MainMenuActivity.REQUEST_CODE)
        startActivity(detailActivity)
    }

}
