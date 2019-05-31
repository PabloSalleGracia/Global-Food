package com.example.pablo.globalfood

import android.content.Context
import android.content.res.Resources
import android.support.v4.app.Fragment
import android.os.Bundle
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * A placeholder fragment containing a simple view.
 */
class MainMenuFragment : Fragment() {

    private lateinit var listener: OnButtonPressedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val listFavRecipes:ListView = view!!.findViewById(R.id.list_item)
        //cargar datos de string
        //val listItems = resources.getStringArray(R.array.pruebaLista)

        //mirar como obtener context sin !!
        val datos = ArrayList<FavRecipe>()


        datos.add(FavRecipe("juan", "rapero"))
        datos.add(FavRecipe("juan", "colilla"))
        //datos.add((FavRecipe()))

        val favRecipeAdapter = FavRecipeAdapter(context!!, datos)
        listFavRecipes.adapter = favRecipeAdapter


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }
}
