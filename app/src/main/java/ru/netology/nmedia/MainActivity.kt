package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            share = false,
            view = false,

            )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like?.let {
                fun ImageButton.setLiked(liked: Boolean) {
                    val likeIconResId = if (post.likedByMe)
                        R.drawable.ic_baseline_favorite_24 else R.drawable.ic_outline_favorite_border_24
                    setImageResource(likeIconResId)
                }
                it.setLiked(post.likedByMe)
                it.setOnClickListener {
                    post.likedByMe = !post.likedByMe
                    like.setLiked(post.likedByMe)
                    //Добавил счетчик лайков
                    likeCount?.let {
                        if (post.likedByMe)
                            post.likes++ else post.likes--
                        likeCount.text = post.likes.toString()
                    }
                }
            }
            share?.let {
                fun ImageButton.setShare(share: Boolean) {
                    val shareIconResId = R.drawable.ic_baseline_share_24
                }
                it.setShare(post.share)
                it.setOnClickListener {
                    post.share = !post.share
                    share.setShare(post.share)
                    //Добавил счетчик лайков
                    shareCount?.let {
                        shareCount
                        post.shares++
                        shareCount.text = formatCount(post.shares)
                    }
                }
            }
            views?.let {
                fun ImageButton.setView(view: Boolean) {
                    val shareIconResId = R.drawable.ic_baseline_remove_red_eye_24
                }
                it.setView(post.view)
                it.setOnClickListener {
                    post.share = !post.view
                    views.setView(post.view)
                    //Добавил счетчик лайков
                    viewsCount?.let {
                        viewsCount
                        post.views++
                        viewsCount.text = formatCount(post.views)
                    }
                }
            }
        }
    }
}


//формат чисел с обавление суфикса
private fun formatCount(number: Int): String {
    val suffixes = charArrayOf('k', 'm',)
    if (number < 1000) {
        return java.lang.String.valueOf(number)
    }

    val string = java.lang.String.valueOf(number)

    // разрядность числа
    val magnitude = (string.length - 1) / 3

    // количество цифр до суффикса
    val digits = (string.length - 1) % 3 + 1

    val value = CharArray(4)
    for (i in 0 until digits) {
        value[i] = string[i]
    }
    var valueLength = digits

    // добавление точки и числа
    if (digits == 1 && string[1] != '0') {
        value[valueLength++] = '.'
        value[valueLength++] = string[1]
    }

    // добавление суффикса
    value[valueLength++] = suffixes[magnitude - 1]
    return String(value)
}


