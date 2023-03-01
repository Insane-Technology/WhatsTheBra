package com.insane.whatsthebra

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.insane.service.MainService

class SplashScreen : AppCompatActivity(), MainService.DataCallBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Load data from API
        MainService.loadData(this)
    }

    /**
     * CallBack method from loading data
     */
    override fun onDataLoaded() {
        val intent = Intent(this, MainActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
        }, 1000)
    }

}