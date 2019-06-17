package com.example.pablo.globalfood.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.pablo.globalfood.Fragments.AddNewRecipe
import com.example.pablo.globalfood.Fragments.Search
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R

private const val UPLOAD = "SUBIR"
private const val BACK = "VOLVER"
private var tituloRecRes = "seEnviaTitulo"
private var tipoRecRes = "seEnviaTipo"
private var botonFav = "seEnviaFav"

class AddNewRecipeActivity : AppCompatActivity(), OnButtonPressedListener{

    override fun onItemPressed(titulo: String, tipo: String, fav: String) {
        tituloRecRes = titulo
        tipoRecRes = tipo
        botonFav = fav
        openDetailRecipes()
    }

    override fun onButtonPressed(text: String) {
        when(text){
            UPLOAD -> goToLastScreen()
            BACK -> goToLastScreen()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_recipe)

        val palabraBuscada = intent.getStringExtra("palabraBusqueda")
        val add = intent.getStringExtra("addRecipe")


        if(savedInstanceState == null){
            if(palabraBuscada != null){
                openSearch()
            }
            if(add != null){
                openAddNewRecipe()
            }
        }

    }

    private fun openSearch(){
        val search = Search()
        supportFragmentManager.beginTransaction().add(R.id.add_container, search).addToBackStack(null).commit()
    }


    private fun openAddNewRecipe(){
        //addToBackStack hace al usuario volver a una pagina blanca si le da atras con el boton del movil (FrameLayou de la Activity)
        //pero sino falla la aplicacion, error pendiente de arreglar/mejorar
        val addFrag = AddNewRecipe()
        supportFragmentManager.beginTransaction().add(R.id.add_container, addFrag).addToBackStack(null).commit()
    }

    private fun goToLastScreen(){
        //vuelvo a abrir el intent porque si uso super.OnBackPressed() salta el mismo error comentado en la funcion openAddNewRecipe
        //pendiente de mejorar/arreglar tambien
        val menuAgain = Intent(this, MainMenuActivity::class.java)
        startActivity(menuAgain)
    }

    private fun openDetailRecipes(){
        val detailActivity = Intent(this, DetailActivity::class.java)
        detailActivity.putExtra("tituloRecRes", tituloRecRes)
        detailActivity.putExtra("tipoRecRes", tipoRecRes)
        detailActivity.putExtra("botonFav", botonFav)
        startActivity(detailActivity)
    }

}
