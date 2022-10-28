package com.dmitriy.instagramclone.app.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.dmitriy.instagramclone.R
import com.dmitriy.instagramclone.app.screens.main.splash.SplashFragment
import com.dmitriy.instagramclone.app.screens.main.tabs.TabsFragment
import com.dmitriy.instagramclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null

    private val topLevelDestinations = setOf(
        R.id.tabsFragment,
        R.id.splashFragment,
        R.id.signInFragment
    )

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
            super.onFragmentStarted(fm, f)
            if (f is TabsFragment || f is NavHostFragment || f is SplashFragment) {
                return
            }
            setActionBarData(f.findNavController())
        }

        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is TabsFragment || f is NavHostFragment) {
                return
            }
            onNavControllerActivated(f.findNavController())
        }

        override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
            super.onFragmentStopped(fm, f)
            if (f is TabsFragment || f is NavHostFragment || f is SplashFragment) {
                return
            }
            clearActionBarData()
        }
    }

    private val onBackPressedCallback = onBackPressedDispatcher.addCallback(this) {
        if (isStartDestination(navController?.currentDestination)) {
            finish()
        } else {
            navController?.popBackStack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        setSupportActionBar(binding.toolbar)

        // what is it

        val navController = rootNavController()
        prepareRootNavController(navController)
        clearActionBarData()
        onNavControllerActivated(navController)

        onBackPressedCallback.isEnabled = true
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        navController = null
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        return (navController?.navigateUp() ?: false) || super.onSupportNavigateUp()
    }

    private fun rootNavController(): NavController {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }

    private fun prepareRootNavController(navController: NavController) {
        val graph = navController.navInflater.inflate(getMainNavigationGraph())
        graph.setStartDestination(getSplashDestination())
        navController.graph = graph
    }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        val graph = destination.parent ?: return false
        val startDestinations = topLevelDestinations + graph.startDestinationId
        return startDestinations.contains(destination.id)
    }

    private fun onNavControllerActivated(navController: NavController) {
        if (this.navController == navController) return
        this.navController = navController
        onNavigateUpActivated(navController)
    }

    private fun onNavigateUpActivated(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setNavigationOnClickListener { _ ->
            NavigationUI.navigateUp(navController, appBarConfiguration)
        }
    }

    private fun setActionBarData(navController: NavController) {
        binding.toolbar.visibility = View.VISIBLE
        supportActionBar?.title = navController.currentDestination?.label
        if (navController.currentDestination == null ||
            isStartDestination(navController.currentDestination)) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            return
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun clearActionBarData() {
        binding.toolbar.visibility = View.GONE
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    fun isSignedIn(): Boolean {
        return false
    }

    private fun getSplashDestination() = R.id.splashFragment
    private fun getMainNavigationGraph() = R.navigation.main_graph
}