package com.project.build_kos.api.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.build_kos.api.repository.KosRepository
import com.project.build_kos.models.KosModel

class KosViewModel : ViewModel() {
    private val repository = KosRepository()

    fun create(data: KosModel.Kos, uri: Uri) : LiveData<Boolean> {
        return repository.create(data, uri)
    }

    fun load() : LiveData<List<KosModel.Kos>> {
        return repository.load()
    }

    fun loadByOwner(owner_id: String) : LiveData<List<KosModel.Kos>> {
        return repository.loadByOwner(owner_id)
    }

    fun show(id: String) : LiveData<KosModel.Kos> {
        return repository.show(id)
    }
}