package com.example.pablo.globalfood.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pablo.globalfood.R
import kotlinx.android.synthetic.main.detail_recipes.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val datosPrueba = "datosEnviados"

/**
 * A simple [Fragment] subclass.
 *
 */
class RecipesDetail : Fragment() {

    private var param1: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //cambiar theme para quitar tabBar?
        //activity!!.actionBar.hide()
        //context!!.theme.applyStyle(R.style.AppTheme, true)
        return inflater.inflate(R.layout.detail_recipes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(datosPrueba)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                RecipesDetail().apply {
                    arguments = Bundle().apply {
                        putString(datosPrueba, param1)
                    }
                }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        /*button3.setOnClickListener{

        }*/
        tituloRec.text = param1


    }


}
