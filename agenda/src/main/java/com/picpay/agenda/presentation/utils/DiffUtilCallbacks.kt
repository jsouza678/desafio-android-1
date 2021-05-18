package com.picpay.agenda.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.picpay.domain.entity.User

object DiffUtilCallbacks {

    fun getUserListDiffUtilCallback(): DiffUtil.ItemCallback<User> {
        return object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
