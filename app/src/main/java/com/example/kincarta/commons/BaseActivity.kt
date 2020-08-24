package com.example.kincarta.commons

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.kincarta.R

open class BaseActivity : AppCompatActivity() {

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}