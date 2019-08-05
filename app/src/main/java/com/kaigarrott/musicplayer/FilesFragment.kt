package com.kaigarrott.musicplayer

import android.content.Context
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File


/**
 * Displays the file explorer view
 *
 */
class FilesFragment : Fragment() {

    private val canRead = Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    private var view: RecyclerView? = null
    private var adapter: FilesAdapter? = null
    private var listener: FilesItemListener? = null
    private var currentDir: File? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is FilesItemListener) listener = context
        if(canRead) {
            currentDir = Environment.getRootDirectory()//.getExternalStorageDirectory()
            val items = listItems(currentDir!!)
            if(items != null) adapter = FilesAdapter(items, listener)
        } else {
            // TODO show better alert re: storage access
            Toast.makeText(context, "Cant read storage", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        view = inflater.inflate(R.layout.fragment_files, container, false) as RecyclerView
        if (view is RecyclerView) {
            view?.setHasFixedSize(true)
            view?.layoutManager = LinearLayoutManager(context)
            view?.adapter = adapter
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun listItems(dir: File): List<FilesItem>? = dir.listFiles()?.map {file -> FilesItem(file.name, file.absolutePath, file.isDirectory)}

    fun select(item: FilesItem) {
        val itemFile = File(item.path)
        if(item.isDir) {
            navigate(itemFile)
        } else {
            // TODO start service and play file
        }
    }

    fun navigate(dir: File?) {
       if(dir is File) {
            currentDir = dir
            val items = listItems(dir)
            if(items != null) {
                adapter = FilesAdapter(items, listener)
                view?.adapter = adapter
            }
        }
    }

    fun onBackPressed() {
        navigate(currentDir?.parentFile)
    }

    interface FilesItemListener {
        fun onItemSelected(item: FilesItem?)
    }

    data class FilesItem(val name: String, val path: String, val isDir: Boolean)

}
