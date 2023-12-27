package com.project.build_kos.views.tenant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.build_kos.api.viewmodel.TransactionViewModel
import com.project.build_kos.databinding.FragmentTransactionTenantBinding
import com.project.build_kos.models.TransactionModel
import com.project.build_kos.utils.helper.DatePickerHelper
import com.project.build_kos.utils.helper.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class TransactionFragment : DialogFragment() {
    private lateinit var binding: FragmentTransactionTenantBinding
    private lateinit var datePickerHelper: DatePickerHelper
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var preferences: SharedPreferences
    private lateinit var v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransactionTenantBinding.inflate(inflater, container, false)
        v = binding.root

        preferences = SharedPreferences(v.context)
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        datePickerHelper = DatePickerHelper(v.context)

        binding.inpStart.setOnClickListener {
            datePickerHelper.showDatePickerDialog(binding.inpStart)
        }

        binding.inpEnd.setOnClickListener {
            datePickerHelper.showDatePickerDialog(binding.inpEnd)
        }

        val kode = arguments?.getString("id").toString()
        val price = arguments?.getString("price").toString().toInt()

        binding.btnSubmit.setOnClickListener {
            val month = binding.inpMonth.text.toString().toInt()

            val amount = month * price

            val data = TransactionModel.Transaction(
                UUID.randomUUID().toString().substring(0, 24).replace("-", ""),
                kode,
                preferences.getString("id", ""),
                binding.inpStart.text.toString(),
                binding.inpEnd.text.toString(),
                amount.toString(),
                "",
                "not paid"
            )

            transactionViewModel.create(data).observe(requireActivity(), Observer {
                requireActivity().recreate()
                dismiss()
                Toast.makeText(v.context, "Success booking this kos!", Toast.LENGTH_SHORT).show()
            })
        }

        return v
    }
}