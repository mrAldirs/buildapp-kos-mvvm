package com.project.build_kos.views.owner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.build_kos.adapter.TransactionAdapter
import com.project.build_kos.adapter.TransactionAdapterOwner
import com.project.build_kos.api.viewmodel.TransactionViewModel
import com.project.build_kos.databinding.FragmentHistoryTransactionBinding

class HistoryFragment : Fragment() {
    lateinit var binding: FragmentHistoryTransactionBinding
    lateinit var v: View
    private lateinit var adapter: TransactionAdapterOwner
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHistoryTransactionBinding.inflate(inflater, container, false)
        v = binding.root

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        adapter = TransactionAdapterOwner(listOf())
        binding.rvTransaction.layoutManager = LinearLayoutManager(v.context)
        binding.rvTransaction.adapter = adapter

        transactionViewModel.load().observe(requireActivity(), Observer {
            adapter.setData(it)
        })

        return v
    }
}