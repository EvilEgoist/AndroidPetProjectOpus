package com.android.opus.ui.screen.mainphoto

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.fragment.app.Fragment
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ImagePicker(
    private val fragment: Fragment,
    private val dispatcher: CoroutineDispatcher
) {

    private var imageUri: List<Uri>? = null

    fun getSingleImage(): Uri? {
        return imageUri?.get(0)
    }

    fun loadData(data: Intent?) {
        imageUri = Matisse.obtainResult(data)
    }

    suspend fun pickSingleImage() {
        withContext(dispatcher) {
            Matisse.from(fragment)
                .choose(MimeType.ofImage(), false)
                .capture(true)
                .captureStrategy(CaptureStrategy(true, "com.android.opus.fileprovider"))
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .showSingleMediaType(true)
                .imageEngine(GlideEngine())
                .showPreview(true)
                .autoHideToolbarOnSingleTap(true)
                .forResult(REQUEST_CODE_CHOOSE)
        }
    }

    companion object {
        const val REQUEST_CODE_CHOOSE = 12
    }
}
