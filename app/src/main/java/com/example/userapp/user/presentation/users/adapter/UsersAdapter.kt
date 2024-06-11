package com.example.userapp.user.presentation.users.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.userapp.user.domain.model.UserInfo

class UsersAdapter(
    private val onItemClickListener: (UserInfo) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<UserInfo>()

    fun setItems(new: List<UserInfo>) {
        val old = ArrayList(data)
        data.clear()
        data.addAll(new)
        DiffUtil.calculateDiff(getDiffCallback(old, new)).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> holder.onBind(data = data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    private fun getDiffCallback(
        oldList: List<UserInfo>,
        newList: List<UserInfo>
    ) = object : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old == new
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
    }
}
