package com.magicapp.android_mvvm_example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.magicapp.android_mvvm_example.database.entity.User
import com.magicapp.android_mvvm_example.databinding.ItemUserBinding

class UserAdapter : ListAdapter<User, UserAdapter.UserVh>(MyDiffUtil()) {

    inner class UserVh(var itemUserBinding: ItemUserBinding):
        RecyclerView.ViewHolder(itemUserBinding.root){
            fun onBind(user: User){
                itemUserBinding.apply {
                    nameTv.text = user.name
                    phoneTv.text = user.phone
                }
            }
    }

    class MyDiffUtil: DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVh {
        return UserVh(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserVh, position: Int) {
        holder.onBind(getItem(position))
    }
}