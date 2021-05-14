package com.picpay.desafio.android.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.base.BaseFragment
import com.picpay.desafio.android.databinding.FragmentAgendaBinding
import com.picpay.desafio.android.domain.entity.ResponseHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

class AgendaFragment: BaseFragment<FragmentAgendaBinding>() {

    private val viewModel by viewModel<AgendaViewModel>()
    private lateinit var adapter: UserListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userListProgressBar.visibility = View.VISIBLE
        adapter = UserListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        setObservers()
    }

    override fun getViewBinding(): FragmentAgendaBinding {
        return FragmentAgendaBinding.inflate(layoutInflater)
    }

    private fun setObservers() {
        viewModel.getContacts().observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ResponseHandler.Success -> {
                        binding.userListProgressBar.visibility = View.GONE
                        adapter.users = it.value
                    }

                    is ResponseHandler.Loading -> {}

                    is ResponseHandler.Error -> {
                        val message = getString(R.string.error)

                        binding.userListProgressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE

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