package ru.netology.nmedia.activity


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.AdapterCallback
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

// переход от активити к рагментам shift+f6 переименовать

class FeedFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { //  не nullable убрать ?
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = PostsAdapter(object : AdapterCallback {

            override fun onLike(post: Post) {
                viewModel.likedById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {

                post?.let { it -> viewModel.edit(it) }
                findNavController().navigate(
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg=post.content }
                )
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

            override fun onPost(post: Post) {
                AndroidUtils.hideKeyboard(requireView())
                findNavController().navigate(
                    R.id.action_feedFragment_to_postFragment,
                    bundleOf("id" to post.id)
                )
            }
        })



        binding.list.adapter = adapter
        //binding.container.itemAnimator =null//отключение анимации по умолчанию
        //viewLifecycleOwner вместо this
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val newPost = adapter.itemCount < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        binding.add.setOnClickListener {
            findNavController().navigate(
                R.id.action_feedFragment_to_newPostFragment,
            )

        }

        return binding.root
    }
}



