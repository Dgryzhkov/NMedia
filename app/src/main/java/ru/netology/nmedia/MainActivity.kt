package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.card_post.*
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()


        val adapter = PostsAdapter ({ viewModel.likedById(it.id)}, {viewModel.share(it.id)},{viewModel.view(it.id)})


        binding.container.adapter = adapter
        //binding.container.itemAnimator =null//отключение анимации по умолчанию
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }


}






