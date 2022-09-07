package com.example.zx_art.parser

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException


object GsonParser {
    val gson = GsonBuilder().setLenient().create()
//    private val gson = GsonBuilder().disableHtmlEscaping().setLenient().create()


    fun serialize(obj: API): String = gson.toJson(obj)


    inline fun <reified T : API> deserialize(jsonText: String?): T? {


//        if (zxArt.containsKey(T::class.java.simpleName)) return zxArt[T::class.java.simpleName]

        val r = tryGson(
            jsonText,
            T::class.java,
            T::class.java.simpleName
        )


        return if (r == null) null else r as T

//        return try {
//            val api = gson.fromJson(jsonText.trimIndent(), T::class.java)
//            zxArt[T::class.java.simpleName] = api
//            api
//        } catch (_: JsonSyntaxException) {
//            null
//        }
    }

    @Synchronized
    fun tryGson(jsonText: String?, api: Class<out API>, simpleName: String) = try {
//        val a = gson.newBuilder().disableHtmlEscaping().disableHtmlEscaping().create()
//        val text = Resources.getSystem().assets.open("src/main/res/tunes.json").bufferedReader(Charsets.UTF_8)
//            .use { it.readText() }.trimIndent()
//        JsonReader(FileReader("")).isLenient = true
//        JsonToken.END_DOCUMENT
        if (jsonText != null) {
            gson.fromJson(jsonText, api)
        } else {
            null
        }
    } catch (jse: JsonSyntaxException) {
        println(jse.message)
        jse.printStackTrace()
        null
    }
}

//--------------------------------------------------------------------------------------------------


//---------------------------CATEGORIES-------------------------------------------------------------

interface API

//--------------------------------------------------------------------------------------------------

data class ZxArtAuthor(
    val limit: Any? = null,
    val responseData: ResponseData? = null,
    val responseStatus: String? = null,
    val start: Int? = null,
    val totalAmount: Int? = null,
) : API {
    data class ResponseData(
        val author: List<Author?>? = null,
    ) {
        data class Author(
            val aliases: List<Int?>? = null,
            val city: String? = null,
            val country: String? = null,
            val dateCreated: Int? = null,
            val dateModified: Int? = null,
            val id: Int? = null,
            val importIds: ImportIds? = null,
            val picturesQuantity: String? = null,
            val realName: String? = null,
            val title: String? = null,
            val tunesQuantity: String? = null,
            val url: String? = null,
        ) {
            data class ImportIds(
                val `3a`: String? = null,
                val pouet: String? = null,
                val sc: String? = null,
                val swiki: String? = null,
                val zxt: String? = null,
            )
        }
    }
}

//--------------------------------------------------------------------------------------------------
//  https://zxart.ee/api/export:zxPicture/limit:1000/filter:authorId=2333
data class ZxArtPicture(
    val limit: Int? = null,
    val responseData: ResponseData? = null,
    val responseStatus: String? = null,
    val start: Int? = null,
    val totalAmount: Int? = null,
) : API {
    data class ResponseData(
        val zxPicture: List<ZxPicture?>? = null,
    ) {
        data class ZxPicture(
            val authorIds: List<Int?>? = null,
            val compo: String? = null,
            val dateCreated: Int? = null,
            val dateModified: Int? = null,
            val description: String? = null,
            val id: Int? = null,
            val imageUrl: String? = null,
            val originalUrl: String? = null,
            val partyId: String? = null,
            val partyPlace: String? = null,
            val rating: String? = null,
            val tags: List<String?>? = null,
            val title: String? = null,
            val type: String? = null,
            val url: String? = null,
            val views: String? = null,
            val year: String? = null,
        )
    }
}

//--------------------------------------------------------------------------------------------------
data class ZxArtAuthorWork(
    val responseData: ResponseData? = null,
    val responseStatus: String? = null,
) : API {
    data class ResponseData(
        val article: List<Any?>? = null,
        val author: List<Author?>? = null,
        val authorAlias: List<Any?>? = null,
        val event: List<Any?>? = null,
        val group: List<Any?>? = null,
        val groupAlias: List<Any?>? = null,
        val news: List<Any?>? = null,
        val party: List<Any?>? = null,
        val production: List<Any?>? = null,
        val searchTotal: Int? = null,
        val zxMusic: List<ZxMusic?>? = null,
        val zxPicture: List<Any?>? = null,
        val zxProd: List<ZxProd?>? = null,
    ) {
        data class Author(
            val id: Int? = null,
            val searchTitle: String? = null,
            val structureType: String? = null,
            val url: String? = null,
        )

        data class ZxMusic(
            val id: Int? = null,
            val structureType: String? = null,
            val title: String? = null,
            val url: String? = null,
        )

        data class ZxProd(
            val id: Int? = null,
            val searchTitle: String? = null,
            val structureType: String? = null,
            val url: String? = null,
        )
    }
}

//--------------------------------------------------------------------------------------------------
data class ZxArtMusic(
    val limit: Int,
    val responseData: ResponseData,
    val responseStatus: String,
    val start: Int,
    val totalAmount: Int,
) : API {
    data class ResponseData(
        val zxMusic: ArrayList<ZxMusic>,
    ) {
        data class ZxMusic(
            val authorIds: List<Int>,
            val compo: String,
            val dateCreated: Int,
            val dateModified: Int,
            val description: String?,
            val id: Int,
            val internalTitle: String?,
            val mp3FilePath: String?,
            val originalFileName: String?,
            val originalUrl: String?,
            val partyId: String,
            val partyPlace: String,
            val plays: String,
            val rating: String,
            val tags: List<String>,
            val time: String?,
            val title: String?,
            val type: String,
            val url: String,
            val year: String,
        )
    }
}