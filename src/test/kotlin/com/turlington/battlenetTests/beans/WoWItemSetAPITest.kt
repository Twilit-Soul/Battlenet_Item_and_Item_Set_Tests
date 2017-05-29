package com.turlington.battlenetTests.beans

import com.turlington.battlenetTests.APIAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector
import kotlin.streams.toList

/**
 * Tests for the WoW Item Set API.
 * Created by Mitchell on 4/14/2016.
 */
class WoWItemSetAPITest {

    @get:Rule
    val collector = ErrorCollector()
    private val adapter = APIAdapter()
    private val DEFAULT_ITEM_SET_ID = 1060

    /**
     * The default ID at time of writing for https://dev.battle.net/io-docs is 1060.
     * We can make sure the first value users often see works as expected.
     */
    @Test
    fun testDefaultItemSet() {
        val itemSet = adapter.getWoWItemSet(DEFAULT_ITEM_SET_ID)!!
        collector.assertEquals(DEFAULT_ITEM_SET_ID, itemSet.id)
        collector.assertEquals("Deep Earth Vestments", itemSet.name)
        collector.assertEquals("Reduces the mana cost of Healing Touch and Rejuvenation by 5%.",
                itemSet.setBonuses[0].description)
        collector.assertEquals(2, itemSet.setBonuses[0].threshold)
        collector.assertEquals("Your Rejuvenation and Regrowth spells have a 10% chance " + "to Timeslip and have double the normal duration.",
                itemSet.setBonuses[1].description)
        collector.assertEquals(4, itemSet.setBonuses[1].threshold)
        val expected = intArrayOf(76749, 76750, 76751, 76752, 76753)
        collector.assertEquals(expected.size, itemSet.items.size)
        for (item in expected) {
            collector.assertTrue(itemSet.items.contains(item))
        }
    }

    /**
     * Something good to check is probably that the items in the default set
     * are real and correct items.
     */
    @Test
    fun testItemsInDefaultSetReal() {
        val itemSet = adapter.getWoWItemSet(DEFAULT_ITEM_SET_ID)!!
        //Already do this part in another test, but still seems wise to have it here, no?
        val itemIds = itemSet.items
        collector.assertEquals(5, itemIds.size)
        val items = itemIds.stream().map({ id -> adapter.getWoWItem(id)!! }).toList()

        for (item in items) {
            collector.assertEquals(DEFAULT_ITEM_SET_ID, item.itemSet.id)
        }

        collector.assertEquals("Deep Earth Handwraps", items[0].name)
        collector.assertEquals("Deep Earth Helm", items[1].name)
        collector.assertEquals("Deep Earth Legwraps", items[2].name)
        collector.assertEquals("Deep Earth Robes", items[3].name)
        collector.assertEquals("Deep Earth Mantle", items[4].name)
    }

    /**
     * One of the three languages on https://dev.battle.net/io-docs.
     */
    @Test
    fun testPortugueseDefaultItemSet() {
        val itemSet = adapter.getWoWItemSet(DEFAULT_ITEM_SET_ID, APIAdapter.APILanguage.PORTUGUESE)!!
        collector.assertEquals(DEFAULT_ITEM_SET_ID, itemSet.id)
        collector.assertEquals("Vestimenta da Terra Profunda", itemSet.name)
        collector.assertEquals("Reduz em 5% o custo de mana de Toque de Cura e Rejuvenescer.",
                itemSet.setBonuses[0].description)
        collector.assertEquals("Seus feitiços Rejuvenescer e Recrescimento têm 10% de chance de dar um Salto Temporal " + "e ter o dobro da duração.",
                itemSet.setBonuses[1].description)
    }

    /**
     * One of the three languages on https://dev.battle.net/io-docs.
     */
    @Test
    fun testSpanishDefaultItemSet() {
        val itemSet = adapter.getWoWItemSet(DEFAULT_ITEM_SET_ID, APIAdapter.APILanguage.SPANISH)!!
        //Already do this part in another test, but still seems wise to have it here, no?
        collector.assertEquals(DEFAULT_ITEM_SET_ID, itemSet.id)
        collector.assertEquals("Vestimentas del interior de la tierra", itemSet.name)
        collector.assertEquals("Reduce el costo de maná de Toque de sanación y Rejuvenecimiento un 5%.",
                itemSet.setBonuses[0].description)
        collector.assertEquals("Tus hechizos Rejuvenecimiento y Recrecimiento tienen un 10% de probabilidad de realizar un " + "Salto temporal y durar el doble de lo normal.",
                itemSet.setBonuses[1].description)
    }

    @Test
    fun badItemSetId() {
        val itemSet = adapter.getWoWItemSet(0)
        collector.assertEquals(null, itemSet)
    }
}
