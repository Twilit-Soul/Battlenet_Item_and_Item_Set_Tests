package com.turlington.battlenetTests

import com.google.gson.Gson
import com.turlington.battlenetTests.beans.WoWItem
import com.turlington.battlenetTests.beans.WoWItemSet
import org.apache.logging.log4j.LogManager
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * Way of accessing the api conveniently.
 * Created by Mitchell on 4/14/2016.
 */
class APIAdapter {
    private val gson = Gson() //This thing is just...nifty, isn't it?
    private val logger = LogManager.getLogger(APIAdapter::class.java)

    /*
        I'm a fan of type safety.
    */
    enum class APILanguage constructor(internal val code: String) {
        ENGLISH("en_US"),
        PORTUGUESE("pt_BR"),
        SPANISH("es_MX")
    }

    /**
     * (If you want to specify the language.)
     */
    fun getWoWItemSet(setId: Int, language: APILanguage = APILanguage.ENGLISH): WoWItemSet? {
        try {
            return gson.fromJson(getWoWItemSetJson(setId, language), WoWItemSet::class.java)
        } catch (e: Exception) {
            logger.warn("Couldn't get WoWItemSet for id $setId: ${e.message}")
        }
        return null
    }

    fun getWoWItem(itemId: Int, language: APILanguage = APILanguage.ENGLISH): WoWItem? {
        try {
            return gson.fromJson(getWoWItemJson(itemId, language), WoWItem::class.java)
        } catch (e: Exception) {
            logger.warn("Couldn't get WoWItem for id $itemId: ${e.message}")
        }
        return null
    }

    //TODO: this should take an enum or something, not a class
    private fun getItemOrSet(id: Int, language: APILanguage = APILanguage.ENGLISH, clazz: Class<*>): String? {
        val key = id.toString() + language.code
        when (clazz) {
            WoWItem::class.java -> {
                cachedItemJsons.putIfAbsent(key, callURL(getWoWItemURL(id, language)))
                return cachedItemJsons[key]!!
            }
            WoWItemSet::class.java -> {
                cachedItemSetJsons.putIfAbsent(key, callURL(getWoWItemSetURL(id, language)))
                return cachedItemSetJsons[key]!!
            }
            else -> return null
        }
    }

    fun getWoWItemJson(id: Int, language: APILanguage = APILanguage.ENGLISH): String {
        return getItemOrSet(id, language, WoWItem::class.java)!!
    }

    fun getWoWItemSetJson(id: Int, language: APILanguage = APILanguage.ENGLISH): String {
        return getItemOrSet(id, language, WoWItemSet::class.java)!!
    }

    /**
     * Uses id to try and retrieve an item set from the battle.net API. Can specify language, and add jsonp.
     */
    internal fun getWoWItemSetJsonp(setId: Int, jsonp: String): String {
        return callURL(getWoWItemSetURL(setId, jsonp = jsonp))
    }

    /**
     * Uses id to try and retrieve an item from the battle.net API. Can specify language, and add jsonp.
     */
    internal fun getWoWItemJsonp(itemId: Int, jsonp: String): String {
        return callURL(getWoWItemURL(itemId, jsonp = jsonp))
    }

    /**
     * Sends the passed in request, and returns whatever the response is, hopefully.
     */
    internal fun callURL(myURL: String): String {
        val sb = StringBuilder()
        val urlConn = URL(myURL).openConnection()
        InputStreamReader(urlConn.getInputStream(),
                StandardCharsets.UTF_8).use { inStream ->
            BufferedReader(inStream).use { bufferedReader ->
                urlConn.readTimeout = 60 * 1000
                if (urlConn.getInputStream() != null) {
                    var cp = bufferedReader.read()
                    while (cp != -1) {
                        sb.append(cp.toChar())
                        cp = bufferedReader.read()
                    }
                }
            }
        }

        return sb.toString()
    }

    /**
     * Get url to hit the API with for a WoW item. Can include jsonp.
     */
    fun getWoWItemURL(itemId: Int, language: APILanguage = APILanguage.ENGLISH, key: String = API_KEY, jsonp: String = ""): String {
        return "$ITEM_API_START$itemId$LOCALE_START${language.code}${if (jsonp.isEmpty()) ""
        else JSONP_START + jsonp}$API_KEY_START$key"
    }

    /**
     * Get url to hit the API with for a WoW item set. Can include jsonp.
     */
    fun getWoWItemSetURL(setId: Int, language: APILanguage = APILanguage.ENGLISH, key: String = API_KEY, jsonp: String = ""): String {
        return "$ITEM_SET_API_START$setId$LOCALE_START${language.code}${if (jsonp.isEmpty()) ""
        else JSONP_START + jsonp}$API_KEY_START$key"
    }

    companion object {
        internal val API_KEY = "2gv8ep8at8cnk4ngcbrpzqydk96s8b5k"
        internal val ITEM_SET_API_START = "https://us.api.battle.net/wow/item/set/"
        internal val ITEM_API_START = "https://us.api.battle.net/wow/item/"
        internal val LOCALE_START = "?locale="
        internal val API_KEY_START = "&apikey="
        private val JSONP_START = "&jsonp="

        private val cachedItemJsons = HashMap<String, String>()
        private val cachedItemSetJsons = HashMap<String, String>()
    }
}