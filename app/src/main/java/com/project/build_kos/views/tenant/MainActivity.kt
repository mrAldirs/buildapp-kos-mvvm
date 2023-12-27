package com.project.build_kos.views.tenant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.build_kos.databinding.ActivityMainTenantBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainTenantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Tenant"
    }
}