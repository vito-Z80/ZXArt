package com.example.zx_art

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.text.Html
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
        // FIXME как оно блят работает ???? всегда 70-е возвращает сцуко
        SimpleDateFormat.getDateInstance(DateFormat.DATE_FIELD).format(Date(MKey.dateCreated))
//        SimpleDateFormat("dd.MM.yy").format(Date(date.toLong()))
    } catch (e: Exception) {
        UNDEFINED_MESSAGE
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
fun decodeText(text: String?) =
    Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)?.toString() ?: UNDEFINED_MESSAGE