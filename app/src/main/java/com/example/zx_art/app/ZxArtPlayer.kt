package com.example.zx_art.app

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.zx_art.GOOGLE_TUNE
import com.example.zx_art.UNDEFINED_MESSAGE
import com.example.zx_art.decodeText
import com.example.zx_art.parser.ZxArtMusic
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Timeline
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

var exo: ExoPlayer? = null

fun exoInit(context: Context) {
    if (exo != null) {
        println("INIT")
        return
    }
    exo = ExoPlayer.Builder(context)
        .build()

    exo?.addListener(playbackState())

//    exo?.setMediaItems(l)
    exo?.playWhenReady = playWhenReady
//    exo?.seekTo(MKey.ID, 0L)
    exo?.prepare()
}


//  https://exoplayer.dev/playlists.html

fun updatePlaylist() {
//    val l = arrayListOf<MediaItem>()
//    MKey.showingTunes?.forEach {
//        // TODO нужна мелодия обзначающая отсутсвие мелодии на сервере !!!
//        val t = MediaItem.fromUri(it.mp3FilePath
//            ?: "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
//        l.add(t)
//    }
//    exo?.setMediaItems(l)


    MKey.corMain.launch {
        val time = measureTimeMillis {

            exo?.setMediaItems(MKey.showingTunes?.map {
                MediaItem.fromUri(it.mp3FilePath
                    ?: "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
            }?.toMutableList() ?: mutableListOf())
        }
        println("PlayList: $time")
    }


}


fun updateExoList() {
    exo?.clearMediaItems()
    MKey.showingTunes?.forEach {
        // FIXME возможно внести в MediaItem больше информации типа metadata
        val title = decodeText(it.title)
        val t = MediaItem.Builder().setUri(it.mp3FilePath ?: GOOGLE_TUNE).setMediaId(title).build()
//        val t = MediaItem.Builder().setUri(it.mp3FilePath ?: GOOGLE_TUNE).setTag(it).build()  // толкать сюда данные трека с сайта ?
        exo?.addMediaItem(t)
    }
    println("MEDIA COUNT: ${exo?.mediaItemCount}")
}


fun pausePlay() {
    if (exo != null && exo!!.isPlaying) {
        exo?.pause()
    } else {
        exo?.play()
    }
}
// начать проигрывать мелодию по нажатию пользователя. пауза при повторном нажатии
fun userTune(mediaItem: ZxArtMusic.ResponseData.ZxMusic, index: Int) {
    // FIXME если на паузе нажать другую мелодию она не заиграет пока не отжать паузу
    if (exo == null) return
    if (mediaItem.title == exo?.currentMediaItem?.mediaId) {
        pausePlay()
    } else {
        updateExoList()
        exo?.seekTo(index, 0L)
    }
}


object PlayList {
    /*
    текущая мелодия играет всегда кроме случаев:
        следующая мелодия
        мелодия нажатая юзером

    обновление плейлиста плеера (не визуально) происходит когда:
        проиграла последняя мелодия плейлиста -
            если плейлист не визуальный плейлист то в плейлист тащим визуальный плейлист
            иначе загружаем следующий визуальный плейлист и тащим его в плейлист
        мелодия нажата юзером - если плейлист не соответствует визуальному плейлисту

     */


    var id = mutableStateOf(-1)
    var page = mutableStateOf(-1)
    var numbers = mutableStateOf(16)

}


fun exoPlay(id: Int) {

    // TODO пересмотреть данный метод/
    if (MKey.showingTunes != null && exo != null) {
        MKey.corMain.launch {
            if (exo!!.mediaItemCount != MKey.showingTunesCount
                || MKey.PLAYING_PAGE != MKey.showingTunesPageNumber
            ) {
                println(MKey.PLAYING_PAGE != MKey.showingTunesPageNumber)
                updateExoList()
                MKey.PLAYING_PAGE = MKey.showingTunesPageNumber
            }
            exo?.seekTo(id, 0)
            exo?.play()         // нужно для выхода из режима пауза, когда нажимают другой трек
            MKey.playPauseLabel = true
            MKey.playingTuneTitle = MKey.showingTunes?.get(MKey.PLAYING_ID)?.title!!
        }
    }

}

private var playWhenReady = true
fun releaseExo() {
    exo?.let { exoPlayer ->
        MKey.PLAYING_ID = exoPlayer.currentMediaItemIndex
        MKey.PLAYING_POSITION = exoPlayer.currentPosition
        playWhenReady = exoPlayer.playWhenReady
        exoPlayer.removeListener(playbackState())
        exoPlayer.release()
    }
    exo = null
}


private fun playbackState() = object : Player.Listener {


    override fun onTimelineChanged(timeline: Timeline, reason: Int) {
        super.onTimelineChanged(timeline, reason)

    }


    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        // установка названия мелодии при изменении играющего трека
        MKey.playingTuneTitle = mediaItem?.mediaId ?: UNDEFINED_MESSAGE
    }

    override fun onEvents(player: Player, events: Player.Events) {
        super.onEvents(player, events)
        // TODO использовать переменные для данных времени трека
//        player.contentPosition
//        player.duration
//        player.contentDuration
//        player.totalBufferedDuration
//        Log.e("Playlist ID tune", "${player.currentMediaItemIndex}")
//        Log.e("Position", "${player.currentPosition}")
//        Log.e("Buffering", "${player.bufferedPosition}")


//        Player.EVENT_MEDIA_ITEM_TRANSITION
//
//        MKey.PLAYING_ID = player.currentMediaItemIndex
//        MKey.playingTuneTitle = "${MKey.showingTunes?.get(exo?.currentMediaItemIndex ?: 0)?.title}"

    }

    override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long) {
        super.onSeekForwardIncrementChanged(seekForwardIncrementMs)

    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_ENDED -> {
                val pagesCount = MKey.tunesTotalAmount / MKey.showingTunesCount
                if (MKey.showingTunesPageNumber + 1 > pagesCount) {
                    MKey.showingTunesPageNumber = 0
                } else {
//                    MKey.showingTunesPageNumber++
                }
//                MKey.PLAYING_ID = 0
//
//                MKey.playingTuneTitle = "${MKey.showingTunes?.get(0)?.title}"
//                updateExoList()
//                exo?.seekTo(0, 0)
//                println("ENDEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD")
            }
            else -> Unit
        }
//        Log.d(TAG, "Change state to $stateString")
    }
}

