package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post

/*class PostRepositoryInFilefImpl(val context: Context) : PostRepository {
    private val gson = Gson()

    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var nextId = 1L
    private var posts = emptyList<Post>()

    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
       if (file.exists()){
           context.openFileInput(filename).bufferedReader().use {
               posts = gson.fromJson(it, type)
               nextId=posts.maxOfOrNull { post->post.id }?.inc()?:1
               data.value=posts
           }
       } else {
           sync()
       }

    }*/
class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

/*    private fun sync(){
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }

    }*/
    override fun getAll(): LiveData<List<Post>> = data


    override fun likeById(id: Long) {
        dao.likeById(id)
        posts = posts.map {
            if (it.id != id) it else if (!it.likedByMe) it.copy(
                likedByMe = !it.likedByMe,
                likes = it.likes + 1
            ) else it.copy(likedByMe = !it.likedByMe, likes = it.likes - 1)


        }
        data.value = posts
        //sync()
    }

    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it.shares + 1)
        }
        data.value = posts
        //sync()
    }

    override fun view(id: Long) {

        posts = posts.map {
            if (it.id != id) it else it.copy(views = it.views + 1)
        }
        data.value = posts
       // sync()

    }

    override fun removeById(id: Long) {
        dao.removeById(id)
        posts = posts.filter { it.id != id }
        data.value = posts
        //sync()
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (post.id == 0L) {
            listOf(saved)+posts
            //listOf(post.copy(id = nextId++, author = "Me", published = "now")) + posts
        } else {
            posts.map { if (it.id!=id) it  else saved }
            //posts.map { if (it.id != post.id) it else it.copy(content = post.content) }
        }
        data.value = posts
       // sync()
    }

    override fun cancel(post: Post) {
        post
    }


}
