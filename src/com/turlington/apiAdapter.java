package com.turlington;

import com.turlington.WoWItem.WoWItemSet;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Way of accessing the api conveniently.
 * Created by Mitchell on 4/14/2016.
 */
class APIAdapter {
    private final Gson gson = new Gson(); //This thing is just...nifty, isn't it?
    private final Logger logger = LogManager.getLogger(APIAdapter.class);
    private final String API_KEY = "2gv8ep8at8cnk4ngcbrpzqydk96s8b5k";
    private final String ITEM_SET_API_START = "https://us.api.battle.net/wow/item/set/";
    private final String ITEM_API_START = "https://us.api.battle.net/wow/item/";
    private final String LOCALE_START = "?locale=";
    private final String API_KEY_START = "&apikey=";
    private final String JSONP_START = "&jsonp=";

    /*
        I'm a fan of type safety.
    */
    enum APILanguage {
        ENGLISH("en_US"),
        PORTUGUESE("pt_BR"),
        SPANISH("es_MX");

        private String code;

        APILanguage(String code) {
            this.code = code;
        }

        String getCode() {
            return code;
        }
    }

    /*
        The optionals might be unnecessary. In fact, the exception caused by failure might be more
        desirable to see. This seems like a place I'd find my workplace's standard and adhere to it.

        "But which do you think is better, Mitch?"

        I think the exception could be useful indeed, but at least this way a test can continue on
        in case of multiple back-to-back failures, and it could report exactly which parts of a
        loop fails instead of dying on the first failure. And if the exception is really important,
        it gets logged anyway so we can still see it.

        On the other hand, Optionals can definitely complicate the fetching code in a rather annoying
        fashion. I personally like them because I've been burned a lot at my current workplace by
        surprise nulls. I see my choices here as: throw exception, return null, return empty optional.

        For this instance, just gonna return the optional. It's an exercise for trying things anyway,
        right?
     */

    //TODO: Hashmap or something to avoid repeat lookups.

    /**
     * (If you want to specify the language.)
     */
    Optional<WoWItemSet> getWoWItemSet(int setId, APILanguage language) {
        try {
            return Optional.of(gson.fromJson(getWoWItemSetJson(setId, language), WoWItemSet.class));
        } catch (Exception e) {
            logger.error("Couldn't get WoWItemSet for id "+setId+": "+e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * Uses id to try and retrieve a set from the battle.net API.
     */
    Optional<WoWItemSet> getWoWItemSet(int setId) {
        return getWoWItemSet(setId, APILanguage.ENGLISH);
    }

    Optional<WoWItem> getWoWItem(int itemId, APILanguage language) {
        try {
            return Optional.of(gson.fromJson(getWoWItemJson(itemId, language), WoWItem.class));
        } catch (Exception e) {
            logger.error("Couldn't get WoWItem for id "+itemId+": "+e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * Uses id to try and retrieve an item from the battle.net API.
     */
    Optional<WoWItem> getWoWItem(int itemId) {
        return getWoWItem(itemId, APILanguage.ENGLISH);
    }

    String getWoWItemSetJson(int setId, APILanguage language) throws IOException {
        return callURL(ITEM_SET_API_START + setId + LOCALE_START + language.getCode() + API_KEY_START + API_KEY);
    }

    String getWoWItemJson(int itemId, APILanguage language) throws IOException {
        return callURL(ITEM_API_START + itemId + LOCALE_START + language.getCode() + API_KEY_START + API_KEY);
    }

    String getWoWItemSetJsonp(int setId, APILanguage language, String jsonp) throws IOException {
        return callURL(ITEM_SET_API_START + setId + LOCALE_START + language.getCode() + JSONP_START + jsonp + API_KEY_START + API_KEY);
    }

    String getWoWItemJsonp(int itemId, APILanguage language, String jsonp) throws IOException {
        return callURL(ITEM_API_START + itemId + LOCALE_START + language.getCode() + JSONP_START + jsonp + API_KEY_START + API_KEY);
    }

    /**
     * Sends the passed in request, and returns whatever the response is, hopefully.
     */
    String callURL(String myURL) throws IOException {
        //TODO: use a third party library and make this simpler, perhaps?
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = new URL(myURL).openConnection();
        try (InputStreamReader in = new InputStreamReader(urlConn.getInputStream(),
                Charset.defaultCharset());
             BufferedReader bufferedReader = new BufferedReader(in)) {
            urlConn.setReadTimeout(60 * 1000);
            if (urlConn.getInputStream() != null) {
                int cp;
                while ((cp = bufferedReader.read()) != -1) {
                    sb.append((char) cp);
                }
            }
        }

        return sb.toString();
    }
}
