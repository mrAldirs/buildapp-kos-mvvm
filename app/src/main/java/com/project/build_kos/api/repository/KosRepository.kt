package com.project.build_kos.api.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.build_davina.api.global.Config
import com.project.build_davina.api.global.Data
import com.project.build_kos.models.KosModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class KosRepository {
    private val firestore = Config.firestore

    fun create(data: KosModel.Kos, uri: Uri) : LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val hm = HashMap<String, Any>()
        hm.set("id", data.id)
        hm.set("owner_id", data.owner_id)
        hm.set("name", data.name)
        hm.set("price", data.price)
        hm.set("facility", data.facility)
        hm.set("address", data.address)
        hm.set("regulation", data.regulation)
        hm.set("type", data.type)
        hm.set("count_room", data.count_room)
        hm.set("size", data.size)
        hm.set("status", data.status)

        firestore.collection(Data.kos).document(data.id).set(hm)
            .addOnSuccessListener {
                val fileName = "IMG" + SimpleDateFormat("yyyyMMddHHmmssSSS").format(Date()) + "new"
                val fileRef = Config.storage.reference.child(fileName + ".jpg")

                fileRef.putFile(uri)
                    .addOnSuccessListener {
                        fileRef.downloadUrl.addOnSuccessListener {
                            val hm = HashMap<String, Any>()
                            hm.set("image", it.toString())
                            firestore.collection(Data.kos).document(data.id).update(hm)
                                .addOnSuccessListener { result.value = true }
                                .addOnFailureListener { result.value = false }
                        }
                    }
                    .addOnFailureListener { result.value = false }
            }
            .addOnFailureListener { result.value = false }

        return result
    }

    fun load() : LiveData<List<KosModel.Kos>> {
        val result = MutableLiveData<List<KosModel.Kos>>()

        firestore.collection(Data.kos).get()
            .addOnSuccessListener { documents ->
                val data = mutableListOf<KosModel.Kos>()
                for (document in documents) {
                    val id = document.id
                    val owner_id = document.data["owner_id"].toString()
                    val name = document.data["name"].toString()
                    val price = document.data["price"].toString()
                    val facility = document.data["facility"].toString()
                    val address = document.data["address"].toString()
                    val regulation = document.data["regulation"].toString()
                    val type = document.data["type"].toString()
                    val image = document.data["image"].toString()
                    val count_room = document.data["count_room"].toString()
                    val size = document.data["size"].toString()
                    val status = document.data["status"].toString()

                    data.add(KosModel.Kos(id, owner_id, name, price, facility, address, regulation, type, image, count_room, size, status))
                }
                result.value = data
            }
            .addOnFailureListener { result.value = listOf() }

        return result
    }

    fun loadByOwner(owner_id: String) : LiveData<List<KosModel.Kos>> {
        val result = MutableLiveData<List<KosModel.Kos>>()

        firestore.collection(Data.kos).whereEqualTo("owner_id", owner_id).get()
            .addOnSuccessListener { documents ->
                val data = mutableListOf<KosModel.Kos>()
                for (document in documents) {
                    val id = document.id
                    val owner_id = document.data["owner_id"].toString()
                    val name = document.data["name"].toString()
                    val price = document.data["price"].toString()
                    val facility = document.data["facility"].toString()
                    val address = document.data["address"].toString()
                    val regulation = document.data["regulation"].toString()
                    val type = document.data["type"].toString()
                    val image = document.data["image"].toString()
                    val count_room = document.data["count_room"].toString()
                    val size = document.data["size"].toString()
                    val status = document.data["status"].toString()

                    data.add(KosModel.Kos(id, owner_id, name, price, facility, address, regulation, type, image, count_room, size, status))
                }
                result.value = data
            }
            .addOnFailureListener { result.value = listOf() }

        return result
    }

    fun show(id: String) : LiveData<KosModel.Kos> {
        val result = MutableLiveData<KosModel.Kos>()

        firestore.collection(Data.kos).document(id).get()
            .addOnSuccessListener { document ->
                val id = document.id
                val owner_id = document.data?.get("owner_id").toString()
                val name = document.data?.get("name").toString()
                val price = document.data?.get("price").toString()
                val facility = document.data?.get("facility").toString()
                val address = document.data?.get("address").toString()
                val regulation = document.data?.get("regulation").toString()
                val type = document.data?.get("type").toString()
                val image = document.data?.get("image").toString()
                val count_room = document.data?.get("count_room").toString()
                val size = document.data?.get("size").toString()
                val status = document.data?.get("status").toString()

                result.value = KosModel.Kos(id, owner_id, name, price, facility, address, regulation, type, image, count_room, size, status)
            }
            .addOnFailureListener { result.value = null }

        return result
    }
}