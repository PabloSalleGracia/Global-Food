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
class MyRecipes : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_recipes, container, false)
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*val listMyRecipes: ListView = view!!.findViewById(R.id.list_item2)
        val datos2 = ArrayList<FavRecipe>()

        datos2.add(FavRecipe("my", "reci"))
        datos2.add(FavRecipe("sadas", "sadsad"))
        //datos.add((FavRecipe()))

        val myReci = FavRecipeAdapter(context!!, datos2)
        listMyRecipes.adapter = myReci*/
    }*/


}
