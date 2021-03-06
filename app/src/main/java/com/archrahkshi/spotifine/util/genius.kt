package com.archrahkshi.spotifine.util

import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import timber.log.Timber
import java.io.IOException

suspend fun getOriginalLyrics(
    title: String,
    artists: String
) = withContext(Dispatchers.IO) {
    val songInfo = JsonParser().parse(
        "$GENIUS_API_BASE_URL/search?q=$artists $title".replace(" ", "%20")
            .buildRequest(GENIUS_ACCESS_TOKEN)
    ).asJsonObject["response"].asJsonObject["hits"].asJsonArray.find {
        it.asJsonObject["type"].asString == "song"
    }
    if (songInfo != null) {
        val lyrics = songInfo.asJsonObject["result"].asJsonObject["path"].asString.getLyrics()
        if (lyrics == "[Instrumental]")
            null
        else
            lyrics
    } else {
        Timber.wtf("no song info")
        null
    }
}

fun String.getLyrics() = try { // Forbidden dark magic
    val parsed = Jsoup.parse("$GENIUS_BASE_URL$this".buildRequest(GENIUS_ACCESS_TOKEN))
    parsed?.run {
        val lyricsClass = selectFirst("div.lyrics")?.selectFirst("p")
        if (lyricsClass == null) {
            Timber.wtf("ROOT")
            selectFirst("div[class*=Lyrics__Root]")
                ?.html()
                ?.replace("<br>", "")
                ?.split('\n')
                ?.joinToString("\n") { it.trim() }
                ?.cleanTags()
        } else lyricsClass.html()
            .split("<br>")
            .joinToString("\n") { it.trim() }
            .cleanTags()
    }
} catch (e: IOException) {
    Timber.wtf(e)
    null
}

fun String.cleanTags(): String {
    var str = this
    while (str.indexOf("<") != -1) {
        str = str.replace(
            str.substring(str.indexOf("<"), str.indexOf(">") + 1),
            ""
        )
    }
    return str
}
