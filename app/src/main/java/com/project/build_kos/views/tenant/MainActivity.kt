package com.project.build_kos.views.tenant

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.build_kos.R
import com.project.build_kos.adapter.KosAdapterTenant
import com.project.build_kos.api.viewmodel.KosViewModel
import com.project.build_kos.databinding.ActivityMainTenantBinding
import com.project.build_kos.utils.helper.SharedPreferences
import com.project.build_kos.views.base.SigninActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainTenantBinding
    private lateinit var kosViewModel: KosViewModel
    private lateinit var adapter: KosAdapterTenant
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Tenant"

        preferences = SharedPreferences(this)
        kosViewModel = ViewModelProvider(this).get(KosViewModel::class.java)
        adapter = KosAdapterTenant(arrayListOf())
        binding.rvKos.layoutManager = LinearLayoutManager(this)
        binding.rvKos.adapter = adapter

        kosViewModel.load().observe(this, Observer { kos ->
            adapter.setData(kos)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mn_option -> {
                val popupMenu = PopupMenu(this, findViewById(R.id.mn_option))
                popupMenu.menuInflater.inflate(R.menu.menu_options_tenant, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { popupItem ->
                    when (popupItem.itemId) {
                        R.id.menu_transaction -> {
                            startActivity(Intent(this, TransactionActivity::class.java))
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_logout -> {
                            preferences.remove("id")
                            startActivity(Intent(this, SigninActivity::class.java))
                            finishAffinity()
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }

                popupMenu.show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}