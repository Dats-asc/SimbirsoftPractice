package com.example.simbirsoftpracticeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.simbirsoftpracticeapp.auth.AuthFragment
import com.example.simbirsoftpracticeapp.databinding.ActivityMainBinding
import com.example.simbirsoftpracticeapp.help.HelpFragment
import com.example.simbirsoftpracticeapp.news.NewsFragment
import com.example.simbirsoftpracticeapp.profile.ProfileFragment
import com.example.simbirsoftpracticeapp.search.SearchFragment
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            return;
        }
        supportFragmentManager.beginTransaction().run {
            binding.bottomNavigationView.visibility = View.GONE
            add(R.id.fragment_container_view, AuthFragment())
            commit()
        }

        setupNavigation()
    }

    private fun setupNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navigation_help
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_news -> {
                    supportFragmentManager.beginTransaction().run {
                        replace(R.id.fragment_container_view, NewsFragment())
                        commit()
                    }
                    true
                }
                R.id.navigation_search -> {
                    supportFragmentManager.beginTransaction().run {
                        replace(R.id.fragment_container_view, SearchFragment())
                        commit()
                    }
                    true
                }
                R.id.navigation_help -> {
                    supportFragmentManager.beginTransaction().run {
                        replace(R.id.fragment_container_view, HelpFragment())
                        commit()
                    }
                    true
                }
                R.id.navigation_history -> {
                    true
                }
                R.id.navigation_profile -> {
                    supportFragmentManager.beginTransaction().run {
                        replace(R.id.fragment_container_view, ProfileFragment())
                        commit()
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onAuthSuccesses() {
        binding.bottomNavigationView.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_container_view, HelpFragment())
            commit()
        }
    }
}