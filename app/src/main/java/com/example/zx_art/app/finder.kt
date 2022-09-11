package com.example.zx_art.app.file

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.zx_art.R
import com.example.zx_art.ZxColor


@Composable
fun FinderIcon(onClick: () -> Unit) {

    Box(modifier = Modifier.padding(2f.dp)) {
        Image(painter = painterResource(id = R.drawable.finder),
            contentDescription = null,
//            colorFilter = ColorFilter.tint(ZxColor.BLUE),
            Modifier
                .padding(8.dp)
                .border(width = 2f.dp, color = ZxColor.TUNE_LABEL_LIST_1, shape = AbsoluteRoundedCornerShape(4.dp))
                .background(color = ZxColor.BLUE, shape = AbsoluteRoundedCornerShape(4.dp))

                .clickable {
                    onClick.invoke()
                }
                .padding(8.dp)
        )
    }

}