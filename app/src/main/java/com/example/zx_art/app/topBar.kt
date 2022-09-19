package com.example.zx_art.app

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.zx_art.R
import com.example.zx_art.ZxColor
import com.example.zx_art.additional.ZxButton

@Composable
fun TopBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(4f.dp)
            .background(
                color = Color(163, 163, 163, 255),
                AbsoluteRoundedCornerShape(topLeft = 16.dp,
                    topRight = 16.dp)
            )
            .padding(4f.dp)
    ) {

        Row( modifier = Modifier.align(Alignment.CenterVertically)) {
            MainMenuButton()
            MainMenuSearch()    // TODO search button
        }
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.logo),
                contentDescription = "Logo",
                filterQuality = FilterQuality.None,
                modifier = Modifier
                    .padding(end = 4.dp),
                alignment = Alignment.Center
            )

        }
    }
}
