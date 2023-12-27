package com.project.build_kos.views.owner

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.project.build_kos.api.viewmodel.KosViewModel
import com.project.build_kos.databinding.ActivityKosOwnerInsertBinding
import com.project.build_kos.models.KosModel
import com.project.build_kos.utils.helper.SharedPreferences
import com.squareup.picasso.Picasso
import java.util.*

class KosInsertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKosOwnerInsertBinding
    private lateinit var kosViewModel: KosViewModel
    private lateinit var preferences: SharedPreferences
    lateinit var uri: Uri

    val type = arrayOf("-- Choose Type --","Male","Female","Other")
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKosOwnerInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Insert Kos"
        uri = Uri.EMPTY

        preferences = SharedPreferences(this)
        kosViewModel = ViewModelProvider(this).get(KosViewModel::class.java)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, type)
        binding.inpType.adapter = adapter

        binding.btnChooseImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.setType("image/*")
            startActivityForResult(intent, 1000)
        }

        binding.btnSubmit.setOnClickListener {
            val data = KosModel.Kos(
                UUID.randomUUID().toString().substring(0, 16).replace("-", ""),
                preferences.getString("id", ""),
                binding.inpName.text.toString(),
                binding.inpPrice.text.toString(),
                binding.inpFacility.text.toString(),
                binding.inpAddress.text.toString(),
                binding.inpRegulation.text.toString(),
                binding.inpType.selectedItem.toString(),
                "",
                binding.inpCountRoom.text.toString(),
                binding.inpSize.text.toString(),
                "available"
            )

            kosViewModel.create(data, uri).observe(this, androidx.lifecycle.Observer { response ->
                if (response) {
                    Toast.makeText(this, "Success add new kos!", Toast.LENGTH_SHORT).show()
                    binding.inpName.text?.clear()
                    binding.inpPrice.text?.clear()
                    binding.inpFacility.text?.clear()
                    binding.inpAddress.text?.clear()
                    binding.inpRegulation.text?.clear()
                    binding.inpType.setSelection(0)
                    binding.inpCountRoom.text?.clear()
                    binding.inpSize.text?.clear()
                } else {
                    Toast.makeText(this, "Failed add new kos!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((resultCode == Activity.RESULT_OK) && (requestCode == 1000)) {
            if (data != null){
                uri = data.data!!
                Picasso.get().load(uri.toString()).into(binding.inpImage)
            }
        }
    }
}