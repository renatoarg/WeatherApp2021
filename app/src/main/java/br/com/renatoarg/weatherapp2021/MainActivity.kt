package br.com.renatoarg.weatherapp2021

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import br.com.renatoarg.data.home.HomeRepository
import br.com.renatoarg.weatherapp2021.base.BaseActivity
import br.com.renatoarg.weatherapp2021.cities.CitiesFragment
import br.com.renatoarg.weatherapp2021.databinding.ActivityMainBinding
import br.com.renatoarg.weatherapp2021.home.HomeFragment
import br.com.renatoarg.weatherapp2021.settings.SettingsFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewPager: ViewPager2

    val repository: HomeRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.icon = resources.getDrawable(R.drawable.ic_home, null)
                    tab.text = "Home"
                }
                1 -> {
                    tab.icon = resources.getDrawable(R.drawable.ic_apartments, null)
                    tab.text = "Cities"
                }
                2 -> {
                    tab.icon = resources.getDrawable(R.drawable.ic_settings, null)
                    tab.text = "Settings"
                }
            }
        }.attach()
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    @DelicateCoroutinesApi
    @ExperimentalUnsignedTypes
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> HomeFragment()
            1 -> CitiesFragment()
            else -> SettingsFragment()
        }
    }
}