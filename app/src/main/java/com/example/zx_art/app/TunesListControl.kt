package com.example.zx_art.app

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.zx_art.net.Request
import kotlinx.coroutines.*


/*
    управление списком мелодий:
        навигация по страницам
        установка кол-ва отображаемых мелодий на странице
 */
@Composable
fun TunesListControl() {

    // TODO йобанный паралелизм !!!!!
    LaunchedEffect(MKey.showingTunesPageNumber) {
        MKey.isPageUpload = withContext(coroutineContext) {
            println("ready to upload page")
            Request.uploadPage()
            true
        }
    }

    LaunchedEffect(MKey.showingTunesCount){
        // обновить список при смене кол-ва отображаемых мелодий на странице (только визуально)
        Request.uploadPage()
        Log.i("showingTunesCount", "${MKey.showingTunesCount}")
    }


    LaunchedEffect(MKey.isPageUpload){
        println("ready to update playlist")
        updatePlaylist()
    }

    TunesPerPageCount()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 0.dp, end = 0.dp)
        .padding(4.dp)
        .background(color = Color(126, 119, 83, 255))
        .padding(4.dp)) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(end = 0.dp)
        ) {
            Button(onClick = {
                MKey.expandTunesListCount = !MKey.expandTunesListCount
            }, modifier = Modifier.width(IntrinsicSize.Min)) {
                Text(text = MKey.showingTunesCount.toString())
            }
            TunePagesLabel()
        }
    }
}

/*
    отображение меток страниц  (1 2 3 4 5 ... 14532)
 */
@Composable
private fun TunePagesLabel() {

    @Composable
    fun link(pageNumber: Int) {
        Text(text = "${pageNumber + 1}", modifier = Modifier
            .padding(start = 8.dp)
            .clickable {
                MKey.showingTunesPageNumber = pageNumber
            },
            color = if (pageNumber == MKey.showingTunesPageNumber) Color.Green else Color.DarkGray

        )
    }

    val hScrollState by remember { mutableStateOf(ScrollState(0)) }

    Column(modifier = Modifier.padding(4.dp)) {

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(text = "Tunes:", modifier = Modifier.weight(1f))
            Text(text = "${MKey.tunesTotalAmount}",
                modifier = Modifier.padding(end = 0.dp))
        }

        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .horizontalScroll(hScrollState)
        ) {
            val pagesCount = MKey.tunesTotalAmount / MKey.showingTunesCount

            if (MKey.showingTunesPageNumber > pagesCount) {
                // TODO выбор страницы сбрасываю на ноль если текущая страница не существует.
                //  Не существовать она может при смене кол-ва отображаемых мелодий на более высокое значение.
                MKey.showingTunesPageNumber = 0
            }

            var currentPage = MKey.showingTunesPageNumber - 2

            // start
            if (MKey.showingTunesPageNumber > 2) {
                link(pageNumber = 0)
                Text(text = "...", modifier = Modifier.padding(start = 8.dp))
                while (currentPage < MKey.showingTunesPageNumber) {
                    link(pageNumber = currentPage++)
                }
            } else {
                currentPage = 0
                repeat(2) {
                    link(pageNumber = currentPage++)
                }
            }
            // end
            val plus3Page = currentPage + 3
            while (currentPage < plus3Page) {
                link(pageNumber = currentPage)
                if (++currentPage > pagesCount) break
            }
            if (--currentPage < pagesCount) {
                Text(text = "...", modifier = Modifier.padding(start = 8.dp))
                link(pageNumber = pagesCount)
            }

        }
    }
}


private val perPageList = listOf(64, 32, 16)

/**
всплывающий список отображаемый какое кол-во мелодий отображать на странице
 */
@Composable
private fun TunesPerPageCount() {
    DropdownMenu(expanded = MKey.expandTunesListCount,
        onDismissRequest = { MKey.expandTunesListCount = false }) {
        perPageList.forEach {
            DropdownMenuItem(onClick = {
                MKey.showingTunesCount = it
                MKey.expandTunesListCount = false
//                updateTuneLabels()
            }, modifier = Modifier
                .background(Color(98, 84, 107, 255))
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
            ) {
                Text(text = "$it")
            }
        }
    }
}
