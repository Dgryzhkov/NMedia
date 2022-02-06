package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.card_post.*
import kotlinx.android.synthetic.main.card_post.view.*
import ru.netology.nmedia.R
import ru.netology.nmedia.Services
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )
        val bundle = Bundle()
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val id = arguments?.getLong("id")
        var singlePost: Post? = null

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            posts.map { post ->
                if (post.id == id) {
                    singlePost = post
                }
            }
            with(binding){
                author.text = singlePost?.author
                content.text = singlePost?.content
                published.text = singlePost?.published

                like.text = singlePost?.let { it -> Services.formatCount(it.likes) }
                share.text = singlePost?.shares?.let { it ->
                    Services.formatCount(
                        it
                    )
                }
                like.isChecked = singlePost?.likedByMe == true

            }
        }
        binding.root.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.menu_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {

                            singlePost?.id?.let { id -> viewModel.removeById(id) }

                            findNavController().navigate(
                                R.id.action_feedFragment_to_postFragment
                            )
                            true
                        }
                        R.id.edit -> {

                            singlePost?.let { it -> viewModel.edit(it) }

                            bundle.putString("content", singlePost?.content)

                            findNavController().navigate(
                                R.id.action_postFragment_to_newPostFragment,
                                bundle
                            )
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
        return binding.root
    }
}