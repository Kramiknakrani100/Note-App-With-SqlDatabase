package com.example.noteappmvvm.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteappmvvm.R
import android.content.Intent
import android.os.Handler
import com.example.noteappmvvm.MainActivity
import java.util.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Objects.requireNonNull(supportActionBar)?.hide()
        Handler().postDelayed({

            // This method will be executed once the timer is over
            val i = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 1000)
    }
}