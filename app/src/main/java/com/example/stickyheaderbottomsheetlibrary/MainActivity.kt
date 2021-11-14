package com.example.stickyheaderbottomsheetlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        findViewById<Button>(R.id.btnOpen).setOnClickListener {
            val featureBottomSheet = FeatureBottomSheet()
            featureBottomSheet.show(supportFragmentManager, "feature1")
        }
    }
}