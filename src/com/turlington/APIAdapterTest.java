package com.turlington;

import com.google.gson.Gson;
import org.junit.Test;

import com.turlington.APIAdapter.APILanguage;
import com.turlington.WoWItem.ItemSpells;
import com.turlington.WoWItem.WoWItemSet;
import com.turlington.WoWItem.WoWItemSet.SetBonuses;
import com.turlington.WoWItem.BonusSummary.BonusChances;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Should probably test our connection to the API.
 * Created by Mitchell on 4/15/2016.
 */
public class APIAdapterTest {

    private final int DEFAULT_ITEM_ID = 18803, DEFAULT_ITEM_SET_ID = 1060;

    @Test(expected=FileNotFoundException.class)
    public void testCallURLBad() throws IOException {
        new APIAdapter().callURL("https://us.api.battle.net/wow/item/0?locale=en_US&apikey=2gv8ep8at8cnk4ngcbrpzqydk96s8b5k");
    }

    @Test
    public void testCallURL() throws IOException {
        String results = new APIAdapter().callURL("https://us.api.battle.net/wow/item/76749?locale=en_US&apikey=2gv8ep8at8cnk4ngcbrpzqydk96s8b5k");
        assertFalse(results.isEmpty());
    }

    @Test(expected=FileNotFoundException.class)
    public void testGetItemJsonNoExist() throws IOException {
        new APIAdapter().getWoWItemJson(0, APILanguage.ENGLISH);
    }

    @Test(expected=FileNotFoundException.class)
    public void testGetItemSetJsonNoExist() throws IOException {
        new APIAdapter().getWoWItemSetJson(0, APILanguage.ENGLISH);
    }

    @Test
    public void testGetItemJsonEnglish() throws IOException {
        final String EXPECTED_JSON = "{\"id\":18803,\"disenchantingSkillRank\":225,\"description\":\"Property of " +
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
                "chanceBonusLists\":[],\"bonusChances\":[]}}";

        assertEquals(new APIAdapter().getWoWItemJson(DEFAULT_ITEM_ID, APILanguage.ENGLISH), EXPECTED_JSON);
    }

    @Test
    public void testGetItemSetJsonEnglish() throws IOException {
        final String EXPECTED_JSON = "{\"id\":1060,\"name\":\"Deep Earth Vestments\",\"setBonuses\":" +
                "[{\"description\":\"Reduces the mana cost of Healing Touch and Rejuvenation by 5%." +
                "\",\"threshold\":2},{\"description\":\"Your Rejuvenation and Regrowth spells have a " +
                "10% chance to Timeslip and have double the normal duration.\",\"threshold\":4}]," +
                "\"items\":[76749,76750,76751,76752,76753]}";

        assertEquals(new APIAdapter().getWoWItemSetJson(DEFAULT_ITEM_SET_ID, APILanguage.ENGLISH), EXPECTED_JSON);
    }

    @Test
    public void testGetItemJsonPortuguese() throws IOException {
        final String EXPECTED_JSON = "{\"id\":18803,\"disenchantingSkillRank\":225,\"description\":" +
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
                "chanceBonusLists\":[],\"bonusChances\":[]}}";

        assertEquals(new APIAdapter().getWoWItemJson(DEFAULT_ITEM_ID, APILanguage.PORTUGUESE), EXPECTED_JSON);
    }

    @Test
    public void testGetItemSetJsonPortuguese() throws IOException {
        final String EXPECTED_JSON = "{\"id\":1060,\"name\":\"Vestimenta da Terra Profunda\",\"setBonuses\":[{\"" +
                "description\":\"Reduz em 5% o custo de mana de Toque de Cura e Rejuvenescer.\",\"threshold\":2},{\"" +
                "description\":\"Seus feitiços Rejuvenescer e Recrescimento têm 10% de chance de dar um " +
                "Salto Temporal e ter o dobro da duração.\",\"threshold\":4}],\"" +
                "items\":[76749,76750,76751,76752,76753]}";

        assertEquals(new APIAdapter().getWoWItemSetJson(DEFAULT_ITEM_SET_ID, APILanguage.PORTUGUESE), EXPECTED_JSON);
    }

    @Test
    public void testGetItemJsonSpanish() throws IOException {
        final String EXPECTED_JSON = "{\"id\":18803,\"disenchantingSkillRank\":225,\"description\":\"" +
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
                "defaultBonusLists\":[],\"chanceBonusLists\":[],\"bonusChances\":[]}}";

        assertEquals(new APIAdapter().getWoWItemJson(DEFAULT_ITEM_ID, APILanguage.SPANISH), EXPECTED_JSON);
    }

    @Test
    public void testGetItemSetJsonSpanish() throws IOException {
        final String EXPECTED_JSON = "{\"id\":1060,\"name\":\"Vestimentas del interior de la tierra\",\"" +
                "setBonuses\":[{\"description\":\"Reduce el costo de maná de Toque de sanación y Rejuvenecimiento " +
                "un 5%.\",\"threshold\":2},{\"description\":\"Tus hechizos Rejuvenecimiento y Recrecimiento tienen " +
                "un 10% de probabilidad de realizar un Salto temporal y durar el doble de lo normal.\",\"" +
                "threshold\":4}],\"items\":[76749,76750,76751,76752,76753]}";

        assertEquals(new APIAdapter().getWoWItemSetJson(DEFAULT_ITEM_SET_ID, APILanguage.SPANISH), EXPECTED_JSON);
    }

    /**
     * This is to make sure that the only thing changing between items on different languages is the localized
     * text.
     */
    @Test
    public void testLanguageDifferences() throws IOException {
        APIAdapter adapter = new APIAdapter();
        Gson gson = new Gson();
        JsonLanguageIndependenceTester tester = new JsonLanguageIndependenceTester();
        final int ITEM_SPELLS_ITEM_ID = 124638, ITEM_BONUS_SUMMARY_ITEM_ID = 119007;

        tester.testItemLanguageIndependence(ITEM_SPELLS_ITEM_ID, adapter, gson);
        tester.testItemLanguageIndependence(ITEM_BONUS_SUMMARY_ITEM_ID, adapter, gson);
        tester.testItemSetLanguageIndependence(DEFAULT_ITEM_SET_ID, adapter, gson);
    }

    @Test
    public void testJsonp() throws IOException {
        final String TEST_WORD = "testWord";
        String original = new APIAdapter().getWoWItemJson(DEFAULT_ITEM_ID, APILanguage.ENGLISH);
        String withJsonp = new APIAdapter().getWoWItemJsonp(DEFAULT_ITEM_ID, APILanguage.ENGLISH, TEST_WORD);
        assertEquals(TEST_WORD+"();", withJsonp.replace(original, ""));
    }

    /**
     * Exists to keep all of these methods isolated together. Not every test here would want them, but neither would
     * another class.
     *
     * (Don't know how you guys feel about inner classes such as this)
     */
    private class JsonLanguageIndependenceTester {
        private void testItemSetLanguageIndependence(int itemSetId, APIAdapter adapter, Gson gson) throws IOException {
            List<String> jsonStrings = new ArrayList<>(APILanguage.values().length);
            for (APILanguage language: APILanguage.values()) {
                jsonStrings.add(adapter.getWoWItemSetJson(itemSetId, language));
            }

            List<WoWItemSet> wowItems = jsonStrings.stream().map(s -> gson.fromJson(s, WoWItemSet.class))
                    .collect(Collectors.toList());

            List<String> languageFreeStrings = new ArrayList<>(jsonStrings.size());
            for (int i = 0; i < jsonStrings.size(); i++) {
                languageFreeStrings.add(removeLanguageFromItemSetJson(jsonStrings.get(i), wowItems.get(i)));
            }

            for (int i = 1; i < languageFreeStrings.size(); i++) {
                assertEquals(languageFreeStrings.get(0), languageFreeStrings.get(i));
            }
        }

        /*
            Code between these two methods seems kinda duplicated...but there are key lines where
            different methods are called. Not sure of an easy way to generalize that.
         */

        private void testItemLanguageIndependence(int itemId, APIAdapter adapter, Gson gson) throws IOException {
            List<String> jsonStrings = new ArrayList<>(APILanguage.values().length);
            for (APILanguage language: APILanguage.values()) {
                jsonStrings.add(adapter.getWoWItemJson(itemId, language));
            }

            List<WoWItem> wowItems = jsonStrings.stream().map(s -> gson.fromJson(s, WoWItem.class))
                    .collect(Collectors.toList());

            List<String> languageFreeStrings = new ArrayList<>(jsonStrings.size());
            for (int i = 0; i < jsonStrings.size(); i++) {
                languageFreeStrings.add(removeLanguageFromItemJson(jsonStrings.get(i), wowItems.get(i)));
            }

            for (int i = 1; i < languageFreeStrings.size(); i++) {
                assertEquals(languageFreeStrings.get(0), languageFreeStrings.get(i));
            }
        }

        /**
         * Removes any language-specific text from a json string for a WoW item.
         */
        private String removeLanguageFromItemJson(String json, WoWItem item) {
            json = removeWords(json, item.getName());
            for (ItemSpells itemSpells : item.getItemSpells()) {
                WoWItem.ItemSpells.Spell spell = itemSpells.getSpell();
                json = removeWords(json, spell.getName(), spell.getCastTime(), spell.getDescription());
            }
            for (BonusChances bonusChances : item.getBonusSummary().getBonusChances()) {
                if (bonusChances.getUpgrade() != null) {
                    json = removeWords(json, bonusChances.getUpgrade().getName());
                }
            }
            return json;
        }

        private String removeLanguageFromItemSetJson(String json, WoWItemSet itemSet) {
            json = removeWords(json, itemSet.getName());
            for (SetBonuses setBonuses : itemSet.getSetBonuses()) {
                json = removeWords(json, setBonuses.getDescription());
            }
            return json;
        }

        /**
         * Just a convenient way to remove lots of text.
         */
        private String removeWords(String original, String... toRemove) {
            for (String string : toRemove) {
                original = original.replaceFirst(Pattern.quote(string.replace("\n", "\\n")), "");
            }
            return original;
        }
    }
}
