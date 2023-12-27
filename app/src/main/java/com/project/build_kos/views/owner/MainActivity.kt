package com.project.build_kos.views.owner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.build_kos.databinding.ActivityMainOwnerBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainOwnerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainOwnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Owner"
    }
}