package com.picpay.desafio.android.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.entity.ResponseHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

class AgendaFragment: Fragment(R.layout.fragment_agenda) {

    private val viewModel by viewModel<AgendaViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.user_list_progress_bar)

        progressBar.visibility = View.VISIBLE
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        setObservers()
    }

    private fun setObservers() {
        viewModel.getContacts().observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ResponseHandler.Success -> {
                        progressBar.visibility = View.GONE
                        adapter.users = it.value
                    }

                    is ResponseHandler.Loading -> {}

                    is ResponseHandler.Error -> {
                        val message = getString(R.string.error)

                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.GONE

                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    is ResponseHandler.Empty -> {
                        Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        )
    }
}