package com.example.zx_art.net

import android.content.Context
import android.text.Html
import android.util.Log
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.core.app.ComponentActivity
import com.example.zx_art.EMPTY_MESSAGE
import com.example.zx_art.UNDEFINED_MESSAGE
import com.example.zx_art.app.MKey
import com.example.zx_art.app.file.ZxFile
import com.example.zx_art.decodeText
import com.example.zx_art.parser.GsonParser
import com.example.zx_art.parser.ZxArtAuthor
import com.example.zx_art.parser.ZxArtAuthorWork
import com.example.zx_art.parser.ZxArtMusic
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.random.Random

// https://zxart.ee/eng/about/api/

/*
    Объект сериализации настроек выкачки списка.
    Если файла выкачки списка не существует в приложеннии, создается новый с нулевыми значениями.



 */

fun main() {
//    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        throwable.printStackTrace()
//    }
//    val cor = CoroutineScope(Dispatchers.Default + coroutineExceptionHandler)

    val ns = NetSettings()

//    ns.test()

//    val gson = GsonParser.gson.toJson(ns)
//    println(gson)
//    val savable = MutableList<String?>(ns.coroutinesMap.size) { null }
//    val ns2 = GsonParser.gson.fromJson(gson, NetSettings::class.java)
//    ns2?.coroutinesMap?.values?.forEach {
//        println(it)
//    }
//    //
//    val fields = ArrayList<ZxArtMusic?>()
//
////    val keys = ArrayList<Int?>()
//    while (true) {
//        if (ns.coroutinesMap.entries.find { it.value != null } != null) {
//            val p = ns.coroutinesMap.entries.find { it.value != null }
//            println(p.toString())
//            if (p != null) {
//                val key = p.key
//                val value = p.value
//                cor.launch {
//                    println("Start = $key, value = $value")
//                    val url =
//                        "${Export.SITE}${Export.MUSIC}/start:$key${Export.LIMIT}$value${Export.DESC}"
//                    println(url)
//                    val text = Request.getRequest(url)
//                    val id = ns.coroutinesMap.keys.indexOf(key)
//                    savable[id] = text
//                    println("End = $key, value = $value")
//                    val des = GsonParser.deserialize<ZxArtMusic>(text)
//                    if (des != null) {
//                        fields.add(des)
//                        val ser = GsonParser.gson.toJson(des.responseData.zxMusic)
//                        ZxFile.save(context,
//                            Export.MUSIC,
//                            ",${ser.trimStart('[').trimEnd(']').trimIndent()}",
//                            mode = ComponentActivity.MODE_APPEND)
//                    }
//                }.also { ns.coroutinesMap[key] = null }
//            }
//        } else {
//            if (!savable.contains(null)) {
//                savable.forEach {
//                    println(it)
//                }
//                savable.fill(null)
//                return
//            }
//        }
//    }

}


/**
 * Объект сериализации настроек выкачки списка.
 * Если файла выкачки списка не существует в приложеннии, создается новый с базовыми значениями.
 * @param tunesOnApp - кол-во полей списка мелодий в приложении.
 * @param coroutinesCount - кол-во корутин выкачивающих список
 * @param fieldsCount - кол-во полей для выкачки на каждую корутину
 * @param coroutinesMap - карта полей для корутин. [Int] текущая позиция поля в списке, [Int] кол-во полей
 */
class NetSettings {
    var tunesOnApp = 0


    var coroutinesMap = mutableMapOf<Int, Int?>()

//    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        throwable.printStackTrace()
//    }
//    private val cor = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler)

    suspend fun upload(context: Context, cor: CoroutineScope, amount: Int?) {
        println("AMOUNT: $amount")
        if (amount == null) return
        createMap(amount)
        Log.e("cor list", coroutinesMap.toString())
        Log.e("Coroutines count", coroutinesMap.size.toString())
        val savable = MutableList<String?>(coroutinesMap.size) { null }


        // TODO сохранять скачанное потоками в файл
        //  сохранить NetSettings. Как сохранять ? при добавлении каждой новой записи или при выходе приложения ?
        //  нужно, если тырнет рубануло или батарея села и список не выкачан полностью.

        val fields = ArrayList<ZxArtMusic?>()
        val links = Stack<String>()

        val textPart = ArrayList<String?>()

        coroutinesMap.forEach { (key, value) ->
            val url =
                "${Export.SITE}${Export.MUSIC}/start:$key${Export.LIMIT}$value${Export.DESC}"
            links.add(url)
        }

        fun res20(dest: Stack<String>) = suspend {
            withContext(cor.coroutineContext) {
                repeat(dest.size) {
                    cor.launch {
                        val text = Request.getRequest(dest.pop())
                        textPart.add(text)
                    }
                }
                Log.e("launched", "")
                while (textPart.size < dest.size) {
                    delay(256)
                }
                Log.e("${dest.size} done", textPart.size.toString())
                return@withContext true
            }
        }

        val rl = 10
        while (links.isNotEmpty()) {
            val dest = Stack<String>().apply {
                repeat(rl) {
                    try {
                        push(links.pop())
                    } catch (e: EmptyStackException) {
                        e.printStackTrace()
                        return@repeat
                    }
                }
            }
            while (!res20(dest).invoke()) {
                delay(16)
            }
//            res20(dest).invoke()
            Log.e("current size", textPart.size.toString())
        }
        Log.e("all upload", true.toString())
        textPart.forEach {
            println(it)
        }

        return
        while (true) {

//            var count = 10
//            if (dest.size > 9) break
////            val response20 = res20().invoke()
//            println("size: ${dest.size}")
        }
        Log.e("end", "${textPart.size}")
        return

        var counter = 0
        while (links.isNotEmpty()) {

            repeat(10) {
                cor.launch {
//                    delay(1000)
                    if (links.empty()) return@launch
                    val text = Request.getRequest(links.pop())
                    withContext(cor.coroutineContext) {
                        textPart.add(text)
                        Log.e("text", "${textPart.last()}")
                    }
//                    tunesOnApp += coroutinesMap.values.
                    MKey.uploadProgress =
                        1f - (amount - tunesOnApp).toFloat() / amount.toFloat()
                }
            }
            delay(1000L)
        }

        Log.e("END", "${textPart.size}")

        return
        links.forEach {
            cor.launch {
                delay(1000)
                val text = Request.getRequest(it)
                withContext(cor.coroutineContext) {
                    textPart.add(text)
                    Log.e("text", "${textPart.last()}")
                }
            }
        }
        Log.e("END", "${textPart.size}")
        textPart.forEach {
            println("end: $it")
        }
        return
        coroutinesMap.forEach { (key, value) ->
            cor.launch {

                println("Start = $key, value = $value")
                val url =
                    "${Export.SITE}${Export.MUSIC}/start:$key${Export.LIMIT}$value${Export.DESC}"
                Log.e("url", url)
                val text =
                    withContext(cor.coroutineContext) { return@withContext Request.getRequest(url) }
//                savable[id] = text

                val des = GsonParser.deserialize<ZxArtMusic>(text)
                Log.e("des", "${des?.responseData?.zxMusic?.size} | $key | $value")
                if (des != null) {
                    fields.add(des)
                    val ser = GsonParser.gson.toJson(des.responseData.zxMusic)
                    ZxFile.save(context,
                        Export.MUSIC + "Test",
                        ",${ser.trimStart('[').trimEnd(']').trimIndent()}",
                        mode = ComponentActivity.MODE_APPEND)
                    tunesOnApp += value ?: 0
                    Log.e("tunes", tunesOnApp.toString())
                }
                MKey.uploadProgress =
                    1f - (amount - tunesOnApp).toFloat() / amount.toFloat()
                Log.e("Progress", MKey.uploadProgress.toString())
                println("End = $key, value = $value")
            }
        }

        return

        while (true) {
            delay(30)
            if (coroutinesMap.entries.find { it.value != null } != null) {
                val p = coroutinesMap.entries.find { it.value != null }
                println(p.toString())
                if (p != null) {
                    val key = p.key
                    val value = p.value
                    coroutinesMap[key] = null
                    val id = coroutinesMap.keys.indexOf(key)
                    cor.launch {
                        println("Start = $key, value = $value")
                        val url =
                            "${Export.SITE}${Export.MUSIC}/start:$key${Export.LIMIT}$value${Export.DESC}"
                        println(url)
                        val text = Request.getRequest(url)
                        savable[id] = text

                        val des = GsonParser.deserialize<ZxArtMusic>(text)
                        Log.e("des", "${des?.responseData?.zxMusic?.size}")
                        if (des != null) {
                            fields.add(des)
                            val ser = GsonParser.gson.toJson(des.responseData.zxMusic)
//                            ZxFile.save(context,
//                                Export.MUSIC + "Test",
//                                ",${ser.trimStart('[').trimEnd(']').trimIndent()}",
//                                mode = ComponentActivity.MODE_APPEND)
                            tunesOnApp += value ?: 0
                            Log.e("tunes", tunesOnApp.toString())
                        }
                        MKey.uploadProgress =
                            1f - (amount - tunesOnApp).toFloat() / amount.toFloat()
                        Log.e("Progress", MKey.uploadProgress.toString())
                        println("End = $key, value = $value")
                    }


                }
            } else {
                if (!savable.contains(null)) {
                    savable.forEach {
                        println(it)
                    }
                    savable.fill(null)
                    println("Upload = $tunesOnApp")
                    return
                }
            }
        }

    }

    /**
     * создание карты полей для корутин
     */
    private fun createMap(tunesCount: Int) {
        if (tunesCount <= 0) return
        var tc = tunesCount
        while (true) {
            val id = tunesCount - tc
            val rndCount = Random.nextInt(128, 768)
            if (tc - rndCount < 0) {
                coroutinesMap[id] = tc
                break
            }
            coroutinesMap[id] = rndCount
            tc -= rndCount
        }
        coroutinesMap.entries.removeIf { it.value == 0 }
    }

}


/*
    догрузка новых полей списка
        1) Получить кол-во мелодий, сравнить с ранее выкаченным кол-вом, догрузить остаток
        2) На каждые 100-200 полей списка создать поток. Потоков не более N штук.
            Каждый поток имеет свой порядковый номер соответствующий текущей скачиваемой части списка
        3) результат завершенного потока добавляется в общий файл json в порядке своей очереди, пока список не выгрузится полностью
            так-же результат десереализуется и добавляется в общий список мелодий внутри приложения
        4)


 */

object Export {
    const val MUSIC = "zxMusic"
    const val PICTURE = "zxPicture"
    const val SITE = "http://zxart.ee/api/export:"
    const val DESC = "/order:date,desc"
    const val LIMIT = "/limit:"

}

private val client = HttpClient(CIO)

object Request {


    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    private val cor = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler)


    private const val serverInformation =
        "Please do not close the application until the update is complete."
    private const val serverReconnect = "Reconnecting.........."


    fun getAuthorArts(who: String?) {
        if (who == null) return
        cor.launch {
            MKey.showLoadingMessage = true
            val link = "https://zxart.ee/ajaxSearch/mode:public/types:author,authorAlias,party," +
                    "group,groupAlias,zxProd,zxPicture,zxMusic,article,news,event,production/totals:1/" +
                    "query:${who.replace(" ", "%20")}/"
            println(link)
            val text = getRequest(link)
            println(text)
            val item = GsonParser.deserialize<ZxArtAuthorWork>(text)
            MKey.showLoadingMessage = false
        }
    }


    fun getAuthorDataById(authorId: Int) {
//        if (MKey.authorId >= 0) {
        cor.launch {
            MKey.showLoadingMessage = true
//            val link = "http://zxart.ee/api/export:authorAlias/filter:authorAliasId=$authorId"
            val link = "http://zxart.ee/api/export:author/filter:authorId=${authorId}"
            println(link)
            val text = getRequest(link)//  получить список из l элкментов с сервера
            val item = GsonParser.deserialize<ZxArtAuthor>(text)
            MKey.nick = decodeText(item?.responseData?.author?.map { it?.title }?.joinToString())
            MKey.realName =
                decodeText(item?.responseData?.author?.map { it?.realName }?.joinToString())
            MKey.city = decodeText(item?.responseData?.author?.map { it?.city }?.joinToString())

            MKey.importIds = item?.responseData?.author?.get(0)?.importIds

            MKey.showLoadingMessage = false
        }
//        }
    }

//    private suspend fun clearListFromEnd(ret: () -> Unit) {
//        cor.launch {
//            while (MKey.showingItems.isNotEmpty()) {
//                delay(FRAME)
//                MKey.showingItems.last { it?.value == null }
//            }
//            ret.invoke()
//        }
//    }


    fun uploadPage() {
        // 1) обнулить все итемы с конца по одному каждый кадр (в отдельной корутине)
        // 2) во время обнуления обращение к серверу и гсону.
        // 3) если 2 и 1 выполенено заполнить список по одному итему каждый кадр, иначе дождаться 2 и 1
        // 4)
//        cor.launch {
//            var listCleared = false
//            MKey.showLoadingMessage = true
//            clearListFromEnd { listCleared = true }
//
//            val start = MKey.showingTunesPageNumber * MKey.showingTunesCount
//            val link =
//                "${Export.SITE}${Export.MUSIC}/start:$start${Export.LIMIT}${MKey.showingTunesCount}${Export.DESC}"
//            val text = getRequest(link)//  получить список с сервера
//            val page = GsonParser.deserialize<ZxArtMusic>(text)
//
//            while (!listCleared) {
//                delay(FRAME)
//            }
//            page?.responseData?.zxMusic?.forEachIndexed {id,item->
//                MKey.showingItems[id]?.value = item
//                delay(FRAME)
//            }
//        }
        //////////////////////////////////////////////////////////////
        cor.launch {
            MKey.showLoadingMessage = true
            val start = MKey.showingTunesPageNumber * MKey.showingTunesCount
            val link =
                "${Export.SITE}${Export.MUSIC}/start:$start${Export.LIMIT}${MKey.showingTunesCount}${Export.DESC}"
            val text = getRequest(link)//  получить список с сервера
            val page = GsonParser.deserialize<ZxArtMusic>(text)
            MKey.showingTunes = page?.responseData?.zxMusic
            MKey.showLoadingMessage = false
        }
    }


    val ns = NetSettings()

    fun upload(context: Context) {
        cor.launch {
            ns.upload(context, cor, getTunesMain()?.totalAmount)
        }
    }


    /*
        Получить кол-во всех мелодий на сервере
        Проверить файл сохраненный с сервера в приложении, сравнить кол-во записей файла с кол-вом
            всех мелодий сервера.

        Если с сервера сгружены не все записи мелодий, то догрузить оставшиеся по limit частям
        иначе выходим

        Догрузка в файл приложения должна осуществляться как вставка в джсон


     */
//    fun uploadTunes(context: Context) {
//        cor.launch {
//            val limit = MKey.tunesAmountByOnceUpload
//            val main = getTunesMain()
//
//
//            if (main != null) {
//                if (main.responseStatus.lowercase() != "success") {
//                    MKey.serverMessage =
//                        "ACCESS DENIED !!!\nВладелец сервера ограницил доступ к бд."
//                    return@launch
//                }
//                MKey.zxArtMusic = main
////                MKey.tunesTotalAmount = main.totalAmount
//                MKey.tunesTotalAmount = 3000
//                val data = ZxFile.open(context, Export.MUSIC)?.trim()
//                if (data != null) {
//                    val tunes = GsonParser.deserialize<ZxArtMusic>(data)
//
//                    MKey.zxArtMusic = tunes
//                    updateShowedTunesByList(tunes, ArrayList())
//                    MKey.tunesUploadPosition = tunes?.responseData?.zxMusic?.size!!
//                    Log.e("tunesUploadPosition", MKey.tunesUploadPosition.toString())
//                    Log.e("lost", (MKey.tunesTotalAmount - MKey.tunesUploadPosition).toString())
//                    Log.e("amount", (tunes.responseData.zxMusic.size).toString())
//
//                    // TODO как то надо догружать в gson файл новые треки.
//                    //  Добавлять в конец файла нельзя, нужно добавлять в массив
//                    return@launch
////                    var inFileCount: Int = tunes?.responseData?.zxMusic?.size!!
////                    if (inFileCount == MKey.tunesTotalAmount) {
////                        // all tunes is upload
////                        println("all music grabs from server///////")
////                        return@launch
////                    }
////                    val addedText = StringBuilder()
////                    var l = limit
////                    while (inFileCount < MKey.tunesTotalAmount) {
////                        if (inFileCount + l > MKey.tunesTotalAmount) {
////                            l = MKey.tunesTotalAmount - inFileCount
////                        }
////                        val text =
////                            getRequest("${Export.SITE}${Export.MUSIC}/start:${inFileCount}${Export.LIMIT}$l${Export.DESC}")
////                        ZxFile.save(context, Export.MUSIC, text)
////                        MKey.tunesUploadPosition += l
////                        addedText.append(text)
////                        inFileCount += l
////                    }
////                    addedText.insert(0, data)
////                    MKey.zxArtMusic = GsonParser.deserialize(addedText.toString())
//                } else {
//                    // выполняется один раз при первом запуске приложения
//                    var l = limit
//                    val allTunes = ArrayList<ZxArtMusic?>()     // шаблон списков
//                    val newArray =
//                        arrayListOf<ZxArtMusic.ResponseData.ZxMusic>()   // шаблон отображаемого списка
//                    // первая мелодия была получена раньше при получении кол-ва всех мелодий на сервере
//                    // добавить ее в начало списка
//                    newArray.add(main.responseData.zxMusic[0])
////                    var addTmpToList:Boolean? = false
//                    while (MKey.tunesUploadPosition < MKey.tunesTotalAmount) {
//                        if (MKey.tunesUploadPosition + l > MKey.tunesTotalAmount) {
//                            // если l > чем осталось подгрузить мелодий = уменьшаем l
//                            l = MKey.tunesTotalAmount - MKey.tunesUploadPosition
//                        }
//                        val text =  //  получить список из l элкментов с сервера
//                            getRequest("${Export.SITE}${Export.MUSIC}/start:${MKey.tunesUploadPosition}${Export.LIMIT}$l${Export.DESC}")
//
//                        if (text == null) {
//                            MKey.serverMessage = serverReconnect
//                            l = limit
//                            continue
//                        }
//                        MKey.serverMessage = serverInformation
//                        // новый объект со списком полученных мелодий с сервера
//                        val newZxArtMusicObject = GsonParser.deserialize<ZxArtMusic>(text.trim())
//                        allTunes.add(newZxArtMusicObject)   // добавить полученный спиксок в шаблон
//                        MKey.tunesUploadPosition += l
//
//                        updateShowedTunesByList(newZxArtMusicObject, newArray)
//                    }
//                    allTunes.forEach {
//                        // перенести все списки шаблона в основной
//                        main.responseData.zxMusic.addAll(it?.responseData?.zxMusic!!)
//                    }
//                    Log.e("tunesUploadPosition", MKey.tunesUploadPosition.toString())
//                    Log.e("lost", (MKey.tunesTotalAmount - MKey.tunesUploadPosition).toString())
//                    Log.e("amount", (main.responseData.zxMusic.size).toString())
//                    // сохранить файл сгруженный с сервера целиком
//                    ZxFile.save(context, Export.MUSIC, GsonParser.serialize(main))
//                }
//            }
//        }
//    }


    suspend fun getTunesMain(): ZxArtMusic? {
        val data = getRequest("${Export.SITE}${Export.MUSIC}${Export.LIMIT}1${Export.DESC}")
        MKey.tunesUploadPosition += 1
        return GsonParser.deserialize(data)
    }

//    var response: HttpResponse? = null

    suspend fun post(url: String): String {
        val resp = client.post(url)
        Log.e("request STATUS", "${resp.status}")
        Log.e("response STATUS", "${resp.call.response.status}")
        val ret = resp.bodyAsText()
        Log.e("request STATUS", "${resp.status}")
        Log.e("response STATUS", "${resp.call.response.status}")
        return ret
    }

    suspend fun getRequest(url: String): String? {
//        delay(30)


        val resp = client.request(url.escapeHTML())
        try {
            Log.e("request STATUS", "${resp.status}")
            Log.e("response STATUS", "${resp.call.response.status}")
            return resp.bodyAsText(Charsets.UTF_8)
        } catch (io: io.ktor.utils.io.errors.IOException) {
            Log.e("request STATUS", "${resp.status}")
            Log.e("response STATUS", "${resp.call.response.status}")
            io.printStackTrace()
        }
        return null
        val response: HttpResponse? = null

        println("url: $url")
        try {
            response = client.get(url)
            println("Server status: ${response.status}")

            if (response.status == HttpStatusCode.RequestTimeout) {
                println("TIMEOUT: $response")
//                client.close()
                return null
            }
            println("RESPONSE: $response")
//            client.close()
            return response.bodyAsText(Charsets.UTF_8)
        } catch (io: io.ktor.utils.io.errors.IOException) {
            println("Server status: ${response?.status}")
//            client.close()
            return null
        }
    }

}

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
suspend fun tttt() {
//    val client = HttpClient()
    println("tttt")
//    val statement = client.request(""){
//        HttpRequestBuilder{URLBuilder("")}
//    }
    val h = HttpStatement(
        builder = HttpRequestBuilder { URL("https://zxart.ee/api/export:zxMusic/limit:1000") },
        HttpClient()
    )


    h.execute {
        println("exexcute")

        var offset = 0
        val byteBufferSize = 1024 * 8
        val channel: ByteReadChannel = it.body()
        val contentLen = it.contentLength()?.toInt() ?: 0
        val data = ByteArray(contentLen)

//            val currentRead = channel.readAvailable(data, offset, byteBufferSize)
//            val progress = if(contentLen == 0) 0 else ( offset / contentLen.toDouble() ) * 8
//            Log.e ("Progress","$progress")
//            offset += currentRead
        do {
            val currentRead = channel.readAvailable(data, offset, byteBufferSize)
            val progress = if (contentLen == 0) 0 else (offset / contentLen.toDouble()) * 100
            Log.d("progress", "$progress")
            offset += currentRead
        } while (currentRead >= 0)
    }

}

//  https://stackoverflow.com/questions/65082734/how-can-i-download-a-large-file-with-ktor-and-kotlin-with-a-progress-indicator
//  https://ktor.io/docs/response.html#streaming
object Application {
    @Throws(io.ktor.utils.io.errors.IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        println("main")
        CoroutineScope(Dispatchers.Unconfined).launch { tttt() }
        println("endCoroutine")
        return


        val client = HttpClient(CIO)

        val file = File("P:\\testStream.txt")

        runBlocking {
            client.prepareGet("https://zxart.ee/api/export:zxMusic/limit:1000")
                .execute { httpResponse ->
                    val channel: ByteReadChannel = httpResponse.body()
                    while (!channel.isClosedForRead) {
                        val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                        while (!packet.isEmpty) {
                            val bytes = packet.readBytes()
                            file.appendBytes(bytes)
                            println("Received ${file.length()} bytes from ${httpResponse.contentLength()}")
                        }
                    }
                    println("A file saved to ${file.path}")
                }
        }


        return

//        saveUrl("P:\\test.txt","https://zxart.ee/api/export:zxMusic/limit:10000/" )
        saveUrl("P:\\test.zip", "https://mapbasic.ru/soft/mapbasic_12.zip")

        return
//        if (2 != args.size) {
//            println("USAGE: java -jar so-downloader.jar <source-URL> <target-filename>")
//            exitProcess(1)
//        }
//        val sourceUrl = "https://zxart.ee/api/export:zxMusic/limit:1000/"
//        val targetFilename = "P:\\test.txt"
//        val bytesDownloaded = download(sourceUrl, targetFilename)
//        println(String.format("Downloaded %d bytes from %s to %s.",
//            bytesDownloaded,
//            sourceUrl,
//            targetFilename))
//    }

//    @Throws(IOException::class)
//    fun download(url: String?, fileName: String?): Long {
//        URI.create(url).toURL().openStream().use { `in` ->
//            return Files.copy(`in`,
//                Paths.get(fileName))
//        }
    }
}


@Throws(MalformedURLException::class, io.ktor.utils.io.errors.IOException::class)
fun saveUrl(filename: String?, urlString: String?) {
    var fin: BufferedInputStream? = null
    var fout: FileOutputStream? = null
    try {
        println("in")
        fin = BufferedInputStream(URL(urlString).openStream())
        println("fout")
        fout = FileOutputStream(filename)
        println("data")
        val data = ByteArray(1024)
        println("count")
        var count: Int
        println("while")
        while (fin.read(data, 0, 1024).also { count = it } != -1) {
            println(count)
            fout.write(data, 0, count)
        }
    } finally {
        fin?.close()
        fout?.close()
    }
}


////////////////////////////////////////////////////////////////////////////////////////////////////
fun goToLink(handler: UriHandler, link: String) {
    handler.openUri(link)
}
