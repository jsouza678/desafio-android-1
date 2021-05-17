package com.picpay.agenda.presentation.home.contacts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.agenda.databinding.ListItemUserBinding
import com.picpay.base.extensions.loadImage
import com.picpay.base.extensions.makeGone
import com.picpay.base.extensions.makeVisible
import com.picpay.domain.entity.User

class UserListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ListItemUserBinding.bind(itemView)

    fun bind(user: User) {
        binding.nameTv.text = user.name
        binding.usernameTv.text = user.username
        binding.avatarPb.makeVisible()

        binding.pictureIv.loadImage(
            imageUrl = user.img,
            onSuccess = { binding.avatarPb.makeGone() },
            onError = { binding.avatarPb.makeGone() }
        )
    }
}
