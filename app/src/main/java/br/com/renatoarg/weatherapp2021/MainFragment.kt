package br.com.renatoarg.weatherapp2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import br.com.renatoarg.weatherapp2021.base.BaseFragment
import br.com.renatoarg.weatherapp2021.cities.CitiesFragment
import br.com.renatoarg.weatherapp2021.databinding.FragmentHomeBinding
import br.com.renatoarg.weatherapp2021.databinding.FragmentMainBinding
import br.com.renatoarg.weatherapp2021.home.HomeFragment
import br.com.renatoarg.weatherapp2021.settings.SettingsFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.DelicateCoroutinesApi

@ExperimentalUnsignedTypes
@DelicateCoroutinesApi
class MainFragment : BaseFragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        setupViewPager()
    }

    private fun setupViewPager() {
        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        viewPager = binding.pager
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            when (position) {
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


//    override fun onBackPressed() {
//        if (viewPager.currentItem == 0) {
//            super.onBackPressed()
//        } else {
//            viewPager.currentItem = viewPager.currentItem - 1
//        }
//    }

    @DelicateCoroutinesApi
    @ExperimentalUnsignedTypes
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> HomeFragment()
            1 -> CitiesFragment {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddPlaceFragment())
            }
            else -> SettingsFragment()
        }
    }
}