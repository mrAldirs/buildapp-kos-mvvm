package com.project.build_kos.views.tenant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.build_kos.adapter.KosAdapterTenant
import com.project.build_kos.api.viewmodel.KosViewModel
import com.project.build_kos.databinding.ActivityMainTenantBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainTenantBinding
    private lateinit var kosViewModel: KosViewModel
    private lateinit var adapter: KosAdapterTenant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Tenant"

        kosViewModel = ViewModelProvider(this).get(KosViewModel::class.java)
        adapter = KosAdapterTenant(arrayListOf())
        binding.rvKos.layoutManager = LinearLayoutManager(this)
        binding.rvKos.adapter = adapter

        kosViewModel.load().observe(this, Observer { kos ->
            adapter.setData(kos)
        })
    }
}