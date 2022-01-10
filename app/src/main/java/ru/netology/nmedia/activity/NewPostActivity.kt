package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityPostContractBinding

class PostContractActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPostContractBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.content.setText(intent?.getStringExtra(Intent.EXTRA_TEXT))

        binding.save.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                val intent = Intent().apply {
                    putExtra(Intent.EXTRA_TEXT, text)
                }
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }
}