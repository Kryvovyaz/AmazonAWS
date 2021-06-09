package com.vlad_kryvovyaz.amazonaws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vlad_kryvovyaz.amazonaws.databinding.ActivityAmazonawsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AmazonAWSActivity : AppCompatActivity() {
    private var _binding: ActivityAmazonawsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAmazonawsBinding.inflate(layoutInflater)
        setContentView(_binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}