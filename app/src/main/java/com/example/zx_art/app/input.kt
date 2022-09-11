package com.example.zx_art.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.zx_art.EMPTY_MESSAGE
import com.example.zx_art.ZxColor

@Composable
fun InputPage() {


    var selectedPage by remember { mutableStateOf(0) }

    Row(modifier = Modifier.padding(4f.dp).fillMaxWidth(),horizontalArrangement = Arrangement.Center) {

        BasicTextField(
            value = if (selectedPage < 1) EMPTY_MESSAGE else selectedPage.toString(),
            onValueChange = { d ->
                selectedPage = try {
                    d.toInt()
                } catch (e: Exception) {
                    0
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = {
                MKey.showingTunesPageNumber = selectedPage - 1
                selectedPage = 0
            }),
            modifier = Modifier
                .width(64f.dp)
                .height(24f.dp)
                .border(width = 1f.dp, color = ZxColor.BORDER)
                .background(color = ZxColor.TUNE_LABEL_LIST_2)
                .align(Alignment.CenterVertically)
        )
    }

}