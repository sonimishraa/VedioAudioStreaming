package com.tamasha.vedioaudiostreamingapp.uis.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentGamesBinding
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentSportsBinding

class SportsFragment : Fragment() {

    lateinit var binding: FragmentSportsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSportsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initView()
        //initListener()
    }


}
