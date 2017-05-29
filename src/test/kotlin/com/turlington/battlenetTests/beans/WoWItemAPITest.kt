package com.turlington.battlenetTests.beans

import com.turlington.battlenetTests.APIAdapter
import com.turlington.battlenetTests.APIAdapter.APILanguage
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector



/**
 * Tests for the WoWItem API.
 * Created by Mitchell on 4/14/2016.
 */
class WoWItemAPITest {

    @get:Rule
    val collector = ErrorCollector()
    private val adapter = APIAdapter()

    /**
     * The default ID at time of writing for https://dev.battle.net/io-docs is 18803.
     * We can make sure the first value users often see works as expected.
     */
    @Test
    fun testDefaultItem() {
        val DEFAULT_ITEM_ID = 18803
        val item = adapter.getWoWItem(DEFAULT_ITEM_ID)!!
        /*
            After this massive list of "assertThis" and "assertThat" my sense of
            "there has got to be a better way than this" is ringing heavily. Hopefully
            I find it before submitting.
        */
        collector.assertEquals(DEFAULT_ITEM_ID, item.id)
        collector.assertEquals(225, item.disenchantingSkillRank)
        collector.assertEquals("Property of Finkle Einhorn, Grandmaster Adventurer", item.description)
        collector.assertEquals("Finkle's Lava Dredger", item.name)
        collector.assertEquals("inv_gizmo_02", item.icon)
        collector.assertEquals(1, item.stackable)
        collector.assertEquals(1, item.itemBind)
        collector.assertEquals(4, item.bonusStats.size)
        collector.assertEquals(40, item.bonusStats[0].stat)
        collector.assertEquals(16, item.bonusStats[0].amount)
        collector.assertEquals(51, item.bonusStats[1].stat)
        collector.assertEquals(11, item.bonusStats[1].amount)
        collector.assertEquals(5, item.bonusStats[2].stat)
        collector.assertEquals(18, item.bonusStats[2].amount)
        collector.assertEquals(7, item.bonusStats[3].stat)
        collector.assertEquals(19, item.bonusStats[3].amount)
        collector.assertEquals(0, item.itemSpells.size)
        collector.assertEquals(474384, item.buyPrice)
        collector.assertEquals(2, item.itemClass)
        collector.assertEquals(5, item.itemSubClass)
        collector.assertEquals(0, item.containerSlots)
        collector.assertEquals(81, item.weaponInfo.damage.min)
        collector.assertEquals(122, item.weaponInfo.damage.max)
        val ACCEPTABLE_FUZZ = 0.01
        collector.assertEquals(81.0, item.weaponInfo.damage.exactMin, ACCEPTABLE_FUZZ)
        collector.assertEquals(122.0, item.weaponInfo.damage.exactMax, ACCEPTABLE_FUZZ)
        collector.assertEquals(2.9, item.weaponInfo.weaponSpeed, ACCEPTABLE_FUZZ)
        collector.assertEquals(35.0, item.weaponInfo.dps, ACCEPTABLE_FUZZ)
        collector.assertEquals(17, item.inventoryType)
        collector.assertTrue(item.isEquippable)
        collector.assertEquals(70, item.itemLevel)
        collector.assertEquals(0, item.maxCount) //??? You're allowed 0 at most of these? Finkle does not share.
        collector.assertEquals(120, item.maxDurability)
        collector.assertEquals(0, item.minFactionId)
        collector.assertEquals(0, item.minReputation)
        collector.assertEquals(4, item.quality)
        collector.assertEquals(94876, item.sellPrice)
        collector.assertEquals(0, item.requiredSkill)
        collector.assertEquals(60, item.requiredLevel)
        collector.assertEquals(0, item.requiredSkillRank)
        collector.assertEquals(179703, item.itemSource.sourceId)
        collector.assertEquals("GAME_OBJECT_DROP", item.itemSource.sourceType) //Object? Poor NPCs.
        collector.assertEquals(0, item.baseArmor)
        collector.assertFalse(item.isHasSockets)
        collector.assertFalse(item.isAuctionable)
        collector.assertEquals(0, item.armor)
        collector.assertEquals(31265, item.displayInfoId)
        collector.assertEquals("", item.nameDescription)
        collector.assertEquals("000000", item.nameDescriptionColor)
        collector.assertTrue(item.isUpgradable)
        collector.assertFalse(item.isHeroicTooltip)
        collector.assertEquals("", item.context)
        collector.assertEquals(0, item.bonusLists.size)
        collector.assertEquals(1, item.availableContexts.size)
        collector.assertTrue(item.availableContexts.contains(""))
        collector.assertEquals(0, item.bonusSummary.defaultBonusLists.size)
        collector.assertEquals(0, item.bonusSummary.chanceBonusLists.size)
        collector.assertEquals(0, item.bonusSummary.bonusChances.size)
    }

    /**
     * Our default item doesn't have this, so let's check one that does.
     */
    @Test
    fun testItemSpells() {
        /*
            Why Vishanka (Raid Finder)? Because I remember the glee when I got this bow.
            Also, the WoWHead image for it was submitted by me.
        */
        val VISHANKA_RAID_FINDER_ID = 78480
        val item = adapter.getWoWItem(VISHANKA_RAID_FINDER_ID)!!

        collector.assertEquals(VISHANKA_RAID_FINDER_ID, item.id) //Just make sure we got the right one back.
        collector.assertEquals(1, item.itemSpells.size)
        collector.assertEquals(109857, item.itemSpells[0].spellId)
        collector.assertEquals(109857, item.itemSpells[0].spell.id)
        collector.assertEquals("Item - Dragon Soul - Proc - Agi Ranged Gun LFR", item.itemSpells[0].spell.name)
        collector.assertEquals("spell_holy_avenginewrath", item.itemSpells[0].spell.icon)
        //TODO: could replace numbers with regex match
        collector.assertEquals("Your ranged attacks have a chance to deal ^8.5370 fire damage over 2 sec.", item.itemSpells[0].spell.description)
        collector.assertEquals("Passive", item.itemSpells[0].spell.castTime)
        collector.assertEquals(0, item.itemSpells[0].getnCharges())
        collector.assertFalse(item.itemSpells[0].isConsumable)
        collector.assertEquals(0, item.itemSpells[0].categoryId)
        collector.assertEquals("ON_EQUIP", item.itemSpells[0].trigger)
    }

    @Test
    fun testAllowableClasses() {
        //Why Vishanka (Raid Finder)? Because I already know it has class requirements.
        val VISHANKA_RAID_FINDER_ID = 78480
        val item = adapter.getWoWItem(VISHANKA_RAID_FINDER_ID)!!

        collector.assertEquals(VISHANKA_RAID_FINDER_ID, item.id)
        collector.assertEquals(1, item.allowableClasses.size)
        collector.assertEquals(3, item.allowableClasses[0])
    }

    @Test
    fun testAvailableContexts() {
        val IRON_SKULLCRUSHER_ID = 124373
        val item = adapter.getWoWItem(IRON_SKULLCRUSHER_ID)!!

        collector.assertEquals(IRON_SKULLCRUSHER_ID, item.id)
        collector.assertEquals(3, item.availableContexts.size)
        collector.assertEquals("raid-normal", item.availableContexts[0])
        collector.assertEquals("raid-heroic", item.availableContexts[1])
        collector.assertEquals("raid-mythic", item.availableContexts[2])
    }

    @Test
    fun testBonuses() {
        //This item seems to have a lot of the bonus fields filled in.
        val DARK_KNIGHT_BAND_ID = 119007 //Sounds like a band name! Ba dum tsh.
        val item = adapter.getWoWItem(DARK_KNIGHT_BAND_ID)!!

        collector.assertEquals(DARK_KNIGHT_BAND_ID, item.id)
        collector.assertEquals(1, item.bonusLists.size)
        collector.assertEquals(609, item.bonusLists[0])
        collector.assertEquals(1, item.bonusSummary.defaultBonusLists.size)
        collector.assertEquals(609, item.bonusSummary.defaultBonusLists[0])
        collector.assertEquals(130, item.bonusSummary.chanceBonusLists.size)
        collector.assertEquals(39, item.bonusSummary.chanceBonusLists[0]) //Beginning
        collector.assertEquals(198, item.bonusSummary.chanceBonusLists[106]) //Middle-Ish
        collector.assertEquals(42, item.bonusSummary.chanceBonusLists[129]) //End
        collector.assertEquals(9, item.bonusSummary.bonusChances.size)
        collector.assertEquals("UPGRADE", item.bonusSummary.bonusChances[0].chanceType)
        collector.assertEquals("NAME_SUFFIX", item.bonusSummary.bonusChances[0].upgrade.upgradeType)
        collector.assertEquals("of the Fireflash", item.bonusSummary.bonusChances[0].upgrade.name)
        collector.assertEquals(13150, item.bonusSummary.bonusChances[0].upgrade.id)
        collector.assertEquals(2, item.bonusSummary.bonusChances[0].stats.size)
        collector.assertEquals("32", item.bonusSummary.bonusChances[0].stats[0].statId)
        collector.assertEquals(76, item.bonusSummary.bonusChances[0].stats[0].delta)
        collector.assertEquals(114, item.bonusSummary.bonusChances[0].stats[0].maxDelta)
        collector.assertEquals("36", item.bonusSummary.bonusChances[0].stats[1].statId)
        collector.assertEquals(76, item.bonusSummary.bonusChances[0].stats[1].delta)
        collector.assertEquals(114, item.bonusSummary.bonusChances[0].stats[1].maxDelta)
        collector.assertEquals(0, item.bonusSummary.bonusChances[0].sockets.size)
        collector.assertEquals("SOCKET", item.bonusSummary.bonusChances[8].chanceType)
        collector.assertEquals(0, item.bonusSummary.bonusChances[8].stats.size)
        collector.assertEquals(1, item.bonusSummary.bonusChances[8].sockets.size)
        collector.assertEquals("PRISMATIC", item.bonusSummary.bonusChances[8].sockets[0].socketType)
    }

    @Test
    fun testItemSet() {
        val DEEP_EARTH_HANDWRAPS_ID = 76749
        val item = adapter.getWoWItem(DEEP_EARTH_HANDWRAPS_ID)!!

        collector.assertEquals(DEEP_EARTH_HANDWRAPS_ID, item.id)
        val DEEP_EARTH_VESTMENTS_ID = 1060
        collector.assertEquals(DEEP_EARTH_VESTMENTS_ID, item.itemSet.id)
        collector.assertEquals("Deep Earth Vestments", item.itemSet.name)
        collector.assertEquals("Reduces the mana cost of Healing Touch and Rejuvenation by 5%.",
                item.itemSet.setBonuses[0].description)
        collector.assertEquals(2, item.itemSet.setBonuses[0].threshold)
        collector.assertEquals("Your Rejuvenation and Regrowth spells have a 10% chance " + "to Timeslip and have double the normal duration.",
                item.itemSet.setBonuses[1].description)
        collector.assertEquals(4, item.itemSet.setBonuses[1].threshold)
        val expected = intArrayOf(76749, 76750, 76751, 76752, 76753)
        collector.assertEquals(expected.size, item.itemSet.items.size)
        for (itemInSet in expected) {
            collector.assertTrue(item.itemSet.items.contains(itemInSet))
        }

        val itemSetOptional = adapter.getWoWItemSet(item.itemSet.id)!!
        collector.assertEquals(DEEP_EARTH_VESTMENTS_ID, itemSetOptional.id)
    }

    /**
     * One of the three languages on https://dev.battle.net/io-docs.
     */
    @Test
    fun testPortugueseDefaultItem() {
        val DEFAULT_ITEM_ID = 18803
        val item = adapter.getWoWItem(DEFAULT_ITEM_ID, APILanguage.PORTUGUESE)!!

        collector.assertEquals(DEFAULT_ITEM_ID, item.id)
        collector.assertEquals("Propriedade de Jonas Tiragosto, Grão-mestre Aventureiro", item.description)
        collector.assertEquals("Draga de Lava do Jonas", item.name)
    }

    /**
     * One of the three languages on https://dev.battle.net/io-docs.
     */
    @Test
    fun testSpanishDefaultItem() {
        val DEFAULT_ITEM_ID = 18803
        val item = adapter.getWoWItem(DEFAULT_ITEM_ID, APILanguage.SPANISH)!!

        collector.assertEquals(DEFAULT_ITEM_ID, item.id)
        collector.assertEquals("Propiedad de Finkle Einhorn, gran maestro aventurero.", item.description)
        collector.assertEquals("Draga de lava de Finkle", item.name)
    }

    /**
     * Here a potential disadvantage of my Optional approach is revealed.
     * We can't test for specific exceptions here.
     */
    @Test
    fun badItemID() {
        val item = adapter.getWoWItem(0)
        collector.assertEquals(null, item)
    }
}
