package com.tamasha.vedioaudiostreamingapp.ui.dashboard.refreshearn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tamasha.bottomnavigation.dashboard.play.PlayViewModel
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentRefreshEarnBinding

class RefreshFragment : Fragment() {

    private lateinit var playViewModel: PlayViewModel
    private var _binding: FragmentRefreshEarnBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        playViewModel =
            ViewModelProvider(this).get(PlayViewModel::class.java)

        _binding = FragmentRefreshEarnBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}