package com.ianpedraza.dogedex.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ianpedraza.dogedex.databinding.ActivityMainBinding
import com.ianpedraza.dogedex.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
