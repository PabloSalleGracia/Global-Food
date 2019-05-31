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
class FavRestaurants : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorite_restaurants, container, false)
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*val listFavRestau: ListView = view!!.findViewById(R.id.list_item3)
        val datos3 = ArrayList<FavRecipe>()

        datos3.add(FavRecipe("fav", "rest"))
        datos3.add(FavRecipe("fsd", "fdasf"))

        val favRes = FavRecipeAdapter(context!!, datos3)
        listFavRestau.adapter = favRes*/
    }*/

}
