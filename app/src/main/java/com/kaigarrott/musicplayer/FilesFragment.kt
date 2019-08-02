package com.kaigarrott.musicplayer


import android.content.Context
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File


/**
 * Displays the file explorer view
 *
 */
class FilesFragment : Fragment() {

    private val canRead = Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    private var adapter: FilesAdapter? = null
    private var listener: FilesItemListener? = null
    private var currentDir: File? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is FilesItemListener) listener = context
        if(canRead) {
            currentDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
            adapter = FilesAdapter(listFiles(currentDir), listener)

        } else {
            // TODO show alert re: storage access
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_files, container, false)
        if (view is RecyclerView) {
            with(view) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
            }
            view.adapter = adapter
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun listFiles(dir: File?): List<FilesItem> = dir.listFiles().map {file -> FilesItem(file.name)}

    interface FilesItemListener {
        fun onItemSelected(item: FilesItem?)
    }

    class FilesItem(val name: String)

}
