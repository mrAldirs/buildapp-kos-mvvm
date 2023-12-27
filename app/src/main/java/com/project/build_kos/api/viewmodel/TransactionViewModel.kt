package com.project.build_kos.api.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.build_kos.api.repository.TransactionRepository
import com.project.build_kos.models.TransactionModel

class TransactionViewModel : ViewModel() {
    private val repository = TransactionRepository()

    fun create(data: TransactionModel.Transaction) : LiveData<Boolean> {
        return repository.create(data)
    }

    fun load() : LiveData<List<TransactionModel.TransactionDetails>> {
        return repository.load()
    }

    fun loadByTenant(id: String) : LiveData<List<TransactionModel.TransactionDetails>> {
        return repository.loadByTenant(id)
    }

    fun confirm(id: String) : LiveData<Boolean> {
        return repository.confirm(id)
    }

    fun cancel(id: String) : LiveData<Boolean> {
        return repository.cancel(id)
    }

    fun pay(id: String, id_kos: String, id_tenan: String, uri: Uri) : LiveData<Boolean> {
        return repository.pay(id, id_kos, id_tenan, uri)
    }

    fun loadByOwner(id: String) : LiveData<List<TransactionModel.TransactionDetails>> {
        return repository.loadByOwner(id)
    }
}