package br.com.renatoarg.weatherapp2021

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.renatoarg.data.home.HomeRepository
import br.com.renatoarg.weatherapp2021.base.BaseActivity
import br.com.renatoarg.weatherapp2021.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment

    val repository: HomeRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupNavController()
    }

    private fun setupNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        NavigationUI.setupWithNavController(
            binding.bottomMenu,
            navHostFragment.navController
        )

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
//                R.id.homeFragment -> setupForHostFragment()
            }
        }
    }
}