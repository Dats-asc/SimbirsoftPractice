package com.example.simbirsoftpracticeapp.presentation.main

import android.os.Bundle
import android.view.View
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.databinding.ActivityMainBinding
import com.example.simbirsoftpracticeapp.presentation.auth.AuthFragment
import com.example.simbirsoftpracticeapp.presentation.help.HelpFragment
import com.example.simbirsoftpracticeapp.presentation.news.NewsFragment
import com.example.simbirsoftpracticeapp.presentation.profile.ProfileFragment
import com.example.simbirsoftpracticeapp.presentation.search.SearchFragment
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import moxy.MvpAppCompatActivity
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), HasAndroidInjector, Navigator {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any?>

    private lateinit var binding: ActivityMainBinding

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) {
            return
        }
        supportFragmentManager.beginTransaction().run {
            binding.bottomNavigationView.visibility = View.GONE
            add(R.id.fragment_container_view, AuthFragment())
            commit()
        }
    }

    override fun onStart() {
        super.onStart()
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.navigation_help
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            onBottomNavigationItemSelected(item.itemId)
        }
    }

    private fun onBottomNavigationItemSelected(itemId: Int): Boolean {
        return when (itemId) {
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

    override fun onAuthSuccesses() {
        binding.bottomNavigationView.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_container_view, HelpFragment())
            commit()
        }
    }

}