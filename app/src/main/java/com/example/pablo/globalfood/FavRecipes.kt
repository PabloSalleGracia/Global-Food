package com.example.pablo.globalfood


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavRecipes : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorite_recipes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //cargar datos de string
        //val listItems = resources.getStringArray(R.array.pruebaLista)

        //mirar como obtener context sin !!



        val listFavRecipes: ListView = view!!.findViewById(R.id.list_item1)
        val datos = ArrayList<FavRecipe>()

        datos.add(FavRecipe("Favorite", "Recipes"))
        datos.add(FavRecipe("pdf", "fdsaf"))
        datos.add(FavRecipe("pdf", "fdsaf"))
        datos.add(FavRecipe("pdf", "fdsaf"))
        datos.add(FavRecipe("pdf", "fdsaf"))
        datos.add(FavRecipe("pdf", "fdsaf"))
        datos.add(FavRecipe("pdf", "fdsaf"))
        datos.add(FavRecipe("pdf", "fdsaf"))
        datos.add(FavRecipe("pdf", "fdsaf"))

        val favRecipeAdapter = FavRecipeAdapter(context!!, datos)
        listFavRecipes.adapter = favRecipeAdapter
    }


}
