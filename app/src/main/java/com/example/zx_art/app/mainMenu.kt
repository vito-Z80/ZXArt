package com.example.zx_art.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.zx_art.R
import com.example.zx_art.ZxColor
import com.example.zx_art.additional.PopButton


@Composable
fun MainMenuSearch() {



    val ib = ImageBitmap.imageResource(id = R.drawable.finder)

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {

        Image(bitmap = ib, contentDescription = null, filterQuality = FilterQuality.None,
            modifier = Modifier
                .clickable { MKey.showCentral = true }
                .size(48.dp)
                .border(width = 2.dp, color = ZxColor.INFO_BG, AbsoluteRoundedCornerShape(8.dp))
                .background(color = ZxColor.BLUE, AbsoluteRoundedCornerShape(8.dp))
                .padding(8.dp)
        )
    }
}

@Composable
fun MainMenuButton() {

    val ib = ImageBitmap.imageResource(id = R.drawable.menu_icon)

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {

        Image(bitmap = ib, contentDescription = null, filterQuality = FilterQuality.None,
            modifier = Modifier
                .clickable { MKey.showMainMenu = true }
                .size(48.dp)
                .border(width = 2.dp, color = ZxColor.INFO_BG, AbsoluteRoundedCornerShape(8.dp))
                .background(color = ZxColor.BLUE, AbsoluteRoundedCornerShape(8.dp))
                .padding(8.dp)
        )
    }
}

@Composable
fun MainMenuList() {

    if (!MKey.showMainMenu) return

    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { MKey.showMainMenu = false }

    ) {
        LazyColumn(modifier = Modifier
            .border(width = 0f.dp, color = Color(0, 0, 0, 0), AbsoluteRoundedCornerShape(32f.dp))
        ) {

            item { MenuDatabase() }
            item { MenuPlaylist() }
        }
    }
}

@Composable
private fun MenuDatabase() {
    Row(modifier = Modifier.fillMaxWidth(0.75f)) {
        PopButton(text = "Database") {
            MKey.showMainMenu = false
        }
    }
}

@Composable
private fun MenuPlaylist() {
    Row(modifier = Modifier.fillMaxWidth(0.75f)) {
        PopButton(text = "Your list") {
            MKey.showMainMenu = false
            MKey.showPlaylistCatalog = true
        }
    }
}