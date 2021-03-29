package com.android.opus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.opus.ui.screen.MainScreenFragment
import com.android.opus.ui.screen.vacancy.VacancyScreenFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_container, VacancyScreenFragment.newInstance()).commit()
        }
    }
}
