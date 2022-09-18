@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.zx_art

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.text.Html
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.zx_art.app.MKey
import com.example.zx_art.parser.GsonParser
import com.example.zx_art.parser.ZxArtMusic
import kotlinx.coroutines.launch
import java.io.File
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
@Composable
fun ZxButton(
    text: String,
    textOffset: Offset = Offset(0f, 0f),
    style: TextStyle = MaterialTheme.typography.h2,
    clickable: () -> Unit,
) {
    Text(text = text,
        modifier = Modifier
            .border(width = 1f.dp, color = Color(87, 109, 126, 255))
            .background(color = Color(105, 157, 201, 255))
            .clickable { clickable.invoke() }
            .offset(x = textOffset.x.dp, y = textOffset.y.dp)
            .padding(2f.dp),
        color = Color.Green,
        style = style)
}

////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun PopupList(context: Context, playlist: MutableState<Boolean>) {


    if (!playlist.value) return

    var dir: Array<File>? by remember { mutableStateOf(null) }

    var tuneId by remember { mutableStateOf(-1) }
    LaunchedEffect(tuneId) {
        if (tuneId >= 0) {
            launch {
                playlist.value = false

                //
                val gsonText = dir?.get(tuneId)?.readText()
                val tunes = GsonParser.deserialize<ZxArtMusic>(gsonText) ?: ZxArtMusic(64,
                    ZxArtMusic.ResponseData(arrayListOf()),
                    "",
                    0,
                    64)
                MKey.tuneInfo?.let {
                    tunes.responseData.zxMusic.add(it)
                    dir?.get(tuneId)?.writeText(GsonParser.serialize(tunes))
                }

                //
                Toast.makeText(context,
                    "Added '${decodeText(MKey.tuneInfo?.title)}' to playlist '${dir?.get(tuneId)?.nameWithoutExtension}'",
                    Toast.LENGTH_SHORT).show()
                tuneId = -1
            }
        }
    }

    LaunchedEffect(playlist.value) {
        if (playlist.value) {
            dir = context.filesDir.listFiles()
        }
    }

    DropdownMenu(
        modifier = Modifier.padding(2f.dp),
        expanded = playlist.value, onDismissRequest = { playlist.value = false }) {


        DropdownMenuItem(
            contentPadding = PaddingValues(2f.dp),
            onClick = { }) {

            LazyColumn(modifier = Modifier
                .size(300f.dp)
                .fillMaxWidth()
                .padding(2f.dp)) {

                items(dir?.size ?: 0) {
                    PopButton(text = "${dir?.get(it)?.nameWithoutExtension}") {
                        tuneId = it
                    }
                }
            }
        }

    }
}

@Composable
private fun PopButton(text: String?, onClick: () -> Unit) {
    BasicText(text = text ?: UNDEFINED_MESSAGE,
        modifier = Modifier
            .fillMaxWidth()
            .padding(1f.dp)
            .background(color = ZxColor.INFO_LABEL)
            .border(width = 1f.dp, color = ZxColor.BORDER)
            .clickable { onClick.invoke() }
            .padding(4f.dp),
        style = MaterialTheme.typography.subtitle2,
        softWrap = false,
        maxLines = 1
    )
}

////////////////////////////////////////////////////////////////////////////////////////////////////