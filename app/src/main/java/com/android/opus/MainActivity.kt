package com.android.opus

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.opus.ui.screen.MainScreenFragment
import com.android.opus.ui.screen.activityfield.ActivityFieldFragment
import com.android.opus.ui.screen.skillscreen.SkillsScreenFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container, MainScreenFragment.newInstance()).commit()
//        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, SkillsScreenFragment.newInstance()).commit()
        }
    }
}
