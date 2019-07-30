package com.kaigarrott.musicplayer


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Displays the file explorer view
 *
 */
class FilesFragment : Fragment() {

    //private lateinit var view: RecyclerView
    //private lateinit var adapter: FilesAdapter
    //private lateinit var manager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //manager = LinearLayoutManager(context)
        //adapter = FilesAdapter()

        val view = inflater.inflate(R.layout.fragment_files, container, false)
        if (view is RecyclerView) {
            with(view) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = FilesAdapter()
            }
        }
        return view
    }



}
