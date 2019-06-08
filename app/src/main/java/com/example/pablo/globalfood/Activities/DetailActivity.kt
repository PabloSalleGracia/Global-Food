package com.example.pablo.globalfood.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pablo.globalfood.Fragments.ReadReview
import com.example.pablo.globalfood.Fragments.RecipesDetail
import com.example.pablo.globalfood.Fragments.ReviewsList
import com.example.pablo.globalfood.Fragments.WriteReview
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.OnTitleSelectedListener
import com.example.pablo.globalfood.R

private var tituloAReview = "ensladnas"

class DetailActivity : AppCompatActivity(), OnButtonPressedListener, OnTitleSelectedListener {

    override fun onTitleSelected(text: String) {
       tituloAReview = text
    }

    override fun onItemPressed(text: Any) {
        tituloAReview = text.toString()
        openReadReviews()

    }

    override fun onButtonPressed(text: String) {
        when(text){
            "Ver Review" -> openListReviews()
            "Escribir review" -> openWriteReviews()
            "Volver" -> goToLastScreen()
            "VolverAtras" -> goBackToMenu()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tituloRecibido = intent.getStringExtra("tituloRecRes")

        if(savedInstanceState == null){
            val recipesDetail = RecipesDetail.newInstance(tituloRecibido)
            supportFragmentManager.beginTransaction().
                    add(R.id.detail_container, recipesDetail).commit()
        }
    }

    private fun openListReviews(){
        val reviewList = ReviewsList.newInstance(tituloAReview)
        supportFragmentManager.beginTransaction().replace(R.id.detail_container, reviewList).addToBackStack(null).commit()
    }

    private fun openWriteReviews(){
        val writeReview = WriteReview.newInstance(tituloAReview)
        supportFragmentManager.beginTransaction().replace(R.id.detail_container, writeReview).addToBackStack(null).commit()
    }

    private fun openReadReviews(){
        val readReview = ReadReview.newInstance(tituloAReview)
        supportFragmentManager.beginTransaction().replace(R.id.detail_container, readReview).addToBackStack(null).commit()
    }

    private fun goToLastScreen(){
        supportFragmentManager.popBackStackImmediate()
    }

    private fun goBackToMenu(){
        super.onBackPressed()
    }

}
