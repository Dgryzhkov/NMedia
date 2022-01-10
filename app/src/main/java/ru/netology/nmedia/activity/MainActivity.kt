package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.activity.viewModels

import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.adapter.AdapterCallback
import ru.netology.nmedia.databinding.ActivityMainBinding


import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostContract = registerForActivityResult(PostContract()) { text ->
            text?.let {
                viewModel.changeContent(text.toString())
                viewModel.save()
            }
        }


        val adapter = PostsAdapter(object : AdapterCallback {
            override fun onLike(post: Post) {
                viewModel.likedById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                newPostContract.launch(post.content)
                viewModel.edit(post)

            }

            override fun onShare(post: Post) {
                viewModel.share(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT, post.content
                    )
                    type = "text/plain"
                }
                val chooser =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(chooser)
            }

            override fun onViews(post: Post) {
                viewModel.view(post.id)
            }

            override fun videoPlayer(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }


        })

        binding.add?.setOnClickListener {
            newPostContract.launch("")
        }

        binding.list.adapter = adapter
        //binding.container.itemAnimator =null//отключение анимации по умолчанию
        viewModel.data.observe(this) { posts ->
            val newPost = adapter.itemCount < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }
    }
}



