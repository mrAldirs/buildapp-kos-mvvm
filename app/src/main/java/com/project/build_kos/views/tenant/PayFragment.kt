package com.project.build_kos.views.tenant

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.build_kos.api.viewmodel.TransactionViewModel
import com.project.build_kos.databinding.FragmentPayTenantBinding
import com.project.build_kos.utils.helper.SharedPreferences
import com.squareup.picasso.Picasso

class PayFragment : DialogFragment() {
    private lateinit var binding: FragmentPayTenantBinding
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var preferences: SharedPreferences
    private lateinit var v: View
    lateinit var uri: Uri
    
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPayTenantBinding.inflate(inflater, container, false)
        v = binding.root
        uri = Uri.EMPTY
        
        preferences = SharedPreferences(v.context)
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        
        val id = arguments?.getString("id").toString()
        val id_kos = arguments?.getString("id_kos").toString()
        val name = arguments?.getString("name").toString()
        val amount = arguments?.getString("amount").toString()

        binding.inpName.setText(name)
        binding.inpAmount.setText("Rp. $amount")

        binding.btnChooseImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.setType("image/*")
            startActivityForResult(intent, 1000)
        }
        
        binding.btnSubmit.setOnClickListener {
            transactionViewModel.pay(id, id_kos, arguments?.getString("id", "").toString(), uri).observe(this, Observer {
                Toast.makeText(v.context, "Successful payment!", Toast.LENGTH_SHORT).show()
            })
            Toast.makeText(v.context, "Successful payment!", Toast.LENGTH_SHORT).show()
            dismiss()
            requireActivity().recreate()
        }
        
        return v
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