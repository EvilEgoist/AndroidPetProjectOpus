package com.android.opus.domain

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.opus.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Осторожно!!!
 * Context и getStringArray, который берёт набор строк из файла strings пока работает в качестве
 * заглушки, до того момента, как не настроится бэк
 */
class ActivityFieldInteractor(
    private val dispatcher: CoroutineDispatcher,
    private val context: Context?
) {
    suspend fun loadActivityFieldList() : List<String>? =
        withContext(dispatcher) {
            context?.resources?.getStringArray(R.array.activity_fields)?.toList()
        }
}
