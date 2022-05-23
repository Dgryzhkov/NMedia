package ru.netology.nmedia.error

import android.database.SQLException
import java.io.IOException

sealed class AppError : RuntimeException() {
    companion object {
        fun from(e: Throwable): AppError {
            val appError = when (e) {
                is AppError -> e
                is SQLException -> DbError
                is IOException -> NetworkError
                else -> UnknownError
            }
            return appError
        }
    }
}
class ApiError : AppError()
object NetworkError : AppError()
object UnknownError: AppError()
object DbError : AppError()
