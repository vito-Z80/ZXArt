package com.example.zx_art.app

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.example.zx_art.R
import com.example.zx_art.parser.ZxArtMusic

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TuneInfo(music: ZxArtMusic.ResponseData.ZxMusic, index: Int) {

    // FIXME эта функция не должна быьб в каждом треке !!! !!!
    var visible by remember { mutableStateOf(false) }

    Text(text = "i",
        modifier = Modifier
            .border(width = 1f.dp, color = Color(87, 109, 126, 255))
            .background(color = Color(105, 157, 201, 255))
            .clickable {
                visible = true
            }
            .offset(x = (3f).dp),
        color = Color.Green,
        style = MaterialTheme.typography.h2)
    if (visible)
        Dialog(onDismissRequest = { visible = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                securePolicy = SecureFlagPolicy.Inherit
            )) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 64f.dp, vertical = 96f.dp)
                .border(width = 1f.dp,
                    color = Color.DarkGray,
                    shape = AbsoluteRoundedCornerShape(8f.dp))
                .background(color = Color.LightGray, shape = AbsoluteRoundedCornerShape(8f.dp))
            ) {

                Column(modifier = Modifier
                    .padding(4f.dp)
                ) {
                    Title(tuneName = music.title)
                    Divider(modifier = Modifier.padding(2f.dp), thickness = 2f.dp)
                    Column(modifier = Modifier) {

                        repeat(10) {
                            Text(text = "item: $it",
                                color = Color.DarkGray,
                                style = MaterialTheme.typography.h1)
                        }
                    }
                }
            }
        }
}

@Composable
private fun Title(tuneName: String?) {
    Row(modifier = Modifier.fillMaxWidth(0.9f), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "$tuneName",
            style = MaterialTheme.typography.h1,
            color = Color.Blue,
            softWrap = false,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterVertically)
                .horizontalScroll(ScrollState(0))
        )
        Image(painter = painterResource(id = R.drawable.zx_logo),
            contentDescription = null,
            modifier = Modifier.windowInsetsEndWidth(WindowInsets(right = 80f.dp)).windowInsetsPadding(
                WindowInsets(right = 0f.dp))
//                .padding(end = 0.dp), alignment = Alignment.CenterEnd
//            .offset((-80f).dp)
        )
    }
}