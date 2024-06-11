package com.example.userapp.user.presentation.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.userapp.databinding.ItemUserBinding
import com.example.userapp.user.domain.model.UserInfo

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val onItemClickListener: (UserInfo) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    constructor(
        parent: ViewGroup,
        onItemClick: (UserInfo) -> Unit
    ) : this(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
        onItemClick,
    )

    fun onBind(data: UserInfo) {
        binding.textViewUserName.text = data.name
        binding.textViewEmail.text = data.email
        binding.imageViewAccount.setUser(data.name)
        itemView.setOnClickListener {
            onItemClickListener.invoke(data)
        }
    }
}
