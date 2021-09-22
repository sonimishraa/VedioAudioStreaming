package com.tamasha.vedioaudiostreamingapp.uis

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.tamasha.vedioaudiostreamingapp.R
import com.tamasha.vedioaudiostreamingapp.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity()/*, NavigationView.OnNavigationItemSelectedListener*/ {
    private lateinit var binding: ActivityHomeBinding
    lateinit var appBarConfig: AppBarConfiguration
    lateinit var drawerLayout: DrawerLayout
    lateinit var mAppBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initView()
        initNavigation()
//        initListener()
    }

    private fun initView() {
        drawerLayout = binding.drawerLayout
        val headerView = binding.navView.inflateHeaderView(R.layout.nav_drawer_header)
        val btn_closeDrawer = headerView.findViewById<AppCompatImageView>(R.id.close_drawer)
        btn_closeDrawer.setOnClickListener {
            drawerLayout.close()
        }
    }

    private fun initNavigation() {
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_leaderboard, R.id.nav_help,
            R.id.navigation_discover, R.id.navigation_club,
            R.id.navigation_play, R.id.navigation_refresh,
            R.id.navigation_home
        ).setDrawerLayout(drawerLayout).build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration )
        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(binding.appbar.contentMain.bottomNavView, navController)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.appbar.toolbar)
    }

//    private fun initView() {
//        /* if (binding.drawerLayout != null) {
//             binding.navView.setNavigationItemSelectedListener(this)
//         }*/
//        val headerView = binding.navView.inflateHeaderView(R.layout.nav_drawer_header)
//        drawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
//        val bottomNavView: BottomNavigationView = binding.bottomNavView
//
//        val navController = findNavController(R.id.nav_host_fragment)
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.navigation_discover, R.id.navigation_club, R.id.navigation_play, R.id.navigation_refresh -> showAppbar()
//                R.id.navigation_home -> showAppbar()
//                else -> showAppbar()
//            }
//        }
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        /* val appBarConfiguration = AppBarConfiguration(
//             setOf(
//                 R.id.navigation_home,
//             ), drawerLayout
//         )
//         setupActionBarWithNavController(navController, appBarConfiguration)*/
//        bottomNavView.setupWithNavController(navController)
//        navView.setupWithNavController(navController)
//    }

   /* private fun hideAppbar() {
        binding.appBar.appbar.visibility = View.GONE
    }

    private fun showAppbar() {
        binding.appBar.appbar.visibility = View.VISIBLE
    }*/

   /* private fun initListener() {
        binding.appBar.menubar.setOnClickListener {
            val drawer = binding.drawerLayout
            binding.bottomNavView.visibility = View.GONE
            drawer.open()
        }
    }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController( R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp()
        return navController.navigateUp(appBarConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_toolbar_menu, menu)
        return true
    }


    /*override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.close()
        val id = item.itemId
        when (id) {
            R.id.nav_leaderboard -> {
                //findNavController().navigate(R.id.action_profile_fragment)

            }
            R.id.nav_help -> {
                //findNavController().navigate(R.id.action_lab_fragment)

            }
            R.id.nav_logout -> {
                //findNavController().navigate(R.id.action_notifications)

            }
        }
        return true
    }*/

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}