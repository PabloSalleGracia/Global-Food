package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.example.pablo.globalfood.Adapters.ListFavRecipesAdapter
import com.example.pablo.globalfood.Adapters.ListFavRestaurantsAdapter
import com.example.pablo.globalfood.Model.FavRestaurant
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import kotlinx.android.synthetic.main.favorite_recipes.*
import kotlinx.android.synthetic.main.favorite_restaurants.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavRestaurants : Fragment() {

    private lateinit var listener: OnButtonPressedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorite_restaurants, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val listFavRestau: ListView = view!!.findViewById(R.id.list_fav_restaurants)
        val datosFavRestaurants = ArrayList<FavRestaurant>()

        datosFavRestaurants.add(FavRestaurant("Favorite", "Restaurants", "prueba", 1))
        datosFavRestaurants.add(FavRestaurant("titulo", "india", "restau", 0))
        datosFavRestaurants.add(FavRestaurant("titulo", "china", "restau", 1))
        datosFavRestaurants.add(FavRestaurant("titulo", "china", "restau", 1))
        datosFavRestaurants.add(FavRestaurant("titulo", "españa", "restau", 1))
        datosFavRestaurants.add(FavRestaurant("titulo", "india", "restau", 1))
        datosFavRestaurants.add(FavRestaurant("titulo", "india", "restau", 0))
        datosFavRestaurants.add(FavRestaurant("titulo", "usa", "restau", 0))


        val favRestaurantAdapter = ListFavRestaurantsAdapter(context!!, datosFavRestaurants)
        listFavRestau.adapter = favRestaurantAdapter

        listFavRestau.onItemClickListener = (AdapterView.OnItemClickListener { _, _, position, _ ->

            //como pasar datos como con el extra, y como abrir nuevo fragment pasandole esos datos seleccionados
            //se pasan con el bundle?

            //var hola = "hola"
            listener.onItemPressed(favRestaurantAdapter.dataSource[position].title)
            listener.onButtonPressed(list_fav_restaurants.tag.toString())

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
