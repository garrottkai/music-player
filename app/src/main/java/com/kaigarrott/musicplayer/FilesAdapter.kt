package com.kaigarrott.musicplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.file_item.view.*

import com.kaigarrott.musicplayer.FilesFragment.FilesItemListener

/**
 * Adapter for files view data
 */
class FilesAdapter (private val items: List<String>, private val listener: FilesItemListener) : RecyclerView.Adapter<FilesAdapter.Holder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.file_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.textView.text = items[position]
    }

    override fun getItemCount(): Int = items.size

    inner class Holder (view: View) : RecyclerView.ViewHolder (view) {
        val textView: TextView = view.dummyText
    }
}