package com.example.pablo.globalfood.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pablo.globalfood.Fragments.*
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.OnTitleSelectedListener
import com.example.pablo.globalfood.R

private var tituloAReview = "ensladnas"
private var tipoAReview = "ensladnas"
private var autorAReview = "ensladnas"

class DetailActivity : AppCompatActivity(), OnButtonPressedListener, OnTitleSelectedListener {

    override fun onAutorSelected(autor: String) {
        autorAReview = autor
    }

    override fun onTitleSelected(titulo:String , tipo:String) {
       tituloAReview = titulo
       tipoAReview = tipo
    }

    override fun onItemPressed(titulo: String, tipo: String) {
        tituloAReview = titulo
        tipoAReview = tipo
        openReadReviews()

    }

    override fun onButtonPressed(text: String) {
        when(text){
            "Ver Review" -> openListReviews()
            "Escribir review" -> openWriteReviews()
            "Volver" -> goToLastScreen()
            "VolverAtras" -> goBackToMenu()
            "COMENTAR" -> openListReviews()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tituloRecibido = intent.getStringExtra("tituloRecRes")
        val tipoRecibido = intent.getStringExtra("tipoRecRes")

        if(savedInstanceState == null){
            val detail = Detail.newInstance(tituloRecibido, tipoRecibido)
            supportFragmentManager.beginTransaction().
                    add(R.id.detail_container, detail).commit()
        }
    }

    private fun openListReviews(){
        val reviewList = ReviewsList.newInstance(tituloAReview, tipoAReview)
        supportFragmentManager.beginTransaction().replace(R.id.detail_container, reviewList).addToBackStack(null).commit()
    }

    private fun openWriteReviews(){
        val writeReview = WriteReview.newInstance(tituloAReview, tipoAReview)
        supportFragmentManager.beginTransaction().replace(R.id.detail_container, writeReview).addToBackStack(null).commit()
    }

    private fun openReadReviews(){
        val readReview = ReadReview.newInstance(tituloAReview, tipoAReview, autorAReview)
        supportFragmentManager.beginTransaction().replace(R.id.detail_container, readReview).addToBackStack(null).commit()
    }

    private fun goToLastScreen(){
        supportFragmentManager.popBackStackImmediate()
    }

    private fun goBackToMenu(){
        super.onBackPressed()
    }

}
