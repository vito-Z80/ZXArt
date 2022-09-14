package com.example.zx_art.app.playlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.zx_art.AppInput
import com.example.zx_art.ZxButton
import com.example.zx_art.ZxColor
import com.example.zx_art.app.MKey


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PlayListCatalog() {
    if (!MKey.showPlaylistCatalog) return




    Dialog(onDismissRequest = {
        MKey.showPlaylistCatalog = false
    }) {
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
                items(3) {
                    LazyRow(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween) {

                        item {
                            Text(text = "Item: $it",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { },
                                style = MaterialTheme.typography.subtitle2)
                        }
                        item {
                            ZxButton(text = "-", Offset(1f, -1f)) {
                                println(this.hashCode())
                            }
                        }
                    }
                }
            }
            Divider(color = ZxColor.BORDER)
            Column(modifier = Modifier
                .padding(top = 2f.dp)
                .fillMaxWidth(), horizontalAlignment = Alignment.End) {
                ZxButton(text = "New", textOffset = Offset(2f, -1f)) {
                    MKey.showPlaylistCatalog = false

                    MKey.showNewPlaylistInputName = true
                }
            }
        }
    }
}

@Composable
fun InputNewPlaylistName() {

    if (!MKey.showNewPlaylistInputName) return

    val name: MutableState<String?> = remember { mutableStateOf(null) }
//    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
    AppInput(text = name, size = DpSize(256f.dp, 32f.dp)) {
        println("DONEEEEEEEEEEEEEEEEEEEEEEEEEEEE: ${name.value}")
        MKey.showPlaylistCatalog = false
        MKey.showNewPlaylistInputName = false
    }
//    }
    println("INPUTTTTTTTTTTTTTTTT")
}