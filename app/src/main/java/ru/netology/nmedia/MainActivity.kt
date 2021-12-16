package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding.*
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = inflate(layoutInflater)
        setContentView(binding.root)
        val services  = Services()

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like?.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_outline_favorite_border_24)
                likeCount?.text=services.formatCount(post.likes)
                share?.setImageResource(R.drawable.ic_baseline_share_24)
                shareCount?.text=services.formatCount(post.shares)
                views?.setImageResource(R.drawable.ic_baseline_remove_red_eye_24)
                viewsCount?.text=services.formatCount(post.views)

            }
        }
        binding.like?.setOnClickListener {
            viewModel.like()
        }
        binding.share?.setOnClickListener{
            viewModel.share()
        }
        binding.views?.setOnClickListener{
            viewModel.view()
        }
    }
}




