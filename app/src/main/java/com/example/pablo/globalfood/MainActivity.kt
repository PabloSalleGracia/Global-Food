package com.example.pablo.globalfood

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), LoginFragment.OnButtonPressedListener, MainMenu.OnButtonPressedListenerM {

    override fun onButtonPressedM(text: String) {
        when (text) {
            "Mis Recetas" -> {
                val myRecipes = MyRecipes()
                supportFragmentManager.beginTransaction().replace(R.id.main_container, myRecipes).addToBackStack(null).commit()
            }
            "Recetas Fav" -> {
                val recipesFav = FavRecipes()
                supportFragmentManager.beginTransaction().replace(R.id.main_container, recipesFav).addToBackStack(null).commit()
            }
            "Buscar" -> {
                val search = Search()
                supportFragmentManager.beginTransaction().replace(R.id.main_container, search).addToBackStack(null).commit()
            }
            "Restaurantes Fav" -> {
                val resFav = FavRestaurants()
                supportFragmentManager.beginTransaction().replace(R.id.main_container, resFav).addToBackStack(null).commit()
            }

        }
    }

    override fun onButtonPressed(text: String) {

        when (text) {
            "Register" -> {
                val register = Register()
                supportFragmentManager.beginTransaction().replace(R.id.main_container, register).addToBackStack(null).commit()
            }
            "Login" -> {
                val menu = MainMenu()
                supportFragmentManager.beginTransaction().replace(R.id.main_container, menu).addToBackStack(null).commit()
            }
            else -> {
                val register2 = Register()
                supportFragmentManager.beginTransaction().replace(R.id.main_container, register2).addToBackStack(null).commit()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.AppTheme)
        //intento de splashscreen
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().
                add(R.id.main_container, loginFragment).
                commit()
    }

    fun datBasTest(){
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("recetas")

        myRef.setValue("nachos con queso")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

}
