package com.project.build_kos.views.tenant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.build_kos.adapter.TransactionAdapterTenant
import com.project.build_kos.api.viewmodel.TransactionViewModel
import com.project.build_kos.databinding.ActivityTransactionTenantBinding
import com.project.build_kos.utils.helper.SharedPreferences

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionTenantBinding
    private lateinit var adapter: TransactionAdapterTenant
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Your Transaction"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        preferences = SharedPreferences(this)
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        adapter = TransactionAdapterTenant(listOf(), this)
        binding.rvTransaction.layoutManager = LinearLayoutManager(this)
        binding.rvTransaction.adapter = adapter

        transactionViewModel.loadByTenant(preferences.getString("id", "")).observe(this, Observer {
            adapter.setData(it)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}