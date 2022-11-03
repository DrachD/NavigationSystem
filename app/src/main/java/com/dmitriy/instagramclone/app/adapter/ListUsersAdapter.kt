package com.dmitriy.instagramclone.app.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmitriy.instagramclone.app.model.peoples.entities.UserData
import com.dmitriy.instagramclone.app.screens.main.MainActivity
import com.dmitriy.instagramclone.app.utils.downloader.PhotoUploader
import com.dmitriy.instagramclone.databinding.ItemListUserLayoutBinding
import kotlinx.coroutines.*

class ListUsersAdapter(
    private val activity: MainActivity,
    private val listener: (UserData) -> Unit
) : RecyclerView.Adapter<ListUsersAdapter.ListUsersHolder>() {

    class ListUsersHolder(val binding: ItemListUserLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(
            oldItem: UserData,
            newItem: UserData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserData,
            newItem: UserData
        ): Boolean {
            return oldItem == newItem
        }
    }

    val diffAsync = AsyncListDiffer<UserData>(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUsersHolder {
        return ListUsersHolder(
            ItemListUserLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListUsersHolder, position: Int) {
        val userItem = diffAsync.currentList[position]
        holder.binding.apply {
            nicknameTextView.text = "${userItem.first_name} ${userItem.last_name}"
            emailTextView.text = userItem.email
            PhotoUploader(userIconImageView).execute(userItem.avatar)

//            activity.loadIcon(userItem.avatar) {
//                //userIconImageView.setImageBitmap(it)
//            }
//            Picasso.get()
//                .load(userItem.avatar)
//                .into(userIconImageView)
        }
        holder.itemView.setOnClickListener {
            listener.invoke(userItem)
        }
    }

    override fun getItemCount(): Int = diffAsync.currentList.size
}