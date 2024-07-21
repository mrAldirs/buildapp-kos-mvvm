package com.project.build_kos.api.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.build_davina.api.global.Config
import com.project.build_davina.api.global.Data
import com.project.build_kos.models.TransactionModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class TransactionRepository {
    private val firestore = Config.firestore

    fun create(data: TransactionModel.Transaction) : LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String, String>()
        hm.set("id", data.id)
        hm.set("kos_id", data.kos_id)
        hm.set("tenant_id", data.tenant_id)
        hm.set("start", data.start)
        hm.set("end", data.end)
        hm.set("amount", data.amount)
        hm.set("evidence", data.evidence)
        hm.set("status", data.status)
        hm.set("pay", "not paid")

        firestore.collection(Data.transaction).document(data.id).set(hm)
            .addOnSuccessListener {
                firestore.collection(Data.kos).document(data.kos_id).update("status", "booked")
                    .addOnSuccessListener { result.value = true }
                    .addOnFailureListener { result.value = false }
            }
            .addOnFailureListener { result.value = false }

        return result
    }

    fun load() : LiveData<List<TransactionModel.TransactionDetails>> {
        val result = MutableLiveData<List<TransactionModel.TransactionDetails>>()

        firestore.collection(Data.transaction).get()
            .addOnSuccessListener { documents ->
                val data = mutableListOf<TransactionModel.TransactionDetails>()
                for (document in documents) {
                    val id = document.id
                    val kos_id = document.data["kos_id"].toString()
                    val tenant_id = document.data["tenant_id"].toString()
                    val start = document.data["start"].toString()
                    val end = document.data["end"].toString()
                    val amount = document.data["amount"].toString()
                    val evidence = document.data["evidence"].toString()
                    val status = document.data["status"].toString()
                    val pay = document.data["pay"].toString()

                    firestore.collection(Data.kos).whereEqualTo("id", kos_id).get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                val kos_name = document.data["name"].toString()

                                firestore.collection(Data.tenant).whereEqualTo("id", tenant_id).get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            val tenant_name = document.data["name"].toString()

                                            data.add(
                                                TransactionModel.TransactionDetails(
                                                    id,
                                                    kos_id,
                                                    tenant_id,
                                                    start,
                                                    end,
                                                    amount,
                                                    evidence,
                                                    status,
                                                    pay,
                                                    kos_name,
                                                    tenant_name,
                                                )
                                            )
                                        }
                                        result.value = data
                                    }
                            }
                        }
                }
                result.value = data
            }
            .addOnFailureListener { result.value }

        return result
    }

    fun loadByTenant(id: String) : LiveData<List<TransactionModel.TransactionDetails>> {
        val result = MutableLiveData<List<TransactionModel.TransactionDetails>>()

        firestore.collection(Data.transaction).whereEqualTo("tenant_id", id).get()
            .addOnSuccessListener { documents ->
                val data = mutableListOf<TransactionModel.TransactionDetails>()
                for (document in documents) {
                    val id = document.id
                    val kos_id = document.data["kos_id"].toString()
                    val tenant_id = document.data["tenant_id"].toString()
                    val start = document.data["start"].toString()
                    val end = document.data["end"].toString()
                    val amount = document.data["amount"].toString()
                    val evidence = document.data["evidence"].toString()
                    val status = document.data["status"].toString()
                    val pay = document.data["pay"].toString()

                    firestore.collection(Data.kos).whereEqualTo("id", kos_id).get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                val kos_name = document.data["name"].toString()

                                firestore.collection(Data.tenant).whereEqualTo("id", tenant_id).get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            val tenant_name = document.data["name"].toString()

                                            data.add(
                                                TransactionModel.TransactionDetails(
                                                    id,
                                                    kos_id,
                                                    tenant_id,
                                                    start,
                                                    end,
                                                    amount,
                                                    evidence,
                                                    status,
                                                    pay,
                                                    kos_name,
                                                    tenant_name,
                                                )
                                            )
                                        }
                                        result.value = data
                                    }
                            }
                        }
                }
                result.value = data
            }
            .addOnFailureListener { result.value }

        return result
    }

    fun loadByOwner(id: String) : LiveData<List<TransactionModel.TransactionDetails>> {
        val result = MutableLiveData<List<TransactionModel.TransactionDetails>>()

        firestore.collection(Data.kos).whereEqualTo("owner_id", id).get()
            .addOnSuccessListener { documents ->
                val data = mutableListOf<TransactionModel.TransactionDetails>()
                for (document in documents) {
                    val kos_id = document.id

                    firestore.collection(Data.transaction).whereEqualTo("kos_id", kos_id).get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                val id = document.id
                                val kos_id = document.data["kos_id"].toString()
                                val tenant_id = document.data["tenant_id"].toString()
                                val start = document.data["start"].toString()
                                val end = document.data["end"].toString()
                                val amount = document.data["amount"].toString()
                                val evidence = document.data["evidence"].toString()
                                val status = document.data["status"].toString()
                                val pay = document.data["pay"].toString()

                                firestore.collection(Data.kos).whereEqualTo("id", kos_id).get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            val kos_name = document.data["name"].toString()

                                            firestore.collection(Data.tenant).whereEqualTo("id", tenant_id).get()
                                                .addOnSuccessListener { documents ->
                                                    for (document in documents) {
                                                        val tenant_name = document.data["name"].toString()

                                                        data.add(
                                                            TransactionModel.TransactionDetails(
                                                                id,
                                                                kos_id,
                                                                tenant_id,
                                                                start,
                                                                end,
                                                                amount,
                                                                evidence,
                                                                status,
                                                                pay,
                                                                kos_name,
                                                                tenant_name,
                                                            )
                                                        )
                                                    }
                                                    result.value = data
                                                }
                                        }
                                    }
                            }
                            result.value = data
                        }
                }
                result.value = data
            }
            .addOnFailureListener { result.value }

        return result
    }

    fun confirm(id: String) : LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        firestore.collection(Data.transaction).document(id).update("status", "confirmed")
            .addOnSuccessListener { result.value = true }
            .addOnFailureListener { result.value = false }

        return result
    }

    fun cancel(id: String) : LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        firestore.collection(Data.transaction).document(id).update("status", "canceled")
            .addOnSuccessListener { result.value = true }
            .addOnFailureListener { result.value = false }

        return result
    }

    fun pay(id: String, id_kos: String, id_tenant: String, uri: Uri): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val fileName = "IMG" + SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date()) + "new"
        val fileRef = Config.storage.reference.child("$fileName.jpg")

        fileRef.putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                fileRef.downloadUrl
                    .addOnSuccessListener { downloadUrl ->
                        updateTransaction(id, downloadUrl.toString(), result)
                            .addOnSuccessListener {
                                updateKosStatus(id_kos)
                                    .addOnSuccessListener {
                                        updateTenantStatus(id_tenant, result)
                                    }
                                    .addOnFailureListener { result.value = false }
                            }
                            .addOnFailureListener { result.value = false }
                    }
            }
            .addOnFailureListener { result.value = false }

        return result
    }

    private fun updateTransaction(id: String, evidenceUrl: String, result: MutableLiveData<Boolean>) =
        firestore.collection(Data.transaction).document(id)
            .update(mapOf("pay" to "paid", "evidence" to evidenceUrl))

    private fun updateKosStatus(id_kos: String) =
        firestore.collection(Data.kos).whereEqualTo("id", id_kos)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val kos_id = document.id
                    firestore.collection(Data.kos).document(kos_id)
                        .update("status", "no available")
                }
            }

    private fun updateTenantStatus(id_tenant: String, result: MutableLiveData<Boolean>) =
        firestore.collection(Data.tenant).whereEqualTo("id", id_tenant)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val tenant_id = document.id
                    firestore.collection(Data.tenant).document(tenant_id)
                        .update("status", "no active")
                        .addOnSuccessListener { result.value = true }
                        .addOnFailureListener { result.value = false }
                }
            }
            .addOnFailureListener { result.value = false }

}