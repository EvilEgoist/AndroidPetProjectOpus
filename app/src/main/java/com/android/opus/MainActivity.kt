package com.android.opus

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container, MainScreenFragment.newInstance()).commit()
//        }
//        Intent(this, SkillsScreen::class.java)

        val textViewArray = arrayOfNulls<TextView>(10)
        textViewArray[0]

    }
}