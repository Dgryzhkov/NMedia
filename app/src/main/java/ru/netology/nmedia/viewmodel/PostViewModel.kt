package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.MainActivity
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likes = 0,
    likedByMe = false,
    share = false,
    shares = 0,
    view = false,
    views = 0
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likedById(id: Long) = repository.likeById(id)
    fun share(id: Long) = repository.share(id)
    fun view(id: Long) = repository.view(id)
    val edited = MutableLiveData(empty)

    fun save(){
        edited.value?.let {
            repository.save(it)
        }
        edited.value= empty
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = edited.value?.copy(content = text)
        }
    }

    fun removeById(id: Long) = repository.removeById(id)

    fun edit(post: Post) {
        edited.value= post
    }


}