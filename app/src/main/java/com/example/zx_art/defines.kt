package com.example.zx_art

import androidx.compose.ui.graphics.Color


class ZxColor {

    companion object {

        val RED = Color(236, 101, 101, 255)
        val BLUE = Color.Blue


        val TUNE_LABEL_LIST_1 = Color.LightGray
        val TUNE_LABEL_LIST_2 = Color.White
        val LIST_DIVIDER = Color.Cyan
    }

}

const val FRAME = (1f / 60f * 1000f).toLong()

val zxColor = listOf(
    Color.Black,
    Color.Blue,
    Color.Red,
    Color.Magenta,
    Color.Green,
    Color.Cyan,
    Color.Yellow,
    Color.White,
)

const val UNDEFINED_MESSAGE = "undefined"
const val LOADING_MESSAGE = "Loading"
const val EMPTY_MESSAGE = ""
const val DOUBLE_Q = "??"


const val GOOGLE_TUNE = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"