package com.kaigarrott.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(), FilesFragment.FilesItemListener {

    private var filesFragment: FilesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        filesFragment = supportFragmentManager.findFragmentById(R.id.filesFragment) as FilesFragment
    }

    override fun onItemSelected(item: FilesFragment.FilesItem?) {
        if(item != null) {
            filesFragment?.select(item)
            Toast.makeText(this, item.name, Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        filesFragment?.onBackPressed()
    }
}
