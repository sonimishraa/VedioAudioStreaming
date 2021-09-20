package com.tamasha.vedioaudiostreamingapp.ui.sdkhome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tamasha.vedioaudiostreamingapp.BuildConfig
import com.tamasha.vedioaudiostreamingapp.databinding.SdkFragmentHomeBinding
import com.tamasha.vedioaudiostreamingapp.model.Constants.ENV_PROD
import com.tamasha.vedioaudiostreamingapp.model.Constants.REGEX_MEETING_CODE
import com.tamasha.vedioaudiostreamingapp.model.Constants.REGEX_MEETING_URL_CODE
import com.tamasha.vedioaudiostreamingapp.model.Constants.REGEX_MEETING_URL_ROOM_ID
import com.tamasha.vedioaudiostreamingapp.model.Constants.ROOM_DETAILS
import com.tamasha.vedioaudiostreamingapp.model.request.RoomDetails
import com.tamasha.vedioaudiostreamingapp.settings.SettingsStorePreferences
import com.tamasha.vedioaudiostreamingapp.tokennetwork.Status
import com.tamasha.vedioaudiostreamingapp.ui.meeting.MeetingActivity
import com.tamasha.vedioaudiostreamingapp.util.getInitEndpointEnvironment
import com.tamasha.vedioaudiostreamingapp.util.isValidMeetingUrl
import com.tamasha.vedioaudiostreamingapp.util.toSubdomain
import com.tamasha.vedioaudiostreamingapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: SdkFragmentHomeBinding
    lateinit var setting: SettingsStorePreferences
    lateinit var meetingUrl: String
    lateinit var userName: String
    val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SdkFragmentHomeBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEditTextViews()
        initListener()
        initObserver()
    }

    private fun initView() {
        userName = binding.editTextName.text.toString()
        meetingUrl = binding.editTextMeetingUrl.text.toString()
        setting = SettingsStorePreferences(requireContext())
        binding.editTextName.setText(setting.username)
        binding.editTextMeetingUrl.setText(setting.lastUsedMeetingUrl)
    }

    private fun initEditTextViews() {
        // Load the data if saved earlier (easy debugging)
        binding.editTextName.setText(setting.username)
        binding.editTextMeetingUrl.setText(setting.lastUsedMeetingUrl)

        mapOf(
            binding.editTextName to binding.containerName,
            binding.editTextMeetingUrl to binding.containerMeetingUrl
        ).forEach {
            it.key.addTextChangedListener { text ->
                if (text.toString().isNotEmpty()) it.value.error = null
            }
        }
    }

    private fun initListener() {
        binding.buttonJoinMeeting.setOnClickListener {
            val input = binding.editTextMeetingUrl.text.toString()
            if (saveTokenEndPoint(input) && validateName()) {
                joinRoom()

            } else if (REGEX_MEETING_CODE.matches(input) && validateName()) {
                var subdomain = BuildConfig.TOKEN_ENDPOINT.toSubdomain()
                if (BuildConfig.INTERNAL) {
                    val env = when (setting.environment) {
                        ENV_PROD -> "prod2"

                        else -> "qa2"
                    }
                    subdomain = "$env.100ms.live"
                }
                val url = "https://$subdomain/meeting/$input"
                saveTokenEndPoint(url)
                joinRoom()
            } else {
                binding.containerMeetingUrl.error = "Invalid Meeting URL"
            }
        }
    }

    private fun initObserver() {
        viewModel.authTokenResponse.observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    val data = response.data!!
                    val roomDetails = RoomDetails(
                        env = setting.environment,
                        url = setting.lastUsedMeetingUrl,
                        username = userName,
                        authToken = data.token
                    )
                    Log.i(TAG, "Auth Token: ${roomDetails.authToken}")
                    Intent(requireContext(), MeetingActivity::class.java).apply {
                        putExtra(ROOM_DETAILS, roomDetails)
                        startActivity(this)
                    }
                    requireActivity().finish()
                }
                Status.ERROR -> {
                    //hideProgressBar()
                    Log.e(TAG, "observeLiveData: $response")
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                Status.LOADING -> {

                }
            }

        })

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
                viewModel.fetchTokenByRoom(subdomain, roomId, role, env)
            }
            REGEX_MEETING_URL_CODE.matches(url) -> {
                val groups = REGEX_MEETING_URL_CODE.findAll(url).toList()[0].groupValues
                val code = groups[2]
                viewModel.fetchTokenByCode(subdomain, code, env)
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