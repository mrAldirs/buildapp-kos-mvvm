package com.project.build_kos.views.base

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.build_kos.api.viewmodel.UserViewModel
import com.project.build_kos.databinding.ActivitySignupBinding
import com.project.build_kos.models.UserModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Sign Up"
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val db = Firebase.firestore
        val optionsCollection = db.collection("role")

        optionsCollection.get()
            .addOnSuccessListener { documents ->
                val optionsList = mutableListOf<String>()

                for (document in documents) {
                    val optionValue = document.getString("name")
                    optionValue?.let {
                        optionsList.add(it)
                    }
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, optionsList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.inpRole.adapter = adapter
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Gagal mengambil data dari Firestore: ${e.message}", Toast.LENGTH_LONG).show()
            }

        binding.btnSignup.setOnClickListener {
            val email = binding.inpEmail.text.toString()
            val password = binding.inpPassword.text.toString()
            val selectedOption: String = binding.inpRole.selectedItem.toString()

            userViewModel.registerUser(email, password) { success, id ->
                if (success) {
                    val dataList = id?.let { it1 ->
                        UserModel.User(
                            it1, email,password, selectedOption
                        )
                    }

                    dataList?.let { it1 ->
                        userViewModel.createUser(it1).observe(this, Observer {
                            Toast.makeText(this, "Success in create new account", Toast.LENGTH_SHORT).show()
                        })
                    }

                    val data = UserModel.Profile(
                        id.toString(),
                        binding.inpName.text.toString(),
                        binding.inpPhone.text.toString(),
                        binding.inpAddress.text.toString()
                    )

                    if (selectedOption.equals("Owner")) {
                        userViewModel.createSingle(data, "owner")
                            .observe(this, Observer {
                                onBackPressed()
                                Toast.makeText(this, "Please validate your email!", Toast.LENGTH_SHORT).show()
                            })
                    } else if (selectedOption.equals("Tenant")) {
                        userViewModel.createSingle(data, "tenant")
                            .observe(this, Observer {
                                onBackPressed()
                                Toast.makeText(this, "Please validate your email!", Toast.LENGTH_SHORT).show()
                            })
                    } else {
                        onBackPressed()
                        Toast.makeText(this, "Please validate your email!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Failed to create account.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}