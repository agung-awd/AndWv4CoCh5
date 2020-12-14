package com.studioos.batuguntingkertas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator

class LandingPage : AppCompatActivity() {
    val NUM_PAGES = 3
    private val viewPager2 by lazy { findViewById<ViewPager2>(R.id.viewpager2) }
    private val spring_dots_indicator by lazy { findViewById<SpringDotsIndicator>(R.id.spring_dots_indicator) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager2.adapter = pagerAdapter
        spring_dots_indicator.setViewPager2(viewPager2)

    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = SlideFragment(position)
    }

    override fun onBackPressed() {
        if (viewPager2.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager2.currentItem = viewPager2.currentItem - 1
        }
    }
}
