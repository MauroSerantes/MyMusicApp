package com.myapps.mymusic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.myapps.mymusic.R
import com.myapps.mymusic.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost: NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
            R.id.homeFragment2 , R.id.searchFragment , R.id.myMusicFragment,
            )
        )

        supportActionBar?.hide()
        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.navButtons.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            run {
                when (destination.id) {
                    R.id.tracksFragment-> hideBottomNavBar()
                    R.id.artistsFragment->hideBottomNavBar()
                    else -> showBottomNavBar()
                }
            }
        }
    }

    private fun hideBottomNavBar(){
        binding.navButtons.visibility = View.GONE
    }

    private fun showBottomNavBar(){
        binding.navButtons.visibility = View.VISIBLE
    }


}