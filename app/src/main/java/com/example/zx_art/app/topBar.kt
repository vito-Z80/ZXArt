package com.example.zx_art.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.zx_art.PopupList
import com.example.zx_art.R
import com.example.zx_art.ZxButton

@Composable
fun TopBar() {
    Row(
//        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
//            .height(IntrinsicSize.Min)
            .padding(4f.dp)
            .background(
                color = Color(163, 163, 163, 255),
                AbsoluteRoundedCornerShape(topLeft = 16.dp,
                    topRight = 16.dp)
            )
            .padding(8f.dp)
    ) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .height(64f.dp), verticalArrangement = Arrangement.Center) {

            Row(modifier = Modifier.horizontalScroll(ScrollState(0))) {

                ZxButton(text = " Tunes ", style = MaterialTheme.typography.subtitle2) {

                }
                Spacer(modifier = Modifier.padding(end = 2f.dp))
                PlaylistButton()
            }

            Spacer(modifier = Modifier.padding(top = 2f.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(ScrollState(0))) {


                repeat(10) {

                    ZxButton(text = " Tunes $it", style = MaterialTheme.typography.subtitle2) {

                    }
                    Spacer(modifier = Modifier.padding(end = 2f.dp))
                }

            }

        }
        Row(modifier = Modifier
            .padding(end = 8f.dp)
            .size(128f.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
//                modifier = Modifier
//                    .height(64f.dp)
            )
        }


        // TODO использовать кусок для прогрузки изображений.
//        SubcomposeAsyncImage(model = "https://zxart.ee/project/images/public/logo.png",
//            contentDescription = null,
//            modifier = Modifier.height(128.dp)) {
//            val state = painter.state
//            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
//                CircularProgressIndicator()
//            } else {
//                SubcomposeAsyncImageContent()
//            }
//        }
    }
}


@Composable
private fun PlaylistButton() {
    ZxButton(text = " Playlist ", style = MaterialTheme.typography.subtitle2) {
        MKey.showPlaylistCatalog = true
    }
}
