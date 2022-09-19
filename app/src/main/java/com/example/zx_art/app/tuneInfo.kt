package com.example.zx_art.app

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.zx_art.*
import com.example.zx_art.R
import com.example.zx_art.additional.ZxButton
import com.example.zx_art.app.playlist.PlaylistPopup
import com.example.zx_art.net.Request
import com.example.zx_art.net.goToLink
import com.example.zx_art.parser.ZxArtMusic
import kotlinx.coroutines.withContext

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TuneInfoButton(music: ZxArtMusic.ResponseData.ZxMusic, index: Int) {

    Text(text = "i",
        modifier = Modifier
            .border(width = 1f.dp, color = Color(87, 109, 126, 255))
            .background(color = Color(105, 157, 201, 255))
            .clickable {
                MKey.tuneInfo = music
            }
            .offset(x = (3f).dp),
        color = Color.Green,
        style = MaterialTheme.typography.h2)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TuneInfo() {
    if (MKey.tuneInfo == null) return

    val playlist = remember { mutableStateOf(false) }

    LaunchedEffect(MKey.tuneInfo) {
        withContext(coroutineContext) {
            MKey.tuneInfo?.authorIds?.get(0)
                ?.let { Request.getAuthorDataById(it) }
        }.also {
            MKey.dateCreated = MKey.tuneInfo?.dateCreated ?: 0L
        }
    }

    Dialog(onDismissRequest = { MKey.tuneInfo = null }) {
        Column(modifier = Modifier
            .height(IntrinsicSize.Min)
            .border(width = 1f.dp,
                color = ZxColor.BORDER,
                shape = AbsoluteRoundedCornerShape(8f.dp))
            .background(color = ZxColor.INFO_BG, shape = AbsoluteRoundedCornerShape(8f.dp))
            .padding(8f.dp),
            verticalArrangement = Arrangement.Center) {

            Title(tuneName = MKey.tuneInfo?.title ?: UNDEFINED_MESSAGE)
            Divider(color = ZxColor.BORDER, modifier = Modifier.padding(2f.dp))

            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .height(384f.dp)
                .padding(vertical = 1f.dp)
            ) {
                item { NickName() }
                item { RealName() }
                item { Rating() }
                item { Compo() }
                item { DateCreated() }
                item { Party() }
                item { Plays() }
                item { Tags() }
                item { Type() }
                item { ImportIds() }
            }

            Divider(color = ZxColor.BORDER)
            PlaylistPopup(LocalContext.current, playlist)

            Row(modifier = Modifier
                .padding(top = 4f.dp)
                .fillMaxWidth()
                .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
//                val context = LocalContext.current
                ZxButton(text = "Add to playlist", textOffset = Offset(2f, -1f)) {
//                    MKey.tuneInfo = null
//                    MKey.showPlaylistCatalog = true
                    playlist.value = true
                }

            }
        }
    }
}


@Composable
private fun Type() {
    Label(text = "Type:",
        clickableText = AnnotatedString(MKey.tuneInfo?.type ?: UNDEFINED_MESSAGE))
}

@Composable
private fun Plays() {
    Label(text = "Plays:",
        clickableText = AnnotatedString(MKey.tuneInfo?.plays ?: UNDEFINED_MESSAGE))
}

@Composable
private fun Party() {
    Label(text = "Party:",
        clickableText = AnnotatedString(MKey.tuneInfo?.partyPlace ?: UNDEFINED_MESSAGE))
}

@Composable
private fun DateCreated() {
    Label(text = "Created:", clickableText = AnnotatedString(tuneInfoDateCreated()))
}

@Composable
private fun Compo() {
    Label(text = "Compo:",
        clickableText = AnnotatedString(MKey.tuneInfo?.compo ?: UNDEFINED_MESSAGE))
}

@Composable
private fun Rating() {
    Label(text = "Rating:",
        clickableText = AnnotatedString(MKey.tuneInfo?.rating ?: UNDEFINED_MESSAGE))
}

@Composable
private fun NickName() {
    ClickableLabel(text = "Author:",
        clickableText = AnnotatedString(MKey.nick ?: UNDEFINED_MESSAGE))
}

@Composable
private fun RealName() {
    ClickableLabel(text = "Real name:",
        clickableText = AnnotatedString(MKey.realName ?: UNDEFINED_MESSAGE))
}


@Composable
private fun Tags() {
    Row {
        Text(text = "Tags", modifier = Modifier.weight(1f))
        Column() {
            MKey.tuneInfo?.tags?.forEach {
                Text(text = it ?: EMPTY_MESSAGE)
            }
        }
    }
}

@Composable
private fun ZxLink(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    if (enabled) {
        Text(text = text, modifier = Modifier
            .padding(2f.dp)
            .fillMaxWidth(0.7f)
            .background(color = ZxColor.TUNE_LABEL_LIST_2)
            .border(width = 1f.dp, color = ZxColor.BORDER)
            .clickable { onClick.invoke() }
            .padding(4f.dp),
            color = ZxColor.BORDER,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h1
        )
    } else {
        Text(text = text, modifier = Modifier
            .padding(2f.dp)
            .fillMaxWidth(0.7f)
            .background(color = ZxColor.TUNE_LABEL_LIST_3)
            .border(width = 1f.dp, color = ZxColor.BORDER)
            .padding(4f.dp),
            color = ZxColor.BORDER,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
private fun ImportIds() {

    MKey.importIds?.`3a`

    val handler = LocalUriHandler.current
    Row {
        Text(text = "Links:", modifier = Modifier.weight(1f))
        Column() {
            ZxLink(text = "Spectrum computing", onClick = {
                goToLink(handler, "${Link.SPECTRUM_COMPUTING}${MKey.importIds?.sc}")
            }, enabled = MKey.importIds?.sc != null)

            ZxLink(text = "Zx tunes", onClick = {
                goToLink(handler, "${Link.ZX_TUNES}${MKey.importIds?.zxt}")
            }, enabled = MKey.importIds?.zxt != null)

            ZxLink(text = "Pouet", onClick = {
                goToLink(handler, "${Link.POUET}${MKey.importIds?.pouet}")
            }, enabled = MKey.importIds?.pouet != null)

            ZxLink(text = "Spectrum wiki", onClick = {
                goToLink(handler, "${Link.SWIKI}${MKey.importIds?.swiki}")
            }, enabled = MKey.importIds?.swiki != null)

            ZxLink(text = "Zx AAA", onClick = {
                goToLink(handler, "${Link.ZX_AAA}${MKey.importIds?.`3a`}")
            }, enabled = MKey.importIds?.swiki != null)

        }
    }
}

@Composable
private fun Label(text: String, clickableText: AnnotatedString) {
    Row {
        Text(text = text, modifier = Modifier.weight(1f))
        Column() {
            Text(text = clickableText)
        }
    }
}

@Composable
private fun ClickableLabel(text: String, clickableText: AnnotatedString) {
    Row {
        Text(text = text, modifier = Modifier.weight(1f))
        Column() {
            ClickableText(text = clickableText, onClick = {
                println(MKey.tuneInfo?.authorIds?.joinToString())
            })
        }
    }
}

@Composable
private fun Title(tuneName: String?) {
    Row(modifier = Modifier.fillMaxWidth(0.9f), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = decodeText(tuneName),
            style = MaterialTheme.typography.h1,
            color = ZxColor.INFO_TEXT,
            softWrap = false,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterVertically)
                .horizontalScroll(ScrollState(0))
        )
        Image(painter = painterResource(id = R.drawable.zx_logo),
            contentDescription = null,
            modifier = Modifier
                .windowInsetsEndWidth(WindowInsets(right = 80f.dp))
                .windowInsetsPadding(
                    WindowInsets(right = 0f.dp))
//                .padding(end = 0.dp), alignment = Alignment.CenterEnd
//            .offset((-80f).dp)
        )
    }
}