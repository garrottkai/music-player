package com.kaigarrott.musicplayer


import android.content.Context
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
    private var listener: FilesItemListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is FilesItemListener) listener = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //manager = LinearLayoutManager(context)
        //adapter = FilesAdapter()

        val view = inflater.inflate(R.layout.fragment_files, container, false)
        if (view is RecyclerView) {
            with(view) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = FilesAdapter(listOf("these", "are", "some", "fake", "items"), listener)
            }
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface FilesItemListener {
        fun onItemSelected(item: String?)
    }

}
