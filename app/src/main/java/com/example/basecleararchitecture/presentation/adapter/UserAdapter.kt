package com.example.basecleararchitecture.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.basecleararchitecture.base.BaseAdapter
import com.example.basecleararchitecture.databinding.ItemUserBinding
import com.example.basecleararchitecture.domain.model.User
import javax.inject.Inject

class UserAdapter @Inject constructor() : BaseAdapter<User, ItemUserBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemUserBinding {
        return ItemUserBinding.inflate(inflater, parent, false)
    }

    override fun bind(binding: ItemUserBinding, item: User, position: Int) {
        binding.apply {
            tvUserName.text = item.name
            tvUserEmail.text = item.email
            tvUserPhone.text = item.phone
            tvUserWebsite.text = item.website
        }
    }
}