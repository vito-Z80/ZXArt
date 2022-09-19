@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.zx_art

import android.icu.text.SimpleDateFormat
import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.zx_art.app.MKey
import java.util.*

////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////


fun zxArtTime(time: String?): String {
    return time?.split(".")?.get(0)?.split(":").let {
        // FIXME если время по нулям то заменять двойными значаками вопросов (??:??)
        String.format("%02d:%02d",
            it?.get(0)?.toLong() ?: 0,
            it?.get(1)?.toLong() ?: 0)
    }
}

fun tuneInfoDateCreated(): String {
    return try {
        SimpleDateFormat("dd.MM.yyyy", Locale("EN")).format(Date(MKey.dateCreated * 1000))
    } catch (e: Exception) {
        UNDEFINED_MESSAGE
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
fun decodeText(text: String?) =
    Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)?.toString() ?: UNDEFINED_MESSAGE



////////////////////////////////////////////////////////////////////////////////////////////////////

// TODO использовать кусок для прогрузки изображений.
//        SubcomposeAsyncImage(model = "https://zxart.ee/project/images/public/logo.png",
//            contentDescription = null,
//            modifier = Modifier.height(128.dp)) {
//            val state = painter.state
//            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
//                CircularProgressIndicator()
//            } else {
//                SubcomposeAsyncImageContent()
//            }
//        }
////////////////////////////////////////////////////////////////////////////////////////////////////