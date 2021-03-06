package com.rs.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rs.movieapp.view.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.am_fragment_container, ListFragment())
        transaction.commit()

    }
}