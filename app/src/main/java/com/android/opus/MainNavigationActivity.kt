package com.android.opus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_lists.*

class MainNavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)
        setupViews()
    }

    private fun setupViews() {
        val navController = findNavController(R.id.nav_host_container)
        bottomNav.setupWithNavController(navController)

        setSupportActionBar(toolbar)
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf (
                R.id.vacancyScreenFragment,
                R.id.resumeScreenFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
