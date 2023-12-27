package com.project.build_kos.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.build_kos.R
import com.project.build_kos.models.TransactionModel
import com.project.build_kos.views.tenant.PayFragment
import com.project.build_kos.views.tenant.TransactionActivity

class TransactionAdapterTenant(private var dataList: List<TransactionModel.TransactionDetails>, val listener: TransactionActivity) :
    RecyclerView.Adapter<TransactionAdapterTenant.HolderData>(){
    class HolderData (v : View) : RecyclerView.ViewHolder(v) {
        val name = v.findViewById<TextView>(R.id.item_name)
        val kos = v.findViewById<TextView>(R.id.item_kos)
        val start = v.findViewById<TextView>(R.id.item_start)
        val end = v.findViewById<TextView>(R.id.item_end)
        val status = v.findViewById<TextView>(R.id.item_status)
        val amount = v.findViewById<TextView>(R.id.item_amount)
        val confirm = v.findViewById<Button>(R.id.item_btn_pay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_tenant, parent, false)
        return HolderData(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val data = dataList.get(position)
        holder.name.text = data.tenant_name
        holder.kos.text = data.kos_name
        holder.start.text = "Start: ${data.start}"
        holder.end.text = "End: ${data.end}"
        holder.status.text = "Status: ${data.status}"
        holder.amount.text = "Amount: ${data.amount}/month (${data.pay})"

        if (data.pay == "not paid") {
            holder.confirm.visibility = View.VISIBLE
        } else {
            holder.confirm.visibility = View.GONE
        }
        holder.confirm.setOnClickListener {
            val frag = PayFragment()

            val bundle = Bundle()
            bundle.putString("id", data.id)
            bundle.putString("id_kos", data.kos_id)
            bundle.putString("name", data.tenant_name)
            bundle.putString("amount", data.amount)
            frag.arguments = bundle

            frag.show(listener.supportFragmentManager, "pay")
        }
    }

    fun setData(newDataList: List<TransactionModel.TransactionDetails>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}