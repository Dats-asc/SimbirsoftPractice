package com.example.simbirsoftpracticeapp.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.Subjects
import com.example.simbirsoftpracticeapp.auth.AuthFragment
import com.example.simbirsoftpracticeapp.databinding.ActivityMainBinding
import com.example.simbirsoftpracticeapp.help.HelpFragment
import com.example.simbirsoftpracticeapp.news.NewsFragment
import com.example.simbirsoftpracticeapp.profile.ProfileFragment
import com.example.simbirsoftpracticeapp.search.SearchFragment
import com.jakewharton.threetenabp.AndroidThreeTen

class MainActivity : AppCompatActivity(), Navigator, Readable {

    private lateinit var binding: ActivityMainBinding

    private var _subjects: Subjects? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            return
        }
        _subjects = Subjects(this)
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

    override fun onResume() {
        super.onResume()
        setNotificationBadge(_subjects?.subjects?.size ?: 0)
    }

    override fun onAuthSuccesses() {
        binding.bottomNavigationView.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_container_view, HelpFragment())
            commit()
        }
    }

    override val subject: List<Subjects.Subject>
        get() = _subjects?.subjects!!

    override fun setNotificationBadge(count: Int) {
        if (count == 0) {
            binding.bottomNavigationView.getOrCreateBadge(R.id.navigation_news).apply {
                isVisible = false
                number = 0
            }
        } else {
            binding.bottomNavigationView.getOrCreateBadge(R.id.navigation_news).apply {
                isVisible = true
                number = count
            }
        }
    }

    override fun setAsRead(id: Int) {
        _subjects?.setAsRead(id)
    }
}