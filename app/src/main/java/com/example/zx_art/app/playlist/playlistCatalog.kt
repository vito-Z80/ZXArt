@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.zx_art.app.playlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.zx_art.ZxButton
import com.example.zx_art.ZxColor
import com.example.zx_art.app.MKey


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PlayListCatalog() {
    if (!MKey.showPlaylistCatalog) return

    var newName by remember { mutableStateOf("") }

    var list by remember { mutableStateOf(listOf<String>()) }

    var removeElement: String? by remember { mutableStateOf(null) }

    LaunchedEffect(removeElement) {
        if (removeElement.isNullOrEmpty()) return@LaunchedEffect
        list = list.minus(removeElement!!)
        removeElement = null
    }


    // TODO визуализировать добавление мелодии в существующий плейлист + добавление во вновь созданный плейлист.
    //  переделать list
    //

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
                list.forEach { text ->
                    item {
                        LazyRow(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {

                            item {
                                Text(text = text,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { },
                                    style = MaterialTheme.typography.subtitle2)
                            }
                            item {
                                ZxButton(text = "-", Offset(1f, -1f)) {
                                    removeElement = text
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
                            list = list.plus(newName)
                            newName = ""
                        })
                    )

                ZxButton(text = "New", textOffset = Offset(2f, -1f)) {
                    MKey.keyboardFocus = true
                }
            }
        }
    }
}