package com.example.simbirsoftpracticeapp.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.common.Constants
import com.example.simbirsoftpracticeapp.databinding.ActivityMainBinding
import com.example.simbirsoftpracticeapp.presentation.news.NewsDetailFragment
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

    private lateinit var navController: NavController

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        navController = findNavController(R.id.fragment_container_view)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.authFragment -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navController.handleDeepLink(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }


    override fun onAuthSuccesses() {
        navController.navigate(R.id.action_authFragment_to_navigation_help)
    }

}