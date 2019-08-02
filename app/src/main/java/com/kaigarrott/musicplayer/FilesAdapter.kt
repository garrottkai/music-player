package com.kaigarrott.musicplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


/**
 * Adapter for files view data
 */
class FilesAdapter (private val items: List<FilesFragment.FilesItem>, private val listener: FilesFragment.FilesItemListener?) : RecyclerView.Adapter<FilesAdapter.Holder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.file_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val localListener = View.OnClickListener {
            listener?.onItemSelected(items[holder.adapterPosition])
        }
        holder.textView.text = items[position].name
        holder.textView.setOnClickListener(localListener)
    }

    override fun getItemCount(): Int = items.size

    class Holder (view: View) : RecyclerView.ViewHolder (view) {
        val textView: TextView = view.findViewById(R.id.textName)
    }
}