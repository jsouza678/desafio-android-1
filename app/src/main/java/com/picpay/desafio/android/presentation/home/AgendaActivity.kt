package com.picpay.desafio.android.presentation.home

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.UserListAdapter
import com.picpay.desafio.android.domain.entity.ResponseHandler.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class AgendaActivity : AppCompatActivity(R.layout.activity_main), KoinComponent {

    private val viewModel by viewModel<AgendaViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    override fun onResume() {
        super.onResume()

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        progressBar.visibility = View.VISIBLE
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        setObservers()
    }

    private fun setObservers() {
        viewModel.getContacts().observe(
            this,
            Observer {
                when (it) {
                    is Success -> {
                        progressBar.visibility = View.GONE
                        adapter.users = it.value
                    }

                    is Loading -> {}

                    is Error -> {
                        val message = getString(R.string.error)

                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.GONE

                        Toast.makeText(this@AgendaActivity, message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    is Empty -> {
                        Toast.makeText(this@AgendaActivity, "Empty", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        )
    }
}
