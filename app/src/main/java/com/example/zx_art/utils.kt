package com.example.zx_art

import android.icu.text.SimpleDateFormat
import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.zx_art.app.MKey
import java.util.*

////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////


fun zxArtTime(time: String?): String {
    return time?.split(".")?.get(0)?.split(":").let {
        // FIXME если время по нулям то заменять двойными значаками вопросов (??:??)
        String.format("%02d:%02d",
            it?.get(0)?.toLong() ?: 0,
            it?.get(1)?.toLong() ?: 0)
    }
}

fun tuneInfoDateCreated(): String {
    return try {
        SimpleDateFormat("dd.MM.yyyy", Locale("EN")).format(Date(MKey.dateCreated * 1000))
    } catch (e: Exception) {
        UNDEFINED_MESSAGE
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
fun decodeText(text: String?) =
    Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)?.toString() ?: UNDEFINED_MESSAGE

////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun ZxButton(text: String, textOffset: Offset = Offset(0f, 0f), clickable: () -> Unit) {
    Text(text = text,
        modifier = Modifier
            .border(width = 1f.dp, color = Color(87, 109, 126, 255))
            .background(color = Color(105, 157, 201, 255))
            .clickable { clickable.invoke() }
            .offset(x = textOffset.x.dp, y = textOffset.y.dp)
            .padding(2f.dp),
        color = Color.Green,
        style = MaterialTheme.typography.h2)
}

////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun AppInput(
    text: MutableState<String?>,
    size: DpSize,
    keyboardType: KeyboardType = KeyboardType.Text,
    onDone: () -> Unit,
) {

    Row(modifier = Modifier
        .padding(4f.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {

        // TODO нужно что бы клавиатура вылазила при нажатии на кнопку NEW

        BasicTextField(
            value = text.value ?: EMPTY_MESSAGE,
            onValueChange = { text.value = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(onDone = { onDone.invoke() },onGo = {onDone.invoke()}),
            modifier = Modifier
                .width(size.width)
                .height(size.height)
                .border(width = 1f.dp, color = ZxColor.BORDER)
                .background(color = ZxColor.TUNE_LABEL_LIST_2)
//                .padding(4f.dp)
                .align(Alignment.CenterVertically)
        )
    }
}