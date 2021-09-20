package com.tamasha.vedioaudiostreamingapp.ui.dashboard.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tamasha.bottomnavigation.dashboard.play.PlayViewModel
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentPlayBinding

class PlayFragment : Fragment() {

    private lateinit var playViewModel: PlayViewModel
    private var _binding: FragmentPlayBinding? = null

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

        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /* val textView: TextView = binding.textNotifications
         playViewModel.text.observe(viewLifecycleOwner, Observer {
             textView.text = it
         })*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}