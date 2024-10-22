package com.example.osmdemo.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.osmdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(this, android.preference.PreferenceManager.getDefaultSharedPreferences(this))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}