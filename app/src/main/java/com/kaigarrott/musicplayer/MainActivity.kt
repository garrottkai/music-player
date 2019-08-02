package com.kaigarrott.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(), FilesFragment.FilesItemListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onItemSelected(item: FilesFragment.FilesItem?) {
        Toast.makeText(this, item?.name, Toast.LENGTH_LONG).show()
    }
}
