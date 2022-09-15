package com.example.zx_art.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.example.zx_art.parser.ZxArtAuthor
import com.example.zx_art.parser.ZxArtMusic
import kotlinx.coroutines.*

@OptIn(ExperimentalComposeUiApi::class)
object MKey {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    val corDef = CoroutineScope(Dispatchers.Default + coroutineExceptionHandler)
    val corMain = CoroutineScope(Dispatchers.Main + coroutineExceptionHandler)

    init {
        corMain.launch {
            while (true) {
                delay(67)
                playingPosition = exo?.currentPosition ?: 0
            }
        }
    }

    var PLAYING_ID by mutableStateOf(0)
    var PLAYING_PAGE by mutableStateOf(0)
    var PLAYING_POSITION by mutableStateOf(0L)

    var playPauseLabel by mutableStateOf(false)             // отображение метки проигрывание/пауза

    var showPlaylistCatalog by mutableStateOf(false)        //  отображение каталога плейлистов
//    var showNewPlaylistInputName by mutableStateOf(false)   //  отображение текстфилд для ввода нвого имени каталога при создании нового каталога


    var isPageUpload by mutableStateOf(false)


    // объект представляющий информацию о треке
    var tuneInfo: ZxArtMusic.ResponseData.ZxMusic? by mutableStateOf(null)

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
    var dateCreated: Long by mutableStateOf(0L)                  // дата создания

    var importIds: ZxArtAuthor.ResponseData.Author.ImportIds? by mutableStateOf(null)                  // сторонние ссылки


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
    var playingPosition by mutableStateOf(0L)


    // remembers for keyboard
    var keyboardFocus by mutableStateOf(false)
    var keyboardController:SoftwareKeyboardController? by mutableStateOf(null)
    var focus:FocusRequester by mutableStateOf(FocusRequester())

}

//  defines

