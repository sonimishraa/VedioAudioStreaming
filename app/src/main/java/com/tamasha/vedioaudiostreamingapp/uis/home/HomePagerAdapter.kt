package com.tamasha.vedioaudiostreamingapp.uis.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HomePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AllGamesFragment()

            1 -> CardFragment()

            2 ->SportsFragment()

            3->FantasyFragment()

            else -> AllGamesFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        val getPageTitle = arrayOf("All Games", "Card", "Sports", "Fantasy")
        return getPageTitle[position]
    }
}
