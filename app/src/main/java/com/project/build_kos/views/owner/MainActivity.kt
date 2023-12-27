package com.project.build_kos.views.owner

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.build_kos.R
import com.project.build_kos.adapter.KosAdapter
import com.project.build_kos.api.viewmodel.KosViewModel
import com.project.build_kos.databinding.ActivityMainOwnerBinding
import com.project.build_kos.utils.helper.SharedPreferences

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainOwnerBinding
    private lateinit var kosViewModel: KosViewModel
    private lateinit var adapter: KosAdapter
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainOwnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Dashboard Owner"

        preferences = SharedPreferences(this)
        kosViewModel = ViewModelProvider(this).get(KosViewModel::class.java)
        adapter = KosAdapter(arrayListOf())
        binding.rvKos.layoutManager = LinearLayoutManager(this)
        binding.rvKos.adapter = adapter

        kosViewModel.loadByOwner(preferences.getString("id", "")).observe(this, Observer { kos ->
            adapter.setData(kos)
        })

        binding.btnInsert.setOnClickListener {
            startActivity(Intent(this, KosInsertActivity::class.java))
        }

        binding.bottomNavigasi.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_kos -> {
                    supportActionBar?.setTitle("Dashboard Owner")
                    binding.frameLayout.visibility = View.GONE
                }
                R.id.nav_history -> {
                    supportActionBar?.setTitle("Activity History")
                    var frag = HistoryFragment()

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, frag).commit()
                    binding.frameLayout.setBackgroundColor(Color.argb(255,255,255,255))
                    binding.frameLayout.visibility = View.VISIBLE
                }
                R.id.nav_complain -> {

                }
            }
            true
        }

    }
}