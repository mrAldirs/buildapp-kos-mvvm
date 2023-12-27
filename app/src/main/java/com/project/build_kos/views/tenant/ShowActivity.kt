package com.project.build_kos.views.tenant

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.project.build_kos.api.viewmodel.KosViewModel
import com.project.build_kos.databinding.ActivityKosShowTenantBinding
import com.project.build_kos.utils.helper.SharedPreferences
import com.squareup.picasso.Picasso

class ShowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKosShowTenantBinding
    private lateinit var kosViewModel: KosViewModel
    private lateinit var preferences: SharedPreferences
    var price = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKosShowTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail Kos"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        preferences = SharedPreferences(this)
        kosViewModel = ViewModelProvider(this).get(KosViewModel::class.java)

        kosViewModel.show(intent.getStringExtra("id").toString()).observe(this, Observer { data ->
            binding.tvName.text = data.name
            binding.tvAddress.text = "Location: ${data.address}"
            binding.tvPrice.text = "Rp. ${data.price}"
            binding.tvFacility.text = data.facility
            binding.tvRegulation.text = data.regulation
            binding.tvType.text = data.type
            price = data.price
            binding.tvSize.text = "${data.size} m2"
            binding.tvStatus.text = data.status
            Picasso.get().load(data.image).into(binding.imgImage)

            if (data.status == "available") {
                binding.btnBooking.visibility = View.VISIBLE
            } else {
                binding.btnBooking.visibility = View.GONE
            }
        })

        FirebaseFirestore.getInstance().collection("tenant").document(preferences.getString("id", "")).get().addOnSuccessListener { doc ->
            val status = doc.getString("status").toString()
            if (status == "active") {
                binding.btnBooking.visibility = View.VISIBLE
            } else {
                binding.btnBooking.visibility = View.GONE
            }
        }

        binding.btnBooking.setOnClickListener {
            val frag = TransactionFragment()

            val bundle = Bundle()
            bundle.putString("id", intent.getStringExtra("id").toString())
            bundle.putString("price", price)
            frag.arguments = bundle

            frag.show(supportFragmentManager, "transaction")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}