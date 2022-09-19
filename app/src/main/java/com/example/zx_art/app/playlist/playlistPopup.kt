package com.example.zx_art.app.playlist

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zx_art.additional.PopButton
import com.example.zx_art.app.MKey
import com.example.zx_art.decodeText
import com.example.zx_art.parser.GsonParser
import com.example.zx_art.parser.ZxArtMusic
import kotlinx.coroutines.launch
import java.io.File

/**
 * Плейлист для выбора альбома на прослушивание.
 */

@Composable
fun PlaylistPopup(context: Context, playlist: MutableState<Boolean>) {


    // TODO доавить кнопку добавления нового альбома
    //  возможно поменять на Dialog модальное окно или Box

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




