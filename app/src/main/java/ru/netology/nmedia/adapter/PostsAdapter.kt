package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.Services
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

interface AdapterCallback {
    fun onLike(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onShare(post: Post)
    fun onViews(post: Post)
    fun videoPlayer(post: Post)
    fun onPost(post: Post)
}

class PostsAdapter(

    private val callback: AdapterCallback

) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, callback)

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }


}


class PostViewHolder(
    private val binding: CardPostBinding,
    private val callback: AdapterCallback,

    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {

        binding.apply {
            binding.root.setOnClickListener {
                callback.onPost(post)
            }

            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.isChecked = post.likedByMe
            like.text = Services.formatCount(post.likes)
            share.text = Services.formatCount(post.shares)
            views.text = Services.formatCount(post.views)
            binding.like.setOnClickListener {
                callback.onLike(post)
            }
            binding.share.setOnClickListener {
                callback.onShare(post)
            }
            binding.views.setOnClickListener {
                callback.onViews(post)
            }
            groupVideo.isVisible = !post.video.isNullOrBlank()

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> callback.onRemove(post)
                            R.id.edit -> callback.onEdit(post)
                        }
                        true
                    }
                }.show()
            }
        }
    }

}


class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}