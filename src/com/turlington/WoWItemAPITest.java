package com.turlington;

import org.junit.Test;
import com.turlington.APIAdapter.APILanguage;
import com.turlington.WoWItem.WoWItemSet;

import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the WoWItem API.
 * Created by Mitchell on 4/14/2016.
 */
public class WoWItemAPITest {

    private final APIAdapter adapter = new APIAdapter();

    /**
     * The default ID at time of writing for https://dev.battle.net/io-docs is 18803.
     * We can make sure the first value users often see works as expected.
     */
    @Test
    public void testDefaultItem() {
        final int DEFAULT_ITEM_ID = 18803;
        WoWItem item = adapter.getWoWItem(DEFAULT_ITEM_ID).orElse(new WoWItem());
        /*
            After this massive list of "assertThis" and "assertThat" my sense of
            "there has got to be a better way than this" is ringing heavily. Hopefully
            I find it before submitting.
        */
        assertEquals(DEFAULT_ITEM_ID, item.getId());
        assertEquals(225, item.getDisenchantingSkillRank());
        assertEquals("Property of Finkle Einhorn, Grandmaster Adventurer", item.getDescription());
        assertEquals("Finkle's Lava Dredger", item.getName());
        assertEquals("inv_gizmo_02", item.getIcon());
        assertEquals(1, item.getStackable());
        assertEquals(1, item.getItemBind());
        assertEquals(4, item.getBonusStats().size());
        assertEquals(51, item.getBonusStats().get(0).getStat());
        assertEquals(11, item.getBonusStats().get(0).getAmount());
        assertEquals(5, item.getBonusStats().get(1).getStat());
        assertEquals(18, item.getBonusStats().get(1).getAmount());
        assertEquals(6, item.getBonusStats().get(2).getStat());
        assertEquals(16, item.getBonusStats().get(2).getAmount());
        assertEquals(7, item.getBonusStats().get(3).getStat());
        assertEquals(19, item.getBonusStats().get(3).getAmount());
        assertEquals(0, item.getItemSpells().size());
        assertEquals(474384, item.getBuyPrice());
        assertEquals(2, item.getItemClass());
        assertEquals(5, item.getItemSubClass());
        assertEquals(0, item.getContainerSlots());
        assertEquals(81, item.getWeaponInfo().getDamage().getMin());
        assertEquals(122, item.getWeaponInfo().getDamage().getMax());
        final double ACCEPTABLE_FUZZ = 0.01;
        assertEquals(81.0, item.getWeaponInfo().getDamage().getExactMin(), ACCEPTABLE_FUZZ);
        assertEquals(122.0, item.getWeaponInfo().getDamage().getExactMax(), ACCEPTABLE_FUZZ);
        assertEquals(2.9, item.getWeaponInfo().getWeaponSpeed(), ACCEPTABLE_FUZZ);
        assertEquals(35.0, item.getWeaponInfo().getDps(), ACCEPTABLE_FUZZ);
        assertEquals(17, item.getInventoryType());
        assertTrue(item.isEquippable());
        assertEquals(70, item.getItemLevel());
        assertEquals(0, item.getMaxCount()); //??? You're allowed 0 at most of these? Finkle does not share.
        assertEquals(120, item.getMaxDurability());
        assertEquals(0, item.getMinFactionId());
        assertEquals(0, item.getMinReputation());
        assertEquals(4, item.getQuality());
        assertEquals(94876, item.getSellPrice());
        assertEquals(0, item.getRequiredSkill());
        assertEquals(60, item.getRequiredLevel());
        assertEquals(0, item.getRequiredSkillRank());
        assertEquals(179703, item.getItemSource().getSourceId());
        assertEquals("GAME_OBJECT_DROP", item.getItemSource().getSourceType()); //Object? Poor NPCs.
        assertEquals(0, item.getBaseArmor());
        assertFalse(item.isHasSockets());
        assertFalse(item.isAuctionable());
        assertEquals(0, item.getArmor());
        assertEquals(31265, item.getDisplayInfoId());
        assertEquals("", item.getNameDescription());
        assertEquals("000000", item.getNameDescriptionColor());
        assertTrue(item.isUpgradable());
        assertFalse(item.isHeroicTooltip());
        assertEquals("", item.getContext());
        assertEquals(0, item.getBonusLists().size());
        assertEquals(1, item.getAvailableContexts().size());
        assertTrue(item.getAvailableContexts().contains(""));
        assertEquals(0, item.getBonusSummary().getDefaultBonusLists().size());
        assertEquals(0, item.getBonusSummary().getChanceBonusLists().size());
        assertEquals(0, item.getBonusSummary().getBonusChances().size());
    }

    /**
     * Our default item doesn't have this, so let's check one that does.
     */
    @Test
    public void testItemSpells() {
        /*
            Why Vishanka (Raid Finder)? Because I remember the glee when I got this bow.
            Also, the WoWHead image for it was submitted by me.
        */
        final int VISHANKA_RAID_FINDER_ID = 78480;
        WoWItem item = adapter.getWoWItem(VISHANKA_RAID_FINDER_ID).orElse(new WoWItem());

        assertEquals(VISHANKA_RAID_FINDER_ID, item.getId()); //Just make sure we got the right one back.
        assertEquals(1, item.getItemSpells().size());
        assertEquals(109857, item.getItemSpells().get(0).getSpellId());
        assertEquals(109857, item.getItemSpells().get(0).getSpell().getId());
        assertEquals("Item - Dragon Soul - Proc - Agi Ranged Gun LFR", item.getItemSpells().get(0).getSpell().getName());
        assertEquals("spell_holy_avenginewrath", item.getItemSpells().get(0).getSpell().getIcon());
        assertEquals("Your ranged attacks have a chance to deal ^8.2790 fire damage over 2 sec.", item.getItemSpells().get(0).getSpell().getDescription());
        assertEquals("Passive", item.getItemSpells().get(0).getSpell().getCastTime());
        assertEquals(0, item.getItemSpells().get(0).getnCharges());
        assertFalse(item.getItemSpells().get(0).isConsumable());
        assertEquals(0, item.getItemSpells().get(0).getCategoryId());
        assertEquals("ON_EQUIP", item.getItemSpells().get(0).getTrigger());
    }

    @Test
    public void testAllowableClasses() {
        //Why Vishanka (Raid Finder)? Because I already know it has class requirements.
        final int VISHANKA_RAID_FINDER_ID = 78480;
        WoWItem item = adapter.getWoWItem(VISHANKA_RAID_FINDER_ID).orElse(new WoWItem());

        assertEquals(VISHANKA_RAID_FINDER_ID, item.getId());
        assertEquals(1, item.getAllowableClasses().size());
        assertEquals(new Integer(3), item.getAllowableClasses().get(0));
    }

    @Test
    public void testAvailableContexts() {
        final int IRON_SKULLCRUSHER_ID = 124373;
        WoWItem item = adapter.getWoWItem(IRON_SKULLCRUSHER_ID).orElse(new WoWItem());

        assertEquals(IRON_SKULLCRUSHER_ID, item.getId());
        assertEquals(3, item.getAvailableContexts().size());
        assertEquals("raid-normal", item.getAvailableContexts().get(0));
        assertEquals("raid-heroic", item.getAvailableContexts().get(1));
        assertEquals("raid-mythic", item.getAvailableContexts().get(2));
    }

    @Test
    public void testBonuses() {
        //This item seems to have a lot of the bonus fields filled in.
        final int DARK_KNIGHT_BAND_ID = 119007; //Sounds like a band name! Ba dum tsh.
        WoWItem item = adapter.getWoWItem(DARK_KNIGHT_BAND_ID).orElse(new WoWItem());

        assertEquals(DARK_KNIGHT_BAND_ID, item.getId());
        assertEquals(1, item.getBonusLists().size());
        assertEquals(new Integer(609), item.getBonusLists().get(0));
        assertEquals(1, item.getBonusSummary().getDefaultBonusLists().size());
        assertEquals(new Integer(609), item.getBonusSummary().getDefaultBonusLists().get(0));
        assertEquals(214, item.getBonusSummary().getChanceBonusLists().size());
        assertEquals(new Integer(39), item.getBonusSummary().getChanceBonusLists().get(0)); //Beginning
        assertEquals(new Integer(131), item.getBonusSummary().getChanceBonusLists().get(106)); //Middle-Ish
        assertEquals(new Integer(42), item.getBonusSummary().getChanceBonusLists().get(213)); //End
        assertEquals(13, item.getBonusSummary().getBonusChances().size());
        assertEquals("UPGRADE", item.getBonusSummary().getBonusChances().get(0).getChanceType());
        assertEquals("NAME_SUFFIX", item.getBonusSummary().getBonusChances().get(0).getUpgrade().getUpgradeType());
        assertEquals("of the Fireflash", item.getBonusSummary().getBonusChances().get(0).getUpgrade().getName());
        assertEquals(13150, item.getBonusSummary().getBonusChances().get(0).getUpgrade().getId());
        assertEquals(2, item.getBonusSummary().getBonusChances().get(0).getStats().size());
        assertEquals("32", item.getBonusSummary().getBonusChances().get(0).getStats().get(0).getStatId());
        assertEquals(76, item.getBonusSummary().getBonusChances().get(0).getStats().get(0).getDelta());
        assertEquals(114, item.getBonusSummary().getBonusChances().get(0).getStats().get(0).getMaxDelta());
        assertEquals("36", item.getBonusSummary().getBonusChances().get(0).getStats().get(1).getStatId());
        assertEquals(76, item.getBonusSummary().getBonusChances().get(0).getStats().get(1).getDelta());
        assertEquals(114, item.getBonusSummary().getBonusChances().get(0).getStats().get(1).getMaxDelta());
        assertEquals(0, item.getBonusSummary().getBonusChances().get(0).getSockets().size());
        assertEquals("SOCKET", item.getBonusSummary().getBonusChances().get(12).getChanceType());
        assertEquals(0, item.getBonusSummary().getBonusChances().get(12).getStats().size());
        assertEquals(1, item.getBonusSummary().getBonusChances().get(12).getSockets().size());
        assertEquals("PRISMATIC", item.getBonusSummary().getBonusChances().get(12).getSockets().get(0).getSocketType());
    }

    @Test
    public void testItemSet() {
        final int DEEP_EARTH_HANDWRAPS_ID = 76749;
        WoWItem item = adapter.getWoWItem(DEEP_EARTH_HANDWRAPS_ID).orElse(new WoWItem());

        assertEquals(DEEP_EARTH_HANDWRAPS_ID, item.getId());
        final int DEEP_EARTH_VESTMENTS_ID = 1060;
        assertEquals(DEEP_EARTH_VESTMENTS_ID, item.getItemSet().getId());
        assertEquals("Deep Earth Vestments", item.getItemSet().getName());
        assertEquals("Reduces the mana cost of Healing Touch and Rejuvenation by 5%.",
                item.getItemSet().getSetBonuses().get(0).getDescription());
        assertEquals(2, item.getItemSet().getSetBonuses().get(0).getThreshold());
        assertEquals("Your Rejuvenation and Regrowth spells have a 10% chance " +
                        "to Timeslip and have double the normal duration.",
                item.getItemSet().getSetBonuses().get(1).getDescription());
        assertEquals(4, item.getItemSet().getSetBonuses().get(1).getThreshold());
        int[] expected = {76749, 76750, 76751, 76752, 76753};
        assertEquals(expected.length, item.getItemSet().getItems().size());
        for (int itemInSet : expected) {
            assertTrue(item.getItemSet().getItems().contains(itemInSet));
        }

        Optional<WoWItemSet> itemSetOptional = adapter.getWoWItemSet(item.getItemSet().getId());
        assertTrue(itemSetOptional.isPresent());
        assertEquals(DEEP_EARTH_VESTMENTS_ID, itemSetOptional.get().getId());
    }

    /**
     * One of the three languages on https://dev.battle.net/io-docs.
     */
    @Test
    public void testPortugueseDefaultItem() {
        final int DEFAULT_ITEM_ID = 18803;
        WoWItem item = adapter.getWoWItem(DEFAULT_ITEM_ID, APILanguage.PORTUGUESE).orElse(new WoWItem());

        assertEquals(DEFAULT_ITEM_ID, item.getId());
        assertEquals("Propriedade de Jonas Tiragosto, Grão-mestre Aventureiro", item.getDescription());
        assertEquals("Draga de Lava do Jonas", item.getName());
    }

    /**
     * One of the three languages on https://dev.battle.net/io-docs.
     */
    @Test
    public void testSpanishDefaultItem() {
        final int DEFAULT_ITEM_ID = 18803;
        WoWItem item = adapter.getWoWItem(DEFAULT_ITEM_ID, APILanguage.SPANISH).orElse(new WoWItem());

        assertEquals(DEFAULT_ITEM_ID, item.getId());
        assertEquals("Propiedad de Finkle Einhorn, gran maestro aventurero.", item.getDescription());
        assertEquals("Draga de lava de Finkle", item.getName());
    }

    /**
     * Here a potential disadvantage of my Optional approach is revealed.
     * We can't test for specific exceptions here.
     */
    @Test
    public void badItemID() {
        Optional<WoWItem> item = adapter.getWoWItem(0);
        assertFalse(item.isPresent());
    }

}
