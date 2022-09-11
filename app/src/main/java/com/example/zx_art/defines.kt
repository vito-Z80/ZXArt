package com.example.zx_art

import androidx.compose.ui.graphics.Color


class Link {
    companion object{
        const val POUET = "https://www.pouet.net/user.php?who="
        const val SWIKI = "http://speccy.info/"
        const val ZX_TUNES = "http://zxtunes.com/author.php?id="
        const val ZX_AAA = "https://zxaaa.net/view_demos.php?a="
        const val SPECTRUM_COMPUTING = "https://spectrumcomputing.co.uk/index.php?cat=999&label_id="
    }
}

class ZxColor {

    companion object {

        val RED = Color(236, 101, 101, 255)
        val BLUE = Color.Blue


        val TUNE_LABEL_LIST_1 = Color.LightGray
        val TUNE_LABEL_LIST_2 = Color.White
        val TUNE_LABEL_LIST_3 = Color.DarkGray
        val LIST_DIVIDER = Color.Cyan

        val INFO_BG = Color.LightGray
        val INFO_LABEL = Color.Yellow
        val INFO_TEXT = Color.Black

        val BORDER = Color.Black
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