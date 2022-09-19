package com.example.zx_art

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.zx_art.additional.CentralPopupMenu
import com.example.zx_art.app.*
import com.example.zx_art.app.playlist.PlayListCatalog
import com.example.zx_art.net.Request
import com.example.zx_art.ui.theme.ZXArtTheme
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.launch

// Jetpack Compose Navigation в многомодульном проекте
// https://habr.com/ru/company/moex/blog/586192/

@OptIn(ExperimentalComposeUiApi::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // получить кол-во мелодий на сервере
        MKey.corDef.launch {
            MKey.tunesTotalAmount = Request.getTunesMain()?.totalAmount ?: 0

//            var playingColorId = 0
//            while (true) {
//                delay((1f/25f*1000f).toLong())
//                MKey.playingColor = zxColor[playingColorId]
//                playingColorId = ++playingColorId and 7
//            }
        }
//        MKey.corMain.launch {
//
//        }


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
        setContent {


            ZXArtTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(

                        topBar = {
                            TopBar()
                        },
                        content = {
                            exoInit(LocalContext.current)


                            MusicScreen()
                        },
                        bottomBar = {
                            MKey.keyboardController = LocalSoftwareKeyboardController.current
                        }
                    )
                    TuneInfo()
                    PlayListCatalog()
                    MainMenuList()
                    CentralPopupMenu()
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