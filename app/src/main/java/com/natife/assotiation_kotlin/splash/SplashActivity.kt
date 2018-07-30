package com.natife.assotiation_kotlin.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.initgame.InitGameActivity.Companion.start

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            start(this@SplashActivity)
            finish()
        }, 500)
    }


    override fun onBackPressed() {}
}