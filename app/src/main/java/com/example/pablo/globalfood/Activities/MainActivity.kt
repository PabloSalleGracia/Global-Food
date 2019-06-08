package com.example.pablo.globalfood.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pablo.globalfood.Fragments.Login
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.example.pablo.globalfood.Fragments.Register
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private const val TAG = "MainActivity"

private const val REGISTER = "Register"
private const val LOGIN = "Login"
private const val SIGNUP = "Registrarse"
private const val HAVEACC = "TengoCuenta"


class MainActivity : AppCompatActivity(), OnButtonPressedListener {
    override fun onItemPressed(titulo: String, tipo: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onButtonPressed(text: String) {
        when (text) {
            REGISTER -> openRegister()
            LOGIN -> {
                val intent = Intent(this, MainMenuActivity::class.java)
                //intent.putExtras(getRegisterBundle())
                //startActivityForResult(intent, MainMenuActivity.REQUEST_CODE)
                startActivity(intent)
            }
            SIGNUP -> openLogin()
            HAVEACC -> openLogin()
            /*MYRECIPES -> openMyRecipes()
            FAVRECIPES -> openRecipesFav()
            SEARCH -> openSearch()
            FAVRESTAURANTS -> openRestaurantFav()
            else -> openSearch()*/
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.AppTheme)
        //intento de splashscreen
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            val loginFragment = Login()
            supportFragmentManager.beginTransaction().
                    add(R.id.main_container, loginFragment).
                    commit()
        }

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


    private fun openRegister(){
        val register = Register()
        supportFragmentManager.beginTransaction().replace(R.id.main_container, register).addToBackStack(null).commit()
    }


    private fun openLogin(){
        val login = Login()
        supportFragmentManager.beginTransaction().replace(R.id.main_container, login).addToBackStack(null).commit()
    }


}
