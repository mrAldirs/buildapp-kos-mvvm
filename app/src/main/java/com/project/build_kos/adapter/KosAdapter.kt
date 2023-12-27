package com.project.build_kos.adapter

import android.annotation.SuppressLint
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
import com.project.build_kos.models.KosModel
import com.squareup.picasso.Picasso

class KosAdapter(private var dataList: List<KosModel.Kos>) :
    RecyclerView.Adapter<KosAdapter.HolderData>(){
    class HolderData (v : View) : RecyclerView.ViewHolder(v) {
        val image = v.findViewById<ImageView>(R.id.item_image)
        val type = v.findViewById<TextView>(R.id.item_type)
        val status = v.findViewById<TextView>(R.id.item_status)
        val name = v.findViewById<TextView>(R.id.item_name)
        val price = v.findViewById<TextView>(R.id.item_price)
        val detail = v.findViewById<Button>(R.id.item_btn_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_kos, parent, false)
        return HolderData(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val data = dataList.get(position)
        holder.type.text = data.type
        holder.name.text = data.name
        holder.status.text = data.status
        Picasso.get().load(data.image).into(holder.image)
        holder.price.text = "Rp. ${data.price}"
        holder.detail.setOnClickListener {
            it.context.startActivity(Intent(it.context, com.project.build_kos.views.tenant.ShowActivity::class.java).apply {
                putExtra("id", data.id)
            })
        }
    }

    fun setData(newDataList: List<KosModel.Kos>) {
        dataList = newDataList
        notifyDataSetChanged()
    }


}