package com.iotric.vedioaudiostreamingapp.ui.meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iotric.vedioaudiostreamingapp.databinding.FragmentMeetingBinding

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