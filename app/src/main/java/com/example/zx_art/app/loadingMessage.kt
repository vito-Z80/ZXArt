package com.example.zx_art.app

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zx_art.LOADING_MESSAGE
import com.example.zx_art.zxColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.sin

@Composable
fun LoadingMessage() {


    var data by remember { mutableStateOf(listOf<Float>()) }
    var timer by remember { mutableStateOf(0f) }
    val list = arrayListOf<Float>()
    var colorId by remember { mutableStateOf(0) }


    fun correctColorId(colorIndex: Int) = colorIndex and 7

    LaunchedEffect(timer, MKey.showLoadingMessage) {
        if (!MKey.showLoadingMessage) {
            timer = 0f
            return@LaunchedEffect
        }


        delay((1f / 50f * 1000f).toLong())
        list.clear()
        var time = timer
        LOADING_MESSAGE.forEach { _ ->
            val value = 48f - (abs(sin(time)) * 48f)
            list.add(value)
            time -= 0.331f
        }
        timer += 0.131f
        withContext(this.coroutineContext) {
            data = list
        }
        colorId = correctColorId(--colorId)
    }

    if (MKey.showLoadingMessage)
        Column(verticalArrangement = Arrangement.Center) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                LOADING_MESSAGE.forEachIndexed { index, letter ->
                    Text(
                        text = letter.toString(),
                        style = MaterialTheme.typography.h3,
                        modifier = if (data.isEmpty()) Modifier.offset(0.dp, 0.dp)
                        else Modifier.offset(0.dp, (data[index] - 24f).dp),
                        color = zxColor[correctColorId(colorId + index)]
                    )
                }
            }
        }

}