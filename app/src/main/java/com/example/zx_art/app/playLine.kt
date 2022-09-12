package com.example.zx_art.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
fun PlayLine() {


    Box(modifier = Modifier
//        .padding(4f.dp)
        .height(16f.dp)
//        .fillMaxWidth(exo?.currentPosition?.toFloat() ?: 0f)
        .fillMaxWidth()
        .background(color = Color(33, 150, 243, 255))) {


        val a = 1f - ((exo?.duration
            ?: 0L) - MKey.playingPosition).toFloat() / (exo?.duration?.toFloat() ?: 0f)
        Divider(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(a),
            thickness = 16f.dp,
            color = Color(
                1,
                1,
                1,
                99))

        Divider(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(1f - ((exo?.duration ?: 0L) - (exo?.bufferedPosition
                ?: 0L)).toFloat() / (exo?.duration?.toFloat() ?: 0f)),
            thickness = 16f.dp,
            color = Color(1, 1, 1, 62))

        Row {
            Text(text = MKey.playingTuneTitle,
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f))
            val mil = (exo?.duration?.minus(MKey.playingPosition) ?: 0) / 10L % 60
            val sec = (exo?.duration?.minus(MKey.playingPosition) ?: 0) / 1000L % 60
            val min = (exo?.duration?.minus(MKey.playingPosition) ?: 0) / 1000L / 60
            if (min >= 0L && mil >= 0L && sec >= 0L) {
                Text(text = String.format("%02d:%02d:%02d", min, sec, mil),
                    modifier = Modifier.padding(end = 0f.dp),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center)
            } else {
                Text(text = "00:00:00",
                    modifier = Modifier.padding(end = 0f.dp),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center)
            }
        }
    }
}