@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.zx_art.app.playlist

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.zx_art.ZxButton
import com.example.zx_art.ZxColor
import com.example.zx_art.app.MKey
import com.example.zx_art.app.file.ZxFile
import com.example.zx_art.net.Request
import kotlinx.coroutines.withContext
import java.io.File


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PlayListCatalog() {
    if (!MKey.showPlaylistCatalog) return


    val context = LocalContext.current
    var dir by remember { mutableStateOf(context.filesDir.listFiles()) }
    fun reloadDir() {
        dir = context.filesDir.listFiles()
    }

    var openPlaylist: File? by remember { mutableStateOf(null) }
    var addedTune: File? by remember { mutableStateOf(null) }

    var newName by remember { mutableStateOf("") }

//    var list by remember { mutableStateOf(listOf<String>()) }

    var removeElement: String? by remember { mutableStateOf(null) }

    // удалить выбранный плейлист
    LaunchedEffect(removeElement) {
        if (removeElement.isNullOrEmpty()) return@LaunchedEffect
//        list = list.minus(removeElement!!)

        withContext(this.coroutineContext) {
            dir?.find { it.nameWithoutExtension == removeElement }?.delete()
        }

        removeElement = null
        reloadDir()
    }

    // открыть не пустой плейлист
    LaunchedEffect(openPlaylist) {
        if (openPlaylist == null) return@LaunchedEffect
        if (Request.loadPlaylist(openPlaylist)) {
            MKey.showPlaylistCatalog = false
            Toast.makeText(context,
                "Open playlist ${openPlaylist?.nameWithoutExtension}",
                Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Playlist is empty", Toast.LENGTH_SHORT).show()
        }
        openPlaylist = null
    }

    // сохранить добавленную мелодию в плейлист
    LaunchedEffect(addedTune) {
        if (addedTune == null) return@LaunchedEffect
        Toast.makeText(context,
            "Added '${MKey.tuneInfo?.title}' to playlist '${addedTune?.nameWithoutExtension}'",
            Toast.LENGTH_SHORT).show()
        addedTune = null
    }

    Dialog(onDismissRequest = { MKey.showPlaylistCatalog = false }) {
        Column(modifier = Modifier
            .height(IntrinsicSize.Min)
            .border(width = 1f.dp,
                color = ZxColor.BORDER,
                shape = AbsoluteRoundedCornerShape(8f.dp))
            .background(color = ZxColor.INFO_BG, shape = AbsoluteRoundedCornerShape(8f.dp))
            .padding(8f.dp),
            verticalArrangement = Arrangement.Center) {
            Text(text = "Playlists",
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.subtitle1)
            Divider(color = ZxColor.BORDER)
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .height(128f.dp)
                .padding(vertical = 1f.dp)
            ) {
                dir?.forEach {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
//                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = it.nameWithoutExtension,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .clickable {
                                        openPlaylist = it
                                    },
                                style = MaterialTheme.typography.subtitle2,
                                softWrap = false
                            )
                            Row {
                                ZxButton(text = "+", Offset(1f, -1f)) {
                                    addedTune = it
                                }

                                Spacer(modifier = Modifier.padding(end = 8f.dp))
                                ZxButton(text = "-", Offset(1f, -1f)) {
                                    removeElement = it.nameWithoutExtension
                                }
                            }


                        }
                    }
                }
            }
            Divider(color = ZxColor.BORDER)
            LaunchedEffect(MKey.keyboardFocus) {
                if (MKey.keyboardFocus) {
                    MKey.focus.requestFocus()
                    MKey.keyboardController?.show()
                }
            }
            Row(modifier = Modifier
                .padding(top = 4f.dp)
                .fillMaxWidth()
                .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                if (MKey.keyboardFocus)
                    BasicTextField(value = newName,
                        singleLine = true,
                        onValueChange = {
                            newName = it
                        },
                        modifier = Modifier
                            .width(192f.dp)
                            .height(32f.dp)
                            .border(width = 1f.dp, color = ZxColor.BORDER)
                            .background(color = ZxColor.TUNE_LABEL_LIST_2)
                            .focusRequester(MKey.focus),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            MKey.focus.freeFocus()
                            MKey.keyboardFocus = false
                            MKey.keyboardController?.hide()
                            ZxFile.save(
                                context = context,
                                newName,
                                ""
                            )
                            newName = ""
                            reloadDir()
                        })
                    )

                ZxButton(text = "New", textOffset = Offset(2f, -1f)) {
                    MKey.keyboardFocus = true
                }
            }
        }
    }

}