package com.picpay.agenda.presentation.home.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.picpay.agenda.R
import com.picpay.agenda.presentation.utils.DiffUtilCallbacks
import com.picpay.domain.entity.User

class UserListAdapter : ListAdapter<User, UserListItemViewHolder>(
    DiffUtilCallbacks.getUserListDiffUtilCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.list_item_user,
                parent,
                false
            )

        return UserListItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: UserListItemViewHolder,
        position: Int
    ) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size
}
