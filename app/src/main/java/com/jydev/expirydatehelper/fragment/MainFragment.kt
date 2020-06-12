package com.jydev.expirydatehelper.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jydev.expirydatehelper.R

class MainFragment(position : Int) : Fragment() {
    private val mPosition = position
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.main_fragment,null)
        val testTv = view.findViewById<TextView>(R.id.test_tv)
        testTv.text = mPosition.toString()
        return view
    }
}