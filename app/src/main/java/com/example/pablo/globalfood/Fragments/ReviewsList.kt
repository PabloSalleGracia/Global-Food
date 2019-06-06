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
import com.example.pablo.globalfood.Adapters.ListReviewsAdapter
import com.example.pablo.globalfood.Model.FavRecipe
import com.example.pablo.globalfood.Model.Review
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.OnTitleSelectedListener

import com.example.pablo.globalfood.R
import kotlinx.android.synthetic.main.detail_recipes.*
import kotlinx.android.synthetic.main.reviews_list.*

private const val tituloRecibido = "datosRecibidos"

class ReviewsList : Fragment() {

    private var tituloRewList: String? = null
    private lateinit var listener : OnButtonPressedListener
    private lateinit var listenerReview : OnTitleSelectedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.reviews_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tituloRewList = it.getString(tituloRecibido)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(tituloRewList: String) =
                ReviewsList().apply {
                    arguments = Bundle().apply {
                        putString(tituloRecibido, tituloRewList)
                    }
                }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        titulo_reviews_list.text = tituloRewList

        val listReviews: ListView = view!!.findViewById(R.id.list_reviews)
        val datosReviews = ArrayList<Review>()

        datosReviews.add(Review(1,"My", "Reviews", "test", "prueba"))
        datosReviews.add(Review(2,"My", "dsadsa", "tesdsadat", "prueba"))
        datosReviews.add(Review(3,"My", "Revidsadsaews", "test", "prueba"))


        val reviews = ListReviewsAdapter(context!!, datosReviews)
        listReviews.adapter = reviews

        write_reviews.setOnClickListener{
            listenerReview.onTitleSelected(titulo_reviews_list.text.toString())
            listener.onButtonPressed(write_reviews.tag.toString())
        }
        listReviews.onItemClickListener = (AdapterView.OnItemClickListener { _, _, position, _ ->
            listener.onItemPressed(reviews.dataSource[position].title)
        })

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
        listenerReview = activity as OnTitleSelectedListener
    }


}
