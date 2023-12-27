package com.project.build_kos.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.build_kos.R
import com.project.build_kos.models.TransactionModel
import com.project.build_kos.views.admin.TransactionActivity
import com.squareup.picasso.Picasso

class TransactionAdapter(private var dataList: List<TransactionModel.TransactionDetails>, val listener: TransactionActivity) :
    RecyclerView.Adapter<TransactionAdapter.HolderData>(){
    class HolderData (v : View) : RecyclerView.ViewHolder(v) {
        val name = v.findViewById<TextView>(R.id.item_name)
        val kos = v.findViewById<TextView>(R.id.item_kos)
        val start = v.findViewById<TextView>(R.id.item_start)
        val end = v.findViewById<TextView>(R.id.item_end)
        val status = v.findViewById<TextView>(R.id.item_status)
        val amount = v.findViewById<TextView>(R.id.item_amount)
        val cancel = v.findViewById<Button>(R.id.item_btn_cancel)
        val confirm = v.findViewById<Button>(R.id.item_btn_confirm)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
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

        if (data.status == "pending") {
            holder.cancel.visibility = View.VISIBLE
            holder.confirm.visibility = View.VISIBLE
        } else {
            holder.cancel.visibility = View.GONE
            holder.confirm.visibility = View.GONE
        }
        holder.cancel.setOnClickListener {
            AlertDialog.Builder(listener)
                .setTitle("Cancel Transaction")
                .setMessage("Are you sure want to cancel this transaction?")
                .setPositiveButton("Yes") { dialog, which ->
                    listener.cancel(data.id)
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
        holder.confirm.setOnClickListener {
            AlertDialog.Builder(listener)
                .setTitle("Confirm Transaction")
                .setMessage("Are you sure want to confirm this transaction?")
                .setPositiveButton("Yes") { dialog, which ->
                    listener.confirm(data.id)
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    fun setData(newDataList: List<TransactionModel.TransactionDetails>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}