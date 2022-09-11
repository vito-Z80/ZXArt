package com.example.zx_art.app

import android.text.Html
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zx_art.*
import com.example.zx_art.net.Request
import com.example.zx_art.parser.ZxArtMusic
import com.example.zx_art.ui.theme.ZXArtTheme
import com.example.zx_art.ui.theme.tuneLinkColorDark
import com.example.zx_art.ui.theme.tuneLinkColorLight
import io.ktor.http.*

@Composable
fun TuneLabels() {


//    val context = LocalContext.current

    val playingId = remember { mutableStateOf(false) }
    val infoId = remember { mutableStateOf(-1) }
    val showInfo = remember { mutableStateOf(false) }




    LaunchedEffect(playingId.value) {
        exoPlay(MKey.PLAYING_ID)
    }

    LaunchedEffect(MKey.authorId) {
//        Request.getAuthorDataById()
    }

    ZXArtTheme {
        LazyColumn(Modifier
            .fillMaxWidth()
            .fillMaxHeight()
        ) {


//            itemsIndexed(MKey.showingItems) { id, item ->
//                if (item?.value != null) {
//                    Text(text = item.value.compo)
//                }
//            }


//            item {
//                MKey.showingItems.forEachIndexed { id, item ->
//                    if (item?.value != null) {
//                        TuneLabelLine(index = id, tune = item.value, playingId, infoId, showInfo)
//
//                        AnimatedVisibility(visible = showInfo.value && infoId.value == id) {
//                            InfoBlock(item.value)
//                            if (infoId.value == id) {
//                                MKey.authorId = item.value.authorIds[0]
//                            }
//                        }
//                    }
//                }
//            }

            // SECOND

//            itemsIndexed(items = MKey.showingTunes ?: listOf()) { id, item ->
//                TuneLabelLine(index = id, tune = item, playingId, infoId, showInfo)
//
//                AnimatedVisibility(visible = showInfo.value && infoId.value == id) {
//                    InfoBlock(item)
//                    if (infoId.value == id) {
//                        MKey.authorId = item.authorIds[0]
//                    }
//                }
//            }

            // first

            item {
                MKey.showingTunes?.forEachIndexed { index, music ->

                    Row(modifier = Modifier.fillMaxWidth()) {

                        Text(
                            text = decodeText(music.title),
                            modifier = Modifier
                                .weight(1f)
//                                .background(color = if (index % 2 == 0) tuneLinkColorLight else tuneLinkColorDark)
                                .fillMaxWidth()

                                .clickable {
                                    infoId.value = index
                                    showInfo.value = !showInfo.value
                                    userTune(music, index)
                                }
                                .padding(2f.dp)
                                .horizontalScroll(ScrollState(0)),
                            color = if (index % 2 == 0) ZxColor.TUNE_LABEL_LIST_1 else ZxColor.TUNE_LABEL_LIST_2,
                            softWrap = false,
                            style = MaterialTheme.typography.h4

                        )
                        // TODO play button in line

                        Row(horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .width(24f.dp)
                                .align(Alignment.CenterVertically)
//                                .border(width = 1f.dp, color = Color(87, 109, 126, 255))
//                                .background(color = Color(50, 74, 94, 255))

                        ) {
                            TuneInfoButton(music = music, index = index)
//                            Text(text = "i",
//                                modifier = Modifier
//                                    .border(width = 1f.dp, color = Color(87, 109, 126, 255))
//                                    .background(color = Color(105, 157, 201, 255))
//                                    .clickable { userTune(music, index) }
//                                    .offset(x = (3f).dp),
//                                color = Color.Green,
//                                style = MaterialTheme.typography.h2)
//                            Image(
//                                painter = painterResource(id = R.drawable.audio_info),
//                                contentDescription = null,
//                                colorFilter = ColorFilter.tint(color = Color.Green),
//                                modifier = Modifier
//                                    .scale(2f)
//                                )
                        }
                        Text(
                            text = zxArtTime(music.time),
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.h1,
                        )
                    }
                    Divider(color = ZxColor.LIST_DIVIDER)
                    // раскрывающийся блок информации о треке
//                    AnimatedVisibility(visible = infoId.value == index && !showInfo.value
////                    if (infoId == index) {
////                        showInfo
////                    } else false
//                    ) {
//
//                        if (infoId.value == index) {
//                            MKey.authorId = music.authorIds[0]
//                        }
//
//                        Column() {
//                            Text(text = "Compo: ${music.compo}",
//                                style = MaterialTheme.typography.h2)
//                            Row(modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable {
//                                    Request.getAuthorArts(MKey.nick)
//                                }) {
//                                Text(text = "Author:",
//                                    modifier = Modifier.weight(1f),
//                                    style = MaterialTheme.typography.h2)
//                                Text(text = "${MKey.nick}",
//                                    modifier = Modifier.padding(end = 0.dp),
//                                    style = MaterialTheme.typography.h2)
//                            }
//
//                            Row(modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable {
//                                    Request.getAuthorArts(MKey.realName)
//                                }) {
//                                Text(text = "Real name:",
//                                    modifier = Modifier.weight(1f),
//                                    style = MaterialTheme.typography.h2)
//                                Text(text = "${MKey.realName}",
//                                    modifier = Modifier.padding(end = 0.dp),
//                                    style = MaterialTheme.typography.h2)
//                            }
//
//                            Row(modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable {
//                                    Request.getAuthorArts(MKey.city)
//                                }) {
//                                Text(text = "City:",
//                                    modifier = Modifier.weight(1f),
//                                    style = MaterialTheme.typography.h2)
//                                Text(text = "${MKey.city}",
//                                    modifier = Modifier.padding(end = 0.dp),
//                                    style = MaterialTheme.typography.h2)
//                            }
//
////                            Text(text = "Author ID: ${music.authorIds}", style = MaterialTheme.typography.h1)
////                            Text(text = "Create date: ${music.dateCreated}", style = MaterialTheme.typography.h1)
////                            Text(text = "\nDescription:\n${description}\n", style = MaterialTheme.typography.h1)
////                            Text(text = "Title: ${music.internalTitle?.decodeURLPart()}", style = MaterialTheme.typography.h1)
////                            Text(text = "File name: ${music.originalFileName?.decodeURLPart()}", style = MaterialTheme.typography.h1)
////                            Text(text = "URL: ${music.originalUrl?.decodeURLPart()}", style = MaterialTheme.typography.h1)
////                            Text(text = "Time: ${music.time}", style = MaterialTheme.typography.h1)
////                            Text(text = "Rating: ${music.rating}", style = MaterialTheme.typography.h1)
//
//                            Button(onClick = { }) {
//                                Text(text = "+ Playlist", style = MaterialTheme.typography.h2)
//                            }
//                        }
//                    }
                }
            }
        }
    }
}
//--------------------------------------------------------------------------------------------------

@Composable
fun TuneLabelLine(
    index: Int,
    tune: ZxArtMusic.ResponseData.ZxMusic,
    playingId: MutableState<Boolean>,
    infoId: MutableState<Int>,
    showInfo: MutableState<Boolean>,
) {
    Row(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = decodeText(tune.title),
            modifier = Modifier
                .weight(1f)
                .background(color = if (index % 2 == 0) tuneLinkColorLight else tuneLinkColorDark)
                .fillMaxWidth()
                .clickable {
                    infoId.value = index
                    showInfo.value = !showInfo.value
                }
                .padding(2f.dp)
                .horizontalScroll(ScrollState(0)),
//                            color = Purple700,
            softWrap = false,
            style = MaterialTheme.typography.h2
        )
        Text(text = "||", modifier = Modifier
            .background(color = if (index % 2 != 0) tuneLinkColorLight else tuneLinkColorDark)
            .padding(end = 32.dp)
            .clickable {
                MKey.PLAYING_ID = index
                playingId.value = !playingId.value
            },
            style = MaterialTheme.typography.h2
        )

    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////

@Composable
fun InfoBlock(tune: ZxArtMusic.ResponseData.ZxMusic) {
    Column() {
        Text(text = "Compo: ${tune.compo}",
            style = MaterialTheme.typography.h1)
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Request.getAuthorArts(MKey.nick)
            }) {
            Text(text = "Author:",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.h1)
            Text(text = "${MKey.nick}",
                modifier = Modifier.padding(end = 0.dp),
                style = MaterialTheme.typography.h1)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Request.getAuthorArts(MKey.realName)
            }) {
            Text(text = "Real name:",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.h1)
            Text(text = "${MKey.realName}",
                modifier = Modifier.padding(end = 0.dp),
                style = MaterialTheme.typography.h1)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Request.getAuthorArts(MKey.city)
            }) {
            Text(text = "City:",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.h1)
            Text(text = "${MKey.city}",
                modifier = Modifier.padding(end = 0.dp),
                style = MaterialTheme.typography.h1)
        }

//                            Text(text = "Author ID: ${music.authorIds}", style = MaterialTheme.typography.h1)
//                            Text(text = "Create date: ${music.dateCreated}", style = MaterialTheme.typography.h1)
//                            Text(text = "\nDescription:\n${description}\n", style = MaterialTheme.typography.h1)
//                            Text(text = "Title: ${music.internalTitle?.decodeURLPart()}", style = MaterialTheme.typography.h1)
//                            Text(text = "File name: ${music.originalFileName?.decodeURLPart()}", style = MaterialTheme.typography.h1)
//                            Text(text = "URL: ${music.originalUrl?.decodeURLPart()}", style = MaterialTheme.typography.h1)
//                            Text(text = "Time: ${music.time}", style = MaterialTheme.typography.h1)
//                            Text(text = "Rating: ${music.rating}", style = MaterialTheme.typography.h1)

//        Button(onClick = { }) {
//            Text(text = "+ Playlist", style = MaterialTheme.typography.h1)
//        }

    }
}