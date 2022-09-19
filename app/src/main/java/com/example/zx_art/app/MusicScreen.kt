package com.example.zx_art.app

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.zx_art.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun MusicScreen() {
//    var tuneId = remember { mutableStateOf(-1) }
//    var tuneUrl = remember { mutableStateOf("") }
//    var isPlaying = remember { mutableStateOf(false) }
    val scroll by remember { mutableStateOf(ScrollState(0)) }

    var playPause by remember { mutableStateOf(R.drawable.pause_icon) }

    val audioButtonsModifier = Modifier
        .padding(end = 16f.dp)
        .size(32f.dp)
        .offset(y = (-2f).dp)

//    val ms = MusicService(LocalContext.current).apply {
//        onStartCommand(Intent("PLAY"), 0, 0)
//    }

    remember { MKey }

//    val syncProgress = remember { mutableStateOf(0.0f) }


//    var forward by remember { mutableStateOf(false) }
//    var stopTime = rememberCoroutineScope()

//    var startScroll by remember { mutableStateOf(true) }
//
//    LaunchedEffect(startScroll) {
//        startScroll = false
//        println(startScroll)
//        stopTime.launch {
//            while (true) {
////                delay(16)
//                if (scroll.value == 0) forward = false
//                if (scroll.value == scroll.maxValue) forward = true
//
//                if (forward) {
//                    scroll.animateScrollBy(-4f, animationSpec = tween(8,1))
//                } else {
//                    scroll.animateScrollBy(4f, animationSpec = tween(8,1))
//                }
//            }
//        }
//    }


    Column(Modifier.fillMaxSize()) {


        Column(
            Modifier
                .weight(1f)
                .padding(4.dp)
                .background(MaterialTheme.colors.onSecondary)
                .padding(4.dp)
        ) {

//            MusicSyncProgress()
//            UploadProgress()
            TuneLabels()


        }

        TunesListControl()


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 0.dp)
                .padding(4.dp)
                .background(MaterialTheme.colors.primaryVariant)
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.error)
//                .width(IntrinsicSize.Min)
            ) {

                    PlayLine()
                    Text(
                        text = MKey.playingTuneTitle,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(start = 2f.dp, end = 2f.dp)
                            .weight(1f)
//                            .align(Alignment.CenterVertically)
                            .horizontalScroll(scroll),
                    )

//                IconButton(modifier = Modifier
//                    .padding(end = 0.dp),
//                    onClick = {
//
//                        if (exo?.isPlaying!!) {
//                            exo?.pause()
//                            MKey.playPauseLabel = false
//                        } else {
//                            exo?.play()
//                            MKey.playPauseLabel = true
//                        }
//                    }) {
//                    Icon(
//                        imageVector = if (MKey.playPauseLabel) Icons.Rounded.PlayArrow else Icons.TwoTone.Send,
//                        contentDescription = null
//                    )
//                }

                Image(painter = painterResource(id = R.drawable.backward_icon),
                    contentDescription = null,
                    modifier = audioButtonsModifier
                        .clickable {
                            exo?.seekToPrevious()
                        }
                        .align(Alignment.CenterVertically)
                )

                Image(painter = painterResource(id = playPause),
                    contentDescription = null,
                    modifier = audioButtonsModifier
                        .clickable {
                            playPause = when (playPause) {
                                R.drawable.pause_icon -> {
                                    exo?.play()
                                    R.drawable.play_icon
                                }
                                R.drawable.play_icon -> {
                                    exo?.pause()
                                    R.drawable.pause_icon
                                }
                                else -> error("Wrong icon play/pause button")
                            }
                        }
                        .align(Alignment.CenterVertically)
                )

                Image(painter = painterResource(id = R.drawable.forward_icon),
                    contentDescription = null,
                    modifier = audioButtonsModifier
                        .clickable {
                            if (exo != null) {

                                if (exo!!.hasNextMediaItem()) {
                                    exo?.seekToNext()
                                } else {
                                    MKey.corDef.launch {
                                        withContext(this.coroutineContext) {
                                            MKey.showingTunesPageNumber++
                                            while (!MKey.isPageUpload) {
                                                delay(30)
                                            }
                                            MKey.isPageUpload = false
                                            withContext(Dispatchers.Default) {
                                                // TODO йобанный паралелизм !!!!!
                                                //  Нужно как то определять когда запускать только визуальное обновление плейлиста,
                                                //  а когда визульное + обноление плейлиста полеера.
//                                                updatePlaylist()
                                            }
                                        }
                                    }
                                    // обновить страницу визуально (страница следующих треков)
                                    // обновить плейлист
                                }
                            }


                        }
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}
