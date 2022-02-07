package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import kotlinx.android.synthetic.main.card_post.view.*
import kotlinx.android.synthetic.main.fragment_new_post.*
import ru.netology.nmedia.R

import ru.netology.nmedia.databinding.FragmentNewPostBinding

import ru.netology.nmedia.util.StringArg
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
        arguments?.textArg?.let { binding.edit.setText(it) }

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)


        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()//content.text.toString()
            if (text.isNotBlank()) {
                viewModel.changeContent(text)
                viewModel.save()
            }
            findNavController().navigate(R.id.action_newPostFragment_to_feedFragment2)
        }

        viewModel.edited.observe(viewLifecycleOwner){
            if (it.id!=0L){
                Bundle().apply { textArg=it.content }
            }
        }

        return binding.root
    }
companion object {
    var Bundle.textArg: String? by StringArg
}

}