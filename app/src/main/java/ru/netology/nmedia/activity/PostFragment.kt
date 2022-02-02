package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.AdapterCallback
import ru.netology.nmedia.adapter.PostDiffCallback
import ru.netology.nmedia.adapter.PostsAdapter

import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class PostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(
            inflater,
            container,
            false
        )
        val viewModel:PostViewModel by viewModels (
            ownerProducer = ::requireParentFragment
                )

        val bundle = Bundle()

        val adapter = PostsAdapter(object : AdapterCallback{
            override fun onLike(post: Post) {
                viewModel.likedById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                bundle.putString("content", post.content)
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment, bundle)
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


        binding.postContent.content

        viewModel.data.observe(viewLifecycleOwner){ posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(viewLifecycleOwner){post ->
            if(post.id==0L){
                return@observe
            }
        }


        binding.root.setOnClickListener() {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

//содержание oncreate оставшееся от активити


        return binding.root
    }

}


