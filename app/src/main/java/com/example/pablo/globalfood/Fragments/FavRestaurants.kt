package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.example.pablo.globalfood.Adapters.ListFavRestaurantsAdapter
import com.example.pablo.globalfood.Model.FavRestaurant
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.favorite_restaurants.*


class FavRestaurants : Fragment() {

    private lateinit var listener: OnButtonPressedListener
    private val datosFavRestaurants = ArrayList<FavRestaurant>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorite_restaurants, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fireBaseSelectFavRecipes()

        search_view_fav_restaurants.setOnClickListener{
            listener.onButtonPressed("SEARCH")
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
    }

    private fun fireBaseSelectFavRecipes(){
        val db = FirebaseFirestore.getInstance()
        //id del usuario logeado en firebase
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val refUserId = db.document("/Usuarios/$user")

        db.collection("Usuario-Restaurantes")
                .whereEqualTo("id_usuario", refUserId)
                .whereEqualTo("esFav?", true)
                .addSnapshotListener { values, _ ->
                    if (values != null) {
                        for (dc in values.documentChanges) {
                            when (dc.type) {
                                DocumentChange.Type.MODIFIED -> {
                                    println("MODIFIED")
                                }
                                DocumentChange.Type.ADDED -> {
                                    datosFavRestaurants.add(FavRestaurant(dc.document.data["titulo"].toString(), dc.document.data["pais"].toString(),
                                            dc.document.data["tipo"].toString(), dc.document.data["esFav?"] as Boolean))
                                }
                                DocumentChange.Type.REMOVED -> {
                                    datosFavRestaurants.remove(FavRestaurant(dc.document.data["titulo"].toString(), dc.document.data["pais"].toString(),
                                            dc.document.data["tipo"].toString(), dc.document.data["esFav?"] as Boolean))
                                }
                            }
                        }
                        fillListFavRestaurants()
                    }
                }
    }

    private fun fillListFavRestaurants(){
        if(view != null){
            val listFavRestau: ListView = view!!.findViewById(R.id.list_fav_restaurants)

            val favRestaurantAdapter = ListFavRestaurantsAdapter(context!!, datosFavRestaurants)
            listFavRestau.adapter = favRestaurantAdapter

            listFavRestau.onItemClickListener = (AdapterView.OnItemClickListener { _, _, position, _ ->
                listener.onItemPressed(favRestaurantAdapter.dataSource[position].title, favRestaurantAdapter.dataSource[position].resDish, favRestaurantAdapter.dataSource[position].esFav.toString())
            })
        }

    }

}
