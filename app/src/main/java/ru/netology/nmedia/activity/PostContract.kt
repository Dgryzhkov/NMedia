package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class PostContract: ActivityResultContract<String, String?>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, PostContractActivity::class.java).apply { putExtra(Intent.EXTRA_TEXT, input) }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return if (resultCode==Activity.RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }
    }
}