package com.javr.medic_route.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.javr.medic_route.R
import com.javr.medic_route.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}