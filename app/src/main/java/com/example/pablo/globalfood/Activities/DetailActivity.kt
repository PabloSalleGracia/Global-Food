package com.example.pablo.globalfood.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.pablo.globalfood.Fragments.Detail
import com.example.pablo.globalfood.Fragments.ReadReview
import com.example.pablo.globalfood.Fragments.ReviewsList
import com.example.pablo.globalfood.Fragments.WriteReview
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.OnTitleSelectedListener
import com.example.pablo.globalfood.R


private const val READ = "Ver Review"
private const val WRITE = "Escribir review"
private const val BACK = "Volver"
private const val RBACK = "VolverAtras"
private const val COMMENT = "COMENTAR"
private var tituloAReview = "titulo"
private var tipoAReview = "tipo"
private var autorAReview = "autor"

class DetailActivity : AppCompatActivity(), OnButtonPressedListener, OnTitleSelectedListener {

    override fun onAutorSelected(autor: String) {
        autorAReview = autor
    }

    override fun onTitleSelected(titulo:String , tipo:String) {
       tituloAReview = titulo
       tipoAReview = tipo
    }

    override fun onItemPressed(titulo: String, tipo: String, fav: String) {
        tituloAReview = titulo
        tipoAReview = tipo
        openReadReviews()

    }

    override fun onButtonPressed(text: String) {
        when(text){
            READ -> openListReviews()
            WRITE -> openWriteReviews()
            BACK -> goToLastScreen()
            RBACK -> goBackToMenu()
            COMMENT -> goToLastScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tituloRecibido = intent.getStringExtra("tituloRecRes")
        val tipoRecibido = intent.getStringExtra("tipoRecRes")
        val botonFavRecibido = intent.getStringExtra("botonFav")

        if(savedInstanceState == null){
            val detail = Detail.newInstance(tituloRecibido, tipoRecibido, botonFavRecibido)
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
