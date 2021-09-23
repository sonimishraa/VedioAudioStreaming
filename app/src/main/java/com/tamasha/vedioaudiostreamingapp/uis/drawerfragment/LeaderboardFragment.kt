package com.tamasha.vedioaudiostreamingapp.uis.drawerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentLeaderboardBinding
import com.tamasha.vedioaudiostreamingapp.uis.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeaderboardFragment : Fragment() {

    val homeViewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentLeaderboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initView()
    }

}