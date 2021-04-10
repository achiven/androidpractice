package com.example.baseapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.baseapplication.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        if(supportFragmentManager.fragments.size == 0){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment::class.java, null)
                .commit()
        }
    }
}