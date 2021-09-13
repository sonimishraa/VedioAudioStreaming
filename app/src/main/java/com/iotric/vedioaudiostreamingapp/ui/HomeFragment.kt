package com.iotric.vedioaudiostreamingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.iotric.vedioaudiostreamingapp.databinding.FragmentFirstBinding
import com.iotric.vedioaudiostreamingapp.model.Constants.REGEX_MEETING_URL_ROOM_ID
import com.iotric.vedioaudiostreamingapp.settings.SettingsStorePreferences
import com.iotric.vedioaudiostreamingapp.util.getInitEndpointEnvironment
import com.iotric.vedioaudiostreamingapp.util.isValidMeetingUrl
import com.iotric.vedioaudiostreamingapp.util.toSubdomain
import com.iotric.vedioaudiostreamingapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentFirstBinding
    lateinit var setting: SettingsStorePreferences
    lateinit var meetingUrl: String
    val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        meetingUrl = binding.editTextMeetingUrl.text.toString()
        setting = SettingsStorePreferences(requireContext())
    }

    private fun initListener() {
        binding.buttonJoinMeeting.setOnClickListener {
            if (saveTokenEndPoint(meetingUrl) && validateName()) {
                joinRoom()

            }
            /*else if(REGEX_MEETING_CODE.matches(meetingUrl) && validateName()){

            }*/
            else {
                binding.containerMeetingUrl.error = "Invalid Meeting URL"
            }
        }
    }

    private fun joinRoom() {
        val url = setting.lastUsedMeetingUrl
        val env = url.getInitEndpointEnvironment()
        val subdomain = url.toSubdomain()
        when {
            REGEX_MEETING_URL_ROOM_ID.matches(url) -> {
                val groups = REGEX_MEETING_URL_ROOM_ID.findAll(url).toList()[0].groupValues
                val roomId = groups[2]
                val role = groups[3]
                viewModel.fetchToken(subdomain, roomId, role, env)
            }
        }
    }

    private fun saveTokenEndPoint(url: String): Boolean {
        if (url.isValidMeetingUrl()) {
            setting.lastUsedMeetingUrl = url
            binding.editTextMeetingUrl.setText(url)
            setting.environment = url.getInitEndpointEnvironment()
            return true
        }
        return false
    }

    private fun validateName(): Boolean {

        val username = binding.editTextName.text.toString()
        if (username.isEmpty()) {
            binding.containerName.error = "Username cannot be empty"
            return false
        }
        return true
    }
}