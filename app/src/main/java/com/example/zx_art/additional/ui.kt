package com.example.zx_art.additional

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.zx_art.UNDEFINED_MESSAGE
import com.example.zx_art.ZxColor
import com.example.zx_art.app.MKey

////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun PopButton(text: String?, onClick: () -> Unit) {
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
fun CentralPopupMenu() {

    if (!MKey.showCentral) return

    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { MKey.showCentral = false }

    ) {
        Box(modifier = Modifier
            .padding(4f.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
//            .border(width = 1f.dp, color = ZxColor.BORDER, AbsoluteRoundedCornerShape(32f.dp))
            .background(color = Color(0f, 1f, 1f, 0.35f))

        ) {
            Divider(modifier = Modifier.padding(top = 2f.dp))
            LazyColumn(modifier = Modifier.padding(4f.dp)


            ) {


                repeat(10) {

                    item {
                        PopButton(text = "item $it") {
                            MKey.showCentral = false
                        }
                    }
                }

            }

            Divider(modifier = Modifier.padding(bottom = 2f.dp))

        }

    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun BackdropMenu(
    top: @Composable () -> Unit,
    content: @Composable () -> Unit,
    bottom: @Composable () -> Unit,
) {

    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { MKey.showCentral = false }
    ) {
        LazyColumn(modifier = Modifier
            .padding(4.dp)
            .background(color = Color(0x6800838F))
            .border(width = 1.dp, color = Color(0xFFD84315))
        ) {
            item { top.invoke() }
            item { content.invoke() }
            item { bottom.invoke() }
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////