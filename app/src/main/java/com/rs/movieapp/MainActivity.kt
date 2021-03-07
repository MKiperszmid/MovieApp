package com.rs.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rs.movieapp.view.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.am_fragment_container, ListFragment()).commit()
    }
}