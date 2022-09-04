package com.example.zx_art.app

import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.exoplayer2.*
import kotlinx.coroutines.launch
import java.util.zip.CRC32
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
    MKey.corMain.launch {

        val time = measureTimeMillis {

            val l = arrayListOf<MediaItem>()
            MKey.showingTunes?.forEach {
                // TODO нужна мелодия обзначающая отсутсвие мелодии на сервере !!!
                val t = MediaItem.fromUri(it.mp3FilePath
                    ?: "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
                // TODO добавить метадату
//        val m = MediaItem.Builder()
//            .build()
//            .buildUpon()
//        m.setMediaMetadata(MediaMetadata.Builder().setTitle("").build())
//        m.setUri("")
                l.add(t)
            }
            exo?.setMediaItems(l)
            println("MEDIA COUNT: ${exo?.mediaItemCount}")
        }
        println("ExoList: $time")
    }
}

fun exoPlay(id: Int) {


    if (MKey.showingTunes != null && exo != null) {
        if (exo!!.mediaItemCount != MKey.showingTunes!!.size
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


private const val TAG = "Player"
private fun playbackState() = object : Player.Listener {


    override fun onEvents(player: Player, events: Player.Events) {
        super.onEvents(player, events)
//        Log.e("Playlist ID tune", "${player.currentMediaItemIndex}")
//        Log.e("Position", "${player.currentPosition}")
//        Log.e("Buffering", "${player.bufferedPosition}")


        Player.EVENT_MEDIA_ITEM_TRANSITION

        MKey.PLAYING_ID = player.currentMediaItemIndex
        MKey.playingTuneTitle = "${MKey.showingTunes?.get(exo?.currentMediaItemIndex ?: 0)?.title}"

    }

    override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long) {
        super.onSeekForwardIncrementChanged(seekForwardIncrementMs)
        println(seekForwardIncrementMs)
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

