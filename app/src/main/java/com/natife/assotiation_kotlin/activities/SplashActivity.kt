package com.natife.assotiation_kotlin.activities

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

import com.natife.assotiation_kotlin.MainActivity
import com.natife.assotiation_kotlin.R


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            MainActivity.start(this@SplashActivity)
            finish()
        }, 200)
    }


    override fun onBackPressed() {}
}
