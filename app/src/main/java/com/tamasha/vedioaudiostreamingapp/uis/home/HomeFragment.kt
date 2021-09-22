package com.tamasha.vedioaudiostreamingapp.uis.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout.INDICATOR_GRAVITY_BOTTOM
import com.google.android.material.tabs.TabLayout.INDICATOR_GRAVITY_CENTER
import com.tamasha.vedioaudiostreamingapp.R
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(){

   val homeViewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        //initListener()
    }

    private fun initView() {
        //binding.appbar.toolbarTitle.text = getString(R.string.appointment_toolbar_title)
        binding.viewPager.adapter = HomePagerAdapter(childFragmentManager)
        binding.tablayout.setupWithViewPager(binding.viewPager)
         binding.tablayout.setSelectedTabIndicatorColor(Color.parseColor("#EC008C"))
        binding.tablayout.setSelectedTabIndicatorGravity(INDICATOR_GRAVITY_BOTTOM)

    }

}