package com.tamasha.vedioaudiostreamingapp.uis.drawerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.navigation.NavigationView
import com.tamasha.vedioaudiostreamingapp.R
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentHelpBinding
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentHomeBinding
import com.tamasha.vedioaudiostreamingapp.uis.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HelpFragment : Fragment() {

   val homeViewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initView()
    }

}