package com.insane.whatsthebra.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.insane.whatsthebra.service.MainService
import com.insane.whatsthebra.R
import com.insane.whatsthebra.database.AppDataBase
import com.insane.whatsthebra.interfaces.DataCallBack
import com.insane.whatsthebra.utils.Tools

class SplashScreen : AppCompatActivity(), DataCallBack {


    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val db = AppDataBase.getDataBase(this)

        // Check Internet Status
        if (Tools.Connection.internetStatus(this)) {
            MainService.loadData(db, this)
        } else if (db.productDao().getAll().isNotEmpty()) {
            Tools.Show.message(this, this.getString(R.string.offlineData))
            MainService.loadDataOffline(db, this)
        } else {
            noInternet()
        }

    }

    /**
     * Start Application check internet status and load data
     */
    private fun noInternet() {

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val modal = findViewById<ConstraintLayout>(R.id.constraintModalInternet)
        val tvMessage = findViewById<TextView>(R.id.tv_splash_message)
        val btnCancel = findViewById<Button>(R.id.btn_splash_cancel)
        val btnConnect = findViewById<Button>(R.id.btn_splash_connect)

        // Check Internet Status
        if (Tools.Connection.internetStatus(this)) {
            // Load data from API
            MainService.loadData(AppDataBase.getDataBase(this), this)
        } else {
            progressBar.visibility = View.GONE
            modal.visibility = View.VISIBLE
            tvMessage.text = this.getString(R.string.noInternet)

            btnCancel.setOnClickListener {
                this.finish()
            }

            btnConnect.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                modal.visibility = View.GONE
                noInternet()
            }

        }
    }

    /**
     * CallBack method from loading data
     */
    override fun onDataLoaded() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

}