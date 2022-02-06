package ru.netology.nmedia.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg
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

        arguments?.textArg?.let { binding.postContent.content.text = it }

        val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )


        binding.postContent.content.setOnClickListener{
            binding.postContent.content.text.toString()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()


        }
        return binding.root
    }
    companion object{
        var Bundle.textArg: String? by StringArg
    }
}


