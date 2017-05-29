package com.turlington.battlenetTests

import com.google.gson.Gson
import com.turlington.battlenetTests.APIAdapter.APILanguage
import com.turlington.battlenetTests.APIAdapter.Companion.API_KEY
import com.turlington.battlenetTests.APIAdapter.Companion.API_KEY_START
import com.turlington.battlenetTests.APIAdapter.Companion.ITEM_API_START
import com.turlington.battlenetTests.APIAdapter.Companion.ITEM_SET_API_START
import com.turlington.battlenetTests.APIAdapter.Companion.LOCALE_START
import com.turlington.battlenetTests.beans.WoWItem
import com.turlington.battlenetTests.beans.WoWItemSet
import com.turlington.battlenetTests.beans.assertEquals
import junit.framework.TestCase.assertFalse
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import java.util.regex.Pattern
import kotlin.streams.toList

/**
 * Should probably test our connection to the API.
 * Created by Mitchell on 4/15/2016.
 */
class APIAdapterTest {

    @get:Rule
    private val collector = ErrorCollector()
    private val DEFAULT_ITEM_ID = 18803
    private val DEFAULT_ITEM_SET_ID = 1060
    private val adapter = APIAdapter()
    private val gson = Gson()
    private val tester = JsonLanguageIndependenceTester()

    /*
        I don't know if you will frown at me or smile at me for insisting on using my language enum and APIAdapter
        constants.

        On the one hand, if the api ever changes, we only change one place now. On the other, I actually find it a
        little harder to read.
     */

    @Test(expected = FileNotFoundException::class)
    fun testCallURLBadId() {
        adapter.callURL(adapter.getWoWItemURL(0))
    }

    @Test
    fun testCallURLSuccess() {
        val results = adapter.callURL(adapter.getWoWItemURL(DEFAULT_ITEM_ID))
        assertFalse(results.isEmpty())
    }

    @Test(expected = FileNotFoundException::class)
    fun testGetItemJsonNoExist() {
        adapter.getWoWItemJson(0)
    }

    @Test(expected = FileNotFoundException::class)
    fun testGetItemSetJsonNoExist() {
        adapter.getWoWItemSetJson(0)
    }

    @Test
    fun testGetItemJsonEnglish() {
        val EXPECTED_JSON = "{\"id\":18803,\"disenchantingSkillRank\":225,\"description\":\"Property of " +
                "Finkle Einhorn, Grandmaster Adventurer\",\"name\":\"Finkle's Lava Dredger\",\"icon\":\"" +
                "inv_gizmo_02\",\"stackable\":1,\"itemBind\":1,\"bonusStats\":[{\"stat\":51,\"amount\":11},{\"" +
                "stat\":5,\"amount\":18},{\"stat\":6,\"amount\":16},{\"stat\":7,\"amount\":19}],\"itemSpells\"" +
                ":[],\"buyPrice\":474384,\"itemClass\":2,\"itemSubClass\":5,\"containerSlots\":0,\"weaponInfo\":{\"" +
                "damage\":{\"min\":81,\"max\":122,\"exactMin\":81.0,\"exactMax\":122.0},\"weaponSpeed\":2.9,\"" +
                "dps\":35.0},\"inventoryType\":17,\"equippable\":true,\"itemLevel\":70,\"maxCount\":0,\"" +
                "maxDurability\":120,\"minFactionId\":0,\"minReputation\":0,\"quality\":4,\"sellPrice\":94876,\"" +
                "requiredSkill\":0,\"requiredLevel\":60,\"requiredSkillRank\":0,\"itemSource\":{\"" +
                "sourceId\":179703,\"sourceType\":\"GAME_OBJECT_DROP\"},\"baseArmor\":0,\"hasSockets\":false,\"" +
                "isAuctionable\":false,\"armor\":0,\"displayInfoId\":31265,\"nameDescription\":\"\",\"" +
                "nameDescriptionColor\":\"000000\",\"upgradable\":true,\"heroicTooltip\":false,\"context\":\"\",\"" +
                "bonusLists\":[],\"availableContexts\":[\"\"],\"bonusSummary\":{\"defaultBonusLists\":[],\"" +
                "chanceBonusLists\":[],\"bonusChances\":[]}}"

        collector.assertEquals(adapter.getWoWItemJson(DEFAULT_ITEM_ID), EXPECTED_JSON)
    }

    @Test
    fun testGetItemSetJsonEnglish() {
        val EXPECTED_JSON = "{\"id\":1060,\"name\":\"Deep Earth Vestments\",\"setBonuses\":" +
                "[{\"description\":\"Reduces the mana cost of Healing Touch and Rejuvenation by 5%." +
                "\",\"threshold\":2},{\"description\":\"Your Rejuvenation and Regrowth spells have a " +
                "10% chance to Timeslip and have double the normal duration.\",\"threshold\":4}]," +
                "\"items\":[76749,76750,76751,76752,76753]}"

        collector.assertEquals(adapter.getWoWItemSetJson(DEFAULT_ITEM_SET_ID), EXPECTED_JSON)
    }

    @Test
    fun testGetItemJsonPortuguese() {
        val EXPECTED_JSON = "{\"id\":18803,\"disenchantingSkillRank\":225,\"description\":" +
                "\"Propriedade de Jonas Tiragosto, Grão-mestre Aventureiro\",\"name\":\"" +
                "Draga de Lava do Jonas\",\"icon\":\"inv_gizmo_02\",\"stackable\":1,\"itemBind\":1,\"" +
                "bonusStats\":[{\"stat\":51,\"amount\":11},{\"stat\":5,\"amount\":18},{\"stat\":6,\"" +
                "amount\":16},{\"stat\":7,\"amount\":19}],\"itemSpells\":[],\"buyPrice\":474384,\"" +
                "itemClass\":2,\"itemSubClass\":5,\"containerSlots\":0,\"weaponInfo\":{\"damage\":{\"" +
                "min\":81,\"max\":122,\"exactMin\":81.0,\"exactMax\":122.0},\"weaponSpeed\":2.9,\"" +
                "dps\":35.0},\"inventoryType\":17,\"equippable\":true,\"itemLevel\":70,\"maxCount\":0,\"" +
                "maxDurability\":120,\"minFactionId\":0,\"minReputation\":0,\"quality\":4,\"sellPrice\":94876,\"" +
                "requiredSkill\":0,\"requiredLevel\":60,\"requiredSkillRank\":0,\"itemSource\":{\"" +
                "sourceId\":179703,\"sourceType\":\"GAME_OBJECT_DROP\"},\"baseArmor\":0,\"hasSockets\":false,\"" +
                "isAuctionable\":false,\"armor\":0,\"displayInfoId\":31265,\"nameDescription\":\"\",\"" +
                "nameDescriptionColor\":\"000000\",\"upgradable\":true,\"heroicTooltip\":false,\"context\":\"\",\"" +
                "bonusLists\":[],\"availableContexts\":[\"\"],\"bonusSummary\":{\"defaultBonusLists\":[],\"" +
                "chanceBonusLists\":[],\"bonusChances\":[]}}"

        collector.assertEquals(EXPECTED_JSON, adapter.getWoWItemJson(DEFAULT_ITEM_ID, APILanguage.PORTUGUESE))
    }

    @Test
    fun testGetItemSetJsonPortuguese() {
        val EXPECTED_JSON = "{\"id\":1060,\"name\":\"Vestimenta da Terra Profunda\",\"setBonuses\":[{\"" +
                "description\":\"Reduz em 5% o custo de mana de Toque de Cura e Rejuvenescer.\",\"threshold\":2},{\"" +
                "description\":\"Seus feitiços Rejuvenescer e Recrescimento têm 10% de chance de dar um " +
                "Salto Temporal e ter o dobro da duração.\",\"threshold\":4}],\"" +
                "items\":[76749,76750,76751,76752,76753]}"

        collector.assertEquals(EXPECTED_JSON, adapter.getWoWItemSetJson(DEFAULT_ITEM_SET_ID, APILanguage.PORTUGUESE))
    }

    @Test
    fun testGetItemJsonSpanish() {
        val EXPECTED_JSON = "{\"id\":18803,\"disenchantingSkillRank\":225,\"description\":\"" +
                "Propiedad de Finkle Einhorn, gran maestro aventurero.\",\"name\":\"Draga de lava de " +
                "Finkle\",\"icon\":\"inv_gizmo_02\",\"stackable\":1,\"itemBind\":1,\"bonusStats\":[{\"" +
                "stat\":51,\"amount\":11},{\"stat\":5,\"amount\":18},{\"stat\":6,\"amount\":16},{\"" +
                "stat\":7,\"amount\":19}],\"itemSpells\":[],\"buyPrice\":474384,\"itemClass\":2,\"" +
                "itemSubClass\":5,\"containerSlots\":0,\"weaponInfo\":{\"damage\":{\"min\":81,\"max\":122,\"" +
                "exactMin\":81.0,\"exactMax\":122.0},\"weaponSpeed\":2.9,\"dps\":35.0},\"inventoryType\":17,\"" +
                "equippable\":true,\"itemLevel\":70,\"maxCount\":0,\"maxDurability\":120,\"minFactionId\":0,\"" +
                "minReputation\":0,\"quality\":4,\"sellPrice\":94876,\"requiredSkill\":0,\"requiredLevel\":60,\"" +
                "requiredSkillRank\":0,\"itemSource\":{\"sourceId\":179703,\"sourceType\":\"GAME_OBJECT_DROP\"},\"" +
                "baseArmor\":0,\"hasSockets\":false,\"isAuctionable\":false,\"armor\":0,\"displayInfoId\":31265,\"" +
                "nameDescription\":\"\",\"nameDescriptionColor\":\"000000\",\"upgradable\":true,\"heroicTooltip\":" +
                "false,\"context\":\"\",\"bonusLists\":[],\"availableContexts\":[\"\"],\"bonusSummary\":{\"" +
                "defaultBonusLists\":[],\"chanceBonusLists\":[],\"bonusChances\":[]}}"

        collector.assertEquals(EXPECTED_JSON, adapter.getWoWItemJson(DEFAULT_ITEM_ID, APILanguage.SPANISH))
    }

    @Test
    fun testGetItemSetJsonSpanish() {
        val EXPECTED_JSON = "{\"id\":1060,\"name\":\"Vestimentas del interior de la tierra\",\"" +
                "setBonuses\":[{\"description\":\"Reduce el costo de maná de Toque de sanación y Rejuvenecimiento " +
                "un 5%.\",\"threshold\":2},{\"description\":\"Tus hechizos Rejuvenecimiento y Recrecimiento tienen " +
                "un 10% de probabilidad de realizar un Salto temporal y durar el doble de lo normal.\",\"" +
                "threshold\":4}],\"items\":[76749,76750,76751,76752,76753]}"

        collector.assertEquals(EXPECTED_JSON, adapter.getWoWItemSetJson(DEFAULT_ITEM_SET_ID, APILanguage.SPANISH))
    }

    /**
     * This is to make sure that the only thing changing between items on different languages is the localized
     * text.
     */
    @Test
    fun testLanguageDifferences() {
        val ITEM_SPELLS_ITEM_ID = 124638
        val ITEM_BONUS_SUMMARY_ITEM_ID = 119007

        tester.testItemLanguageIndependence(ITEM_SPELLS_ITEM_ID, adapter, gson)
        tester.testItemLanguageIndependence(ITEM_BONUS_SUMMARY_ITEM_ID, adapter, gson)
        tester.testItemSetLanguageIndependence(DEFAULT_ITEM_SET_ID, adapter, gson)
    }

    @Test
    fun testBadLanguage() {
        //Bad language input? English.
        val badLanguage = adapter.callURL("$ITEM_API_START$DEFAULT_ITEM_ID${LOCALE_START}no_NOPE$API_KEY_START$API_KEY")
        val correctLanguage = adapter.getWoWItemJson(DEFAULT_ITEM_ID)
        collector.assertEquals(badLanguage, correctLanguage)
    }

    @Test
    @Throws(IOException::class)
    fun testMissingLanguage() {
        //No language input? Also English.
        val badLanguage = adapter.callURL("$ITEM_API_START$DEFAULT_ITEM_ID?$API_KEY_START$API_KEY")
        val correctLanguage = adapter.getWoWItemJson(DEFAULT_ITEM_ID)
        collector.assertEquals(badLanguage, correctLanguage)
    }

    @Test
    fun testItemBadAPIKey() {
        val BAD_KEY = "afkHunter"
        try {
            adapter.callURL(adapter.getWoWItemURL(DEFAULT_ITEM_ID, key = BAD_KEY))
        } catch (e: IOException) {
            val expectedErrorMessage = "Server returned HTTP response code: 403 for URL: $ITEM_API_START" +
                    "$DEFAULT_ITEM_ID$LOCALE_START${APILanguage.ENGLISH.code}$API_KEY_START$BAD_KEY"
            collector.assertEquals(expectedErrorMessage, e.message)
        }

    }

    @Test
    fun testItemSetBadAPIKey() {
        val BAD_KEY = "afkHunter"
        try {
            adapter.callURL(adapter.getWoWItemSetURL(DEFAULT_ITEM_SET_ID, key = BAD_KEY))
        } catch (e: IOException) {
            val expectedErrorMessage = "Server returned HTTP response code: 403 for URL: $ITEM_SET_API_START" +
                    "$DEFAULT_ITEM_SET_ID$LOCALE_START${APILanguage.ENGLISH.code}$API_KEY_START$BAD_KEY"
            collector.assertEquals(expectedErrorMessage, e.message)
        }

    }

    @Test
    fun testItemJsonp() {
        val TEST_WORD = "testWord"
        val original = adapter.getWoWItemJson(DEFAULT_ITEM_ID)
        val withJsonp = adapter.getWoWItemJsonp(DEFAULT_ITEM_ID, TEST_WORD)
        collector.assertEquals(TEST_WORD + "();", withJsonp.replace(original, ""))
    }

    @Test
    fun testItemSetJsonp() {
        val TEST_WORD = "testWord"
        val original = adapter.getWoWItemSetJson(DEFAULT_ITEM_SET_ID)
        val withJsonp = adapter.getWoWItemSetJsonp(DEFAULT_ITEM_SET_ID, TEST_WORD)
        collector.assertEquals(TEST_WORD + "();", withJsonp.replace(original, ""))
    }

    @Test
    fun testBadJsonp() {
        val TEST_WORD = "#"
        val original = adapter.getWoWItemSetJson(DEFAULT_ITEM_SET_ID)
        val withJsonp = adapter.getWoWItemSetJsonp(DEFAULT_ITEM_SET_ID, TEST_WORD)
        collector.assertEquals("({\"code\":403, \"type\":\"Forbidden\", \"detail\":\"Account Inactive\"})", withJsonp.replace(original, ""))
    }

    /**
     * Exists to keep all of these methods isolated together. Not every test here would want them, but neither would
     * another class.
     *
     *
     * (Don't know how you guys feel about inner classes such as this)
     */
    private inner class JsonLanguageIndependenceTester {

        /**
         * Strips out anything language-related, and makes sure what's left between items is identical.
         */
        internal fun testItemSetLanguageIndependence(itemSetId: Int, adapter: APIAdapter, gson: Gson) {
            val jsonStrings = ArrayList<String>(APILanguage.values().size)
            APILanguage.values().mapTo(jsonStrings) { adapter.getWoWItemSetJson(itemSetId, it) }

            val wowItems = jsonStrings.stream().map { s -> gson.fromJson(s, WoWItemSet::class.java) }.toList()

            val languageFreeStrings = ArrayList<String>(jsonStrings.size)
            jsonStrings.indices.mapTo(languageFreeStrings) { removeLanguageFromItemSetJson(jsonStrings[it], wowItems[it]) }

            for (i in 1..languageFreeStrings.size - 1) {
                collector.assertEquals(languageFreeStrings[0], languageFreeStrings[i])
            }
        }

        /*
            Code between these two methods seems kinda duplicated...but there are key lines where
            different methods are called. Not sure of an easy way to generalize that.
         */

        /**
         * Strips out anything language-related, and makes sure what's left between items is identical.
         */
        internal fun testItemLanguageIndependence(itemId: Int, adapter: APIAdapter, gson: Gson) {
            val jsonStrings = ArrayList<String>(APILanguage.values().size)
            APILanguage.values().mapTo(jsonStrings) { adapter.getWoWItemJson(itemId, it) }


            val wowItems = jsonStrings.stream().map { s -> gson.fromJson(s, WoWItem::class.java) }.toList()

            val languageFreeStrings = ArrayList<String>(jsonStrings.size)
            jsonStrings.indices.mapTo(languageFreeStrings) { removeLanguageFromItemJson(jsonStrings[it], wowItems[it]) }

            for (i in 1..languageFreeStrings.size - 1) {
                collector.assertEquals(languageFreeStrings[0], languageFreeStrings[i])
            }
        }

        /**
         * Removes any language-specific text from a json string for a WoW item.
         */
        private fun removeLanguageFromItemJson(json: String, item: WoWItem): String {
            var changed = json
            changed = removeWords(changed, item.name)
            item.itemSpells
                    .asSequence()
                    .map { it.spell }
                    .forEach { changed = removeWords(changed, it.name, it.castTime, it.description) }
            item.bonusSummary.bonusChances
                    .asSequence()
                    .filter { it.upgrade != null }
                    .forEach { changed = removeWords(changed, it.upgrade.name) }
            return changed
        }

        /**
         * Removes any language-specific text from a json string for a WoW item set.
         */
        private fun removeLanguageFromItemSetJson(json: String, itemSet: WoWItemSet): String {
            var changed = json
            changed = removeWords(changed, itemSet.name)
            for (setBonuses in itemSet.setBonuses) {
                changed = removeWords(changed, setBonuses.description)
            }
            return changed
        }

        /**
         * Just a convenient way to remove lots of text.
         */
        private fun removeWords(original: String, vararg toRemove: String): String {
            var changed = original
            for (string in toRemove) {
                changed = changed.replaceFirst(Pattern.quote(string.replace("\n", "\\n")).toRegex(), "")
            }
            return changed
        }
    }
}
