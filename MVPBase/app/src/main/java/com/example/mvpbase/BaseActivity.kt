package com.example.mvpbase

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(){

    fun showToast(message : String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
