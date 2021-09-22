package com.tamasha.vedioaudiostreamingapp.uis.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentFantasyBinding
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentGamesBinding

class FantasyFragment : Fragment() {

    lateinit var binding: FragmentFantasyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFantasyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initView()
        //initListener()
    }


}
