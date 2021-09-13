package com.iotric.vedioaudiostreamingapp.ui.meeting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.iotric.vedioaudiostreamingapp.R
import com.iotric.vedioaudiostreamingapp.databinding.ActivityMeetingBinding
import com.iotric.vedioaudiostreamingapp.model.Constants

class MeetingActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMeetingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeetingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val rommDetails = intent.extras?.get(Constants.ROOM_DETAILS)

        //setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_meeting)
        /*appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)*/

        /* binding.fab.setOnClickListener { view ->
             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 .setAction("Action", null).show()
         }*/
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_meeting)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}