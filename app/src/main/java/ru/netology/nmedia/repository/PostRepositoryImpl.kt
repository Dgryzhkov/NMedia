package ru.netology.nmedia.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dto.Post
import java.io.IOException
import java.util.concurrent.TimeUnit


class PostRepositoryImpl : PostRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
        private val jsonType = "application/json".toMediaType()
    }

    override fun getAllAsync(callback: PostRepository.GetAllCallback) {

        PostsApi.retrofitService.getAll().enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onResponse(
                call: retrofit2.Call<List<Post>>,
                response: retrofit2.Response<List<Post>>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(
                        java.lang.RuntimeException(response.message()),
                        response.code()
                    )
                    return
                }
                callback.onSuccess(
                    response.body() ?: throw java.lang.RuntimeException("body is null")
                )
            }

            override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                val error = 0
                callback.onError(t, error)
            }
        })
    }

    override fun saveAsync(post: Post, callback: PostRepository.SaveRemoveCallback) {
        PostsApi.retrofitService.save(post).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(
                        java.lang.RuntimeException(response.message()),
                        response.code()
                    )
                    return
                }
                callback.onSuccess()
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                val error = 0
                callback.onError(t, error)
            }
        })
    }


    override fun removeByIdAsync(id: Long, callback: PostRepository.SaveRemoveCallback) {
        PostsApi.retrofitService.removeById(id).enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(
                call: retrofit2.Call<Unit>, response: retrofit2.Response<Unit>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(
                        java.lang.RuntimeException(response.message()),
                        response.code()
                    )
                    return
                }
                callback.onSuccess()
            }

            override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                val error = 0
                callback.onError(t, error)
            }
        })

    }

    override fun likeByIdAsync(id: Long, callback: PostRepository.LikeCallback) {
        PostsApi.retrofitService.likeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>, response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(
                        java.lang.RuntimeException(response.message()), response.code()
                    )
                    return
                }
                val post = response.body()
                if (post != null) {
                    callback.onSuccess(id, post)
                }
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                val error = 0
                callback.onError(t, error)
            }
        })
    }

    override fun unLikeByIdAsync(id: Long, callback: PostRepository.LikeCallback) {
        PostsApi.retrofitService.dislikeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>, response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(
                        java.lang.RuntimeException(response.message()), response.code()
                    )
                    return
                }
                val post = response.body()
                if (post != null) {
                    callback.onSuccess(id, post)
                }
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                val error = 0
                callback.onError(t, error)
            }
        })
    }
}