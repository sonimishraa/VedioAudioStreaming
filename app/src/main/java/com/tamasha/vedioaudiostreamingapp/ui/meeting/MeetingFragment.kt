package com.tamasha.vedioaudiostreamingapp.ui.meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentMeetingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeetingFragment : Fragment() {

    lateinit var binding: FragmentMeetingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeetingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        initObserver()
    }

    private fun initView() {

    }

    private fun initListener() {

    }

    private fun initObserver() {

    }

}