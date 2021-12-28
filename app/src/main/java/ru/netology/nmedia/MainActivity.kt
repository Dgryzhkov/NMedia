package ru.netology.nmedia

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.card_post.*
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
        binding.group.visibility = View.GONE

        val viewModel: PostViewModel by viewModels()


        val adapter = PostsAdapter(object : AdapterCallback {
            override fun onLike(post: Post) {
                viewModel.likedById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onShare(post: Post) {
                viewModel.share(post.id)
            }

            override fun onViews(post: Post) {
                viewModel.view(post.id)
            }


        })

        viewModel.edited.observe(this) {
            if (it.id != 0L) {
                binding.content.setText(it.content)
                binding.content.requestFocus()
                binding.group.visibility = View.VISIBLE
                binding.editMessageStart.setText(it.content)
            }
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
            binding.group.visibility = View.GONE
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                val content = text.toString()
                if (content.isBlank()) {
                    Toast.makeText(it.context, R.string.error_empty_content, Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }

                viewModel.changeContent(content)
                viewModel.save()
                setText("")
                clearFocus()
                hideKeyboard(it)
                binding.group.visibility = View.GONE

            }
        }

        binding.cancel.setOnClickListener {
            with(binding.content) {
                viewModel.changeContent(content.toString())
                setText("")
                clearFocus()
                hideKeyboard(it)
                binding.group.visibility = View.GONE
            }
        }

    }

}

//скрытие клавиатуры (работает)
fun Context.hideKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}





