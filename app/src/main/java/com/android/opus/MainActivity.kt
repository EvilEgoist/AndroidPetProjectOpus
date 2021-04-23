package com.android.opus

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.android.opus.ui.screen.activityfield.ActivityFieldFragment

class MainActivity : AppCompatActivity(), ActivityFieldFragment.BtnNextClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick() {
        Navigation.findNavController(this, R.id.nav_host_fragment_main)
            .navigate(R.id.action_activityFieldFragment_to_skillsScreenFragment)
    }
}

