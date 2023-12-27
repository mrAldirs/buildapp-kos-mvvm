package com.project.build_kos.models

class TransactionModel {
    data class Transaction(
        val id: String,
        val kos_id: String,
        val tenant_id: String,
        val start: String,
        val end: String,
        val amount: String,
        val evidence: String,
        val status: String
    )

    data class TransactionDetails(
        val id: String,
        val kos_id: String,
        val tenant_id: String,
        val start: String,
        val end: String,
        val amount: String,
        val evidence: String,
        val status: String,
        val pay: String,
        val kos_name: String,
        val tenant_name: String,
    )
}