package com.example.simbirsoftpracticeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().run {
            add(R.id.fragment_container_view, ProfileFragment())
            commit()
        }
    }
}