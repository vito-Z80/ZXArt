package com.example.zx_art

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.zx_art.app.*
import com.example.zx_art.net.Request
import com.example.zx_art.ui.theme.ZXArtTheme
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // получить кол-во мелодий на сервере
        MKey.corDef.launch {
            MKey.tunesTotalAmount = Request.getTunesMain()?.totalAmount ?: 0
        }
        MKey.corMain.launch {
            while (true) {
                delay(1500)
                if (exo?.contentPosition != null) {
                    val minutes = exo?.contentPosition!! / 1000 / 60
                    val seconds = exo?.contentPosition!! / 1000 % 60
                    println(String.format("%02d:%02d", minutes, seconds))
                }
//                println(exo?.contentPosition)
//                println(exo?.contentBufferedPosition)
//                println(exo?.bufferedPosition)
//                println(exo?.bufferedPercentage)
//                println("------------------------------------")
            }
        }


        setContent {

            ZXArtTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(

                        topBar = {
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp)
                                    .padding(4.dp)
                                    .background(
                                        color = Color(163, 163, 163, 255),
                                        AbsoluteRoundedCornerShape(topLeft = 16.dp,
                                            topRight = 16.dp)
                                    )
                                    .padding(8.dp)
                            ) {

                                SubcomposeAsyncImage(model = "https://zxart.ee/project/images/public/logo.png",
                                    contentDescription = null,
                                    modifier = Modifier.height(128.dp)) {

                                    val state = painter.state
                                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                        CircularProgressIndicator()
                                    } else {
                                        SubcomposeAsyncImageContent()
                                    }
                                }
//                                Image(
//                                    painter = rememberAsyncImagePainter("https://zxart.ee/project/images/public/logo.png"),
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .height(128.dp)
//                                    )
                            }
                        },
                        content = {
                            exoInit(LocalContext.current)



                            MusicScreen()
                        },
                        bottomBar = {}
                    )
                    // https://medium.com/wizeline-mobile/jetpack-compose-animations-i-f46024bcfa37
                    LoadingMessage()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
//            releaseExo()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
//            releaseExo()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            Unit
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
//        releaseExo()
        Log.e("Destroy", "Done")
    }
}