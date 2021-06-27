package br.com.renatoarg.weatherapp2021

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import br.com.renatoarg.data.api.locations.LocationsRepository
import br.com.renatoarg.data.api.home.HomeRepository
import br.com.renatoarg.weatherapp2021.base.BaseActivity
import br.com.renatoarg.weatherapp2021.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment

    val homeRepository: HomeRepository by inject()
    val locationsRepository: LocationsRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

    }
}