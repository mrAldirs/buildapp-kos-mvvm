package com.project.build_kos.models

class KosModel {
    data class Kos(
        val id: String,
        val owner_id: String,
        val name: String,
        val price: String,
        val facility: String,
        val address: String,
        val regulation: String,
        val type: String,
        val image: String,
        val count_room: String,
        val size: String,
        val status: String,
    )
}