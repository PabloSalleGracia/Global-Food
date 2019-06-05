package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.example.pablo.globalfood.Model.FavRecipe
import com.example.pablo.globalfood.Adapters.ListFavRecipesAdapter
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import kotlinx.android.synthetic.main.favorite_recipes.*
import kotlinx.android.synthetic.main.my_recipes.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavRecipes : Fragment() {

    private lateinit var listener: OnButtonPressedListener

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



        val listFavRecipes: ListView = view!!.findViewById(R.id.list_fav_recipes)
        val datosFavRecipes = ArrayList<FavRecipe>()

        datosFavRecipes.add(FavRecipe("Favorite", "Recipes", "prueba", 1))
        datosFavRecipes.add(FavRecipe("titulo", "india", "plato", 0))
        datosFavRecipes.add(FavRecipe("titulo", "india", "plato", 1))
        datosFavRecipes.add(FavRecipe("titulo", "japon", "plato", 1))
        datosFavRecipes.add(FavRecipe("titulo", "india", "plato", 0))
        datosFavRecipes.add(FavRecipe("titulo", "china", "plato", 1))
        datosFavRecipes.add(FavRecipe("titulo", "india", "plato", 0))
        datosFavRecipes.add(FavRecipe("titulo", "italia", "plato", 0))

        val favRecipeAdapter = ListFavRecipesAdapter(context!!, datosFavRecipes )
        listFavRecipes.adapter = favRecipeAdapter

        listFavRecipes.onItemClickListener = (AdapterView.OnItemClickListener { _, _, position, _ ->

            //como pasar datos como con el extra, y como abrir nuevo fragment pasandole esos datos seleccionados
            //se pasan con el bundle?

            //var hola = "hola"
            listener.onItemPressed(favRecipeAdapter.dataSource[position].title)
            listener.onButtonPressed(list_fav_recipes.tag.toString())

            /* BORRAR FILAS
            datos2.removeAt(position)
            myReci.notifyDataSetChanged()*/

        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }


}
