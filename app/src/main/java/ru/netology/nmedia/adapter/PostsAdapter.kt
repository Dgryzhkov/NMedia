package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
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
    private val services: Services = Services()

) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_outline_favorite_border_24)
            like.setOnClickListener {
                callback.onLike(post)


            }
            likeCount.text = services.formatCount(post.likes)
            share.setImageResource(R.drawable.ic_baseline_share_24)
            shareCount.text = services.formatCount(post.shares)
            views.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
            viewsCount.text = services.formatCount(post.views)

            binding.like.setOnClickListener {
                callback.onLike(post)
            }
            binding.share.setOnClickListener {
                callback.onShare(post)
            }
            binding.views.setOnClickListener {
                callback.onViews(post)
            }
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
            viewsCount.text = services.formatCount(post.views)


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