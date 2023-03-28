package com.example.mytranslator.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mytranslator.R
import com.example.mytranslator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = binding.mainActivityFragmentContainer.getFragment<NavHostFragment>()
        val navSet = setOf(R.id.navigation_search, R.id.navigation_history)
        setupActionBarWithNavController(navHostFragment.navController, AppBarConfiguration(navSet))
        binding.mainActivityBottomNavigation.setupWithNavController(navHostFragment.navController)
    }
}