package com.example.mytranslator.view

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setOnExitAnimationListener { splashScreenProvider ->
                ObjectAnimator.ofFloat(
                    splashScreenProvider.view,
                    View.TRANSLATION_X,
                    INITIAL_VALUE,
                    -splashScreenProvider.view.width.toFloat()
                ).apply {
                    duration = TRANSLATION_DURATION
                    interpolator = AnticipateInterpolator()
                    doOnEnd {
                        splashScreenProvider.remove()
                    }
                }.start()
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = binding.mainActivityFragmentContainer.getFragment<NavHostFragment>()
        val navSet = setOf(R.id.navigation_search, R.id.navigation_history)
        setupActionBarWithNavController(navHostFragment.navController, AppBarConfiguration(navSet))
        binding.mainActivityBottomNavigation.setupWithNavController(navHostFragment.navController)
    }

    companion object {
        private const val INITIAL_VALUE = 0F
        private const val TRANSLATION_DURATION = 800L
    }
}