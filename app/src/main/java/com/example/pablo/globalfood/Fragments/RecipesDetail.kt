package com.example.pablo.globalfood.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pablo.globalfood.OnButtonPressedListener
import com.example.pablo.globalfood.OnTitleSelectedListener

import com.example.pablo.globalfood.R
import kotlinx.android.synthetic.main.detail_recipes.*


private const val tituloRecibido = "datosRecibidos"

class RecipesDetail : Fragment() {

    private var tituloRecDet: String? = null
    private lateinit var listener: OnButtonPressedListener
    private lateinit var listenerTitulo : OnTitleSelectedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_recipes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tituloRecDet = it.getString(tituloRecibido)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(tituloRecDet: String) =
                RecipesDetail().apply {
                    arguments = Bundle().apply {
                        putString(tituloRecibido, tituloRecDet)
                    }
                }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        titulo_detail_receta.text = tituloRecDet

        ver_reviews_detrec.setOnClickListener{
            listenerTitulo.onTitleSelected(titulo_detail_receta.text.toString())
            listener.onButtonPressed(ver_reviews_detrec.tag.toString())
        }


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = activity as OnButtonPressedListener
        listenerTitulo = activity as OnTitleSelectedListener
    }


}
