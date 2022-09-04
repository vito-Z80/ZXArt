package com.example.zx_art.app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.zx_art.parser.ZxArtMusic
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object MKey {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    val corDef = CoroutineScope(Dispatchers.Default + coroutineExceptionHandler)
    val corMain = CoroutineScope(Dispatchers.Main + coroutineExceptionHandler)

    var PLAYING_ID by mutableStateOf(0)
    var PLAYING_PAGE by mutableStateOf(0)
    var PLAYING_POSITION by mutableStateOf(0L)

    var playPauseLabel by mutableStateOf(false)             // отображение метки проигрывание/пауза
    var NEXT by mutableStateOf(-1)
    var PREVIOUS by mutableStateOf(-1)

    var REPEAT_THIS by mutableStateOf(false)
    var REPEAT_ALL by mutableStateOf(false)


    var isPageUpload by mutableStateOf(false)
    var autoLoadPage by mutableStateOf(0)               // если изменилось, значит нужно обновить плейлист визуально и в плеере

    // objects
//    var zxArtMusic: ZxArtMusic? by mutableStateOf(null)      // полный список всех мелодий с сервера

    // TODO обрабатывать этот список, посмотреть как это будет по производительности !!!
//    val showingItems = ArrayList<MutableState<ZxArtMusic.ResponseData.ZxMusic>?>()

    var showingTunes: List<ZxArtMusic.ResponseData.ZxMusic>? by mutableStateOf(null)  //  список отображаемых мелодий
    var showingTunesCount by mutableStateOf(16)              // кол-во отображаемых мелодий а странице
    var showingTunesPageNumber by mutableStateOf(0)          // номер страницы мелодий
    var nick: String? by mutableStateOf(null)                // ник автора
    var realName: String? by mutableStateOf(null)            // настоящее имя
    var aliases: String? by mutableStateOf(null)              // псевдонимы автора
    var city: String? by mutableStateOf(null)                 // город автора
    var authorId: Int by mutableStateOf(-1)                  // ID автора


    //
    var tunesTotalAmount by mutableStateOf(0)               // кол-во мелодий на сервере
    var tunesAmountByOnceUpload by mutableStateOf(111)      // кол-во подгружаемых записей с сервера за один раз
    var tunesUploadPosition by mutableStateOf(0)            // текущая позиция загружаемой записи с сервера

    //  text
    var serverMessage by mutableStateOf("")
    var playingTuneTitle by mutableStateOf("")              // название играющей мелодии

    // expands
    var expandTunesListCount by mutableStateOf(false)       // отображение списка кол-ва мелодий для одной страницы


    // progress
    var uploadProgress by mutableStateOf(0f)

    var showLoadingMessage by mutableStateOf(false)
}

//  defines

