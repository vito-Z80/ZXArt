package com.example.zx_art.app


import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.twotone.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MusicSyncProgress() {

    val context = LocalContext.current
    val progress = animateFloatAsState(
        targetValue = 1f - (MKey.tunesTotalAmount - MKey.tunesUploadPosition).toFloat() / MKey.tunesTotalAmount.toFloat(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    if (progress < 1f) {
        Column {
            Text(text = "${MKey.tunesUploadPosition}/${MKey.tunesTotalAmount}",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 12.sp,
                color = Color.DarkGray
            )
            Row {
                LinearProgressIndicator(
                    progress = progress,
                    backgroundColor = Color.LightGray,
                    color = Color.Green,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .height(10.dp)
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 0.dp)
                        .clickable {
//                            Toast
//                                .makeText(context,
//                                    MKey.serverMessage,
//                                    Toast.LENGTH_LONG)
//                                .show()
                        }
                )
            }
            Text(text = MKey.serverMessage,
                color = Color.Red,
//                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun UploadProgress() {


    Box(modifier = Modifier.fillMaxWidth()) {
        Divider(thickness = 5f.dp, modifier = Modifier
            .fillMaxWidth(MKey.uploadProgress)
        )
    }
}