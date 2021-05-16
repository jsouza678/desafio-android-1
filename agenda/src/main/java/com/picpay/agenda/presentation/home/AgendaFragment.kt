package com.picpay.agenda.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.picpay.agenda.databinding.FragmentAgendaBinding
import com.picpay.base.extensions.showErrorSnackbar
import com.picpay.base.presentation.BaseFragment
import com.picpay.domain.entity.ApiError
import com.picpay.domain.entity.ResponseHandler
import com.picpay.domain.entity.User
import com.picpay.sharedcomponents.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class AgendaFragment: BaseFragment<FragmentAgendaBinding>() {

    private val viewModel by viewModel<AgendaViewModel>()
    private lateinit var adapter: UserListAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserListAdapter()
        setupView()
        setupRecyclerView()
        setObservers()
    }

    override fun getViewBinding(): FragmentAgendaBinding {
        return FragmentAgendaBinding.inflate(layoutInflater)
    }

    private fun setupView() {
        binding.userListProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.recyclerView.adapter = adapter
    }

    private fun setObservers() {
        viewModel.getContacts().observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ResponseHandler.Success -> bindSuccessResponse(it.value)
                    is ResponseHandler.Loading -> {}
                    is ResponseHandler.Error -> bindErrorResponse(it.apiError)
                    is ResponseHandler.Empty -> bindEmptyResponse()
                }
            }
        )
    }

    private fun bindEmptyResponse() {
        binding.userListProgressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE

        requireView().showErrorSnackbar(getString(R.string.empty_data_error))
    }

    private fun bindErrorResponse(apiError: ApiError<Throwable>) {
        val message = when(apiError) {
            is ApiError.HttpError -> getString(R.string.concact_http_error, apiError.message)
            is ApiError.NetworkError -> getString(R.string.connection_error)
            else -> getString(R.string.unknown_error)
        }

        binding.userListProgressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE

        requireView().showErrorSnackbar(message)
    }

    private fun bindSuccessResponse(value: List<User>) {
        binding.recyclerView.visibility = View.VISIBLE
        binding.userListProgressBar.visibility = View.GONE
        adapter.users = value
    }
}
