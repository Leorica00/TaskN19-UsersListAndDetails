package com.example.taskn19.presentation.users.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.taskn19.R
import com.example.taskn19.databinding.UserRecyclerItemBinding
import com.example.taskn19.presentation.model.User
import com.example.taskn19.presentation.users.listener.UserItemClickListener

class UsersRecyclerViewAdapter(private val userItemClickListener: UserItemClickListener) :
    ListAdapter<User, UsersRecyclerViewAdapter.UserViewHolder>(
        usersItemDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind()
    }

    inner class UserViewHolder(private val binding: UserRecyclerItemBinding) :
        ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "ResourceType")
        fun bind() {
            val user = currentList[bindingAdapterPosition]
            with(binding) {
                Glide.with(itemView.context).load(user.avatar).into(imageViewAvatar)
                tvUserEmail.text = user.email
                tvUserFullName.text = "${user.firstName} ${user.lastName}"
                root.setOnClickListener {
                    userItemClickListener.onUserItemClick(userId = user.id)
                }
                if (user.isSelected) {
                    root.setBackgroundResource(R.color.darker_light_blue)
                } else {
                    root.setBackgroundResource(R.color.light_blue)
                }
                bindListeners(user.id)
            }
        }

        private fun bindListeners(id: Int) {
            with(binding.root) {
                setOnClickListener {
                    userItemClickListener.onUserItemClick(userId = id)
                }

                setOnLongClickListener {
                    userItemClickListener.onUserItemLongClick(userId = id)
                    return@setOnLongClickListener true
                }
            }
        }
    }

    companion object {
        private val usersItemDiffCallback = object : DiffUtil.ItemCallback<User>() {

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}