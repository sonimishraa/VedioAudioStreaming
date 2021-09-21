package com.tamasha.vedioaudiostreamingapp.uis.club

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tamasha.vedioaudiostreamingapp.databinding.FragmentClubBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubFragment : Fragment() {

    private lateinit var refreshEarnViewModel: ClubViewModel
    private var _binding: FragmentClubBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        refreshEarnViewModel =
            ViewModelProvider(this).get(ClubViewModel::class.java)

        _binding = FragmentClubBinding.inflate(inflater, container, false)
        val root: View = binding.root

      /*  val textView: TextView = binding.textNotifications
        refreshEarnViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}