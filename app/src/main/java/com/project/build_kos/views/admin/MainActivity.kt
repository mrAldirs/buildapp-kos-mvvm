package com.project.build_kos.views.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.build_kos.databinding.ActivityMainAdminBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Admin"
    }
}