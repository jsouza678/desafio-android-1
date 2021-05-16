package com.picpay.agenda.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.agenda.R
import com.picpay.domain.entity.User

class UserListAdapter : RecyclerView.Adapter<UserListItemViewHolder>() {

    var users = emptyList<User>()
        set(value) {
            val result = DiffUtil.calculateDiff(
                UserListDiffCallback(
                    field,
                    value
                )
            )
            result.dispatchUpdatesTo(this)
            field = value
        }

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
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}