package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.example.pablo.globalfood.Adapters.FavRecipeAdapter
import com.example.pablo.globalfood.Model.FavRecipe
import com.example.pablo.globalfood.OnButtonPressedListener

import com.example.pablo.globalfood.R
import kotlinx.android.synthetic.main.my_recipes.*
import kotlinx.android.synthetic.main.reviews_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ReviewsList : Fragment() {

    private lateinit var listener : OnButtonPressedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.reviews_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val listMyRecipes: ListView = view!!.findViewById(R.id.list_item4)
        val datos4 = ArrayList<FavRecipe>()

        datos4.add(FavRecipe("My", "Reviews"))
        datos4.add(FavRecipe("sadas", "sadsad"))
        datos4.add(FavRecipe("sadas", "sadsad"))
        datos4.add(FavRecipe("sadas", "sadsad"))
        datos4.add(FavRecipe("sadas", "sadsad"))
        datos4.add(FavRecipe("sadas", "sadsad"))
        //datos.add((FavRecipe()))

        val reviews = FavRecipeAdapter(context!!, datos4)
        listMyRecipes.adapter = reviews

        listMyRecipes.onItemClickListener = (AdapterView.OnItemClickListener { _, _, position, _ ->

            //como pasar datos como con el extra, y como abrir nuevo fragment pasandole esos datos seleccionados
            //se pasan con el bundle?

            //var hola = "hola"
            listener.onItemPressed(reviews.dataSource[position].title)
            listener.onButtonPressed(list_item4.tag.toString())

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
