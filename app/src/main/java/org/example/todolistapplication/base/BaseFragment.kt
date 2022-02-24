package org.example.todolistapplication.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    //abstract val layoutResourceId: Int
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("BASEFRAGMENT","onviewcreated")
        super.onViewCreated(view, savedInstanceState)
    }
}