package com.project.build_kos.views.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.build_kos.adapter.TransactionAdapter
import com.project.build_kos.api.viewmodel.TransactionViewModel
import com.project.build_kos.databinding.ActivityTransactionAdminBinding

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionAdminBinding
    private lateinit var adapter: TransactionAdapter
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Transaction"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        adapter = TransactionAdapter(listOf(), this)
        binding.rvTransactionAdmin.layoutManager = LinearLayoutManager(this)
        binding.rvTransactionAdmin.adapter = adapter

        transactionViewModel.load().observe(this, Observer {
            adapter.setData(it)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun confirm(id: String) {
        transactionViewModel.confirm(id).observe(this, Observer {
            if (it) {
                transactionViewModel.load().observe(this, Observer {
                    adapter.setData(it)
                })
            }
        })
    }

    fun cancel(id: String) {
        transactionViewModel.cancel(id).observe(this, Observer {
            if (it) {
                transactionViewModel.load().observe(this, Observer {
                    adapter.setData(it)
                })
            }
        })
    }
}