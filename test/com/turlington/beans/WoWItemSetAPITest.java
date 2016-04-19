package com.turlington.beans;

import com.turlington.APIAdapter;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the WoW Item Set API.
 * Created by Mitchell on 4/14/2016.
 */
public class WoWItemSetAPITest {

    private final APIAdapter adapter = new APIAdapter();
    private final int DEFAULT_ITEM_SET_ID = 1060;

    /**
     * The default ID at time of writing for https://dev.battle.net/io-docs is 1060.
     * We can make sure the first value users often see works as expected.
     */
    @Test
    public void testDefaultItemSet() {
        Optional<WoWItemSet> itemSetOptional = adapter.getWoWItemSet(DEFAULT_ITEM_SET_ID);
        assertTrue(itemSetOptional.isPresent());
        WoWItemSet itemSet = itemSetOptional.get();
        assertEquals(DEFAULT_ITEM_SET_ID, itemSet.getId());
        assertEquals("Deep Earth Vestments", itemSet.getName());
        assertEquals("Reduces the mana cost of Healing Touch and Rejuvenation by 5%.",
                itemSet.getSetBonuses().get(0).getDescription());
        assertEquals(2, itemSet.getSetBonuses().get(0).getThreshold());
        assertEquals("Your Rejuvenation and Regrowth spells have a 10% chance " +
                        "to Timeslip and have double the normal duration.",
                itemSet.getSetBonuses().get(1).getDescription());
        assertEquals(4, itemSet.getSetBonuses().get(1).getThreshold());
        int[] expected = {76749, 76750, 76751, 76752, 76753};
        assertEquals(expected.length, itemSet.getItems().size());
        for (int item : expected) {
            assertTrue(itemSet.getItems().contains(item));
        }
    }

    /**
     * Something good to check is probably that the items in the default set
     * are real and correct items.
     */
    @Test
    public void testItemsInDefaultSetReal() {
        Optional<WoWItemSet> itemSetOptional = adapter.getWoWItemSet(DEFAULT_ITEM_SET_ID);
        //Already do this part in another test, but still seems wise to have it here, no?
        assertTrue(itemSetOptional.isPresent());
        WoWItemSet itemSet = itemSetOptional.get();
        List<Integer> itemIds = itemSet.getItems();
        assertEquals(5, itemIds.size());
        List<WoWItem> items = itemIds.stream().map(id -> adapter.getWoWItem(id).orElse(new WoWItem())).
                collect(Collectors.toList());

        for (WoWItem item : items) {
            assertEquals(DEFAULT_ITEM_SET_ID, item.getItemSet().getId());
        }

        assertEquals("Deep Earth Handwraps", items.get(0).getName());
        assertEquals("Deep Earth Helm", items.get(1).getName());
        assertEquals("Deep Earth Legwraps", items.get(2).getName());
        assertEquals("Deep Earth Robes", items.get(3).getName());
        assertEquals("Deep Earth Mantle", items.get(4).getName());
    }

    /**
     * One of the three languages on https://dev.battle.net/io-docs.
     */
    @Test
    public void testPortugueseDefaultItemSet() {
        Optional<WoWItemSet> itemSetOptional =
                adapter.getWoWItemSet(DEFAULT_ITEM_SET_ID, APIAdapter.APILanguage.PORTUGUESE);
        //Already do this part in another test, but still seems wise to have it here, no?
        assertTrue(itemSetOptional.isPresent());
        WoWItemSet itemSet = itemSetOptional.get();
        assertEquals(DEFAULT_ITEM_SET_ID, itemSet.getId());
        assertEquals("Vestimenta da Terra Profunda", itemSet.getName());
        assertEquals("Reduz em 5% o custo de mana de Toque de Cura e Rejuvenescer.",
                itemSet.getSetBonuses().get(0).getDescription());
        assertEquals("Seus feitiços Rejuvenescer e Recrescimento têm 10% de chance de dar um Salto Temporal " +
                        "e ter o dobro da duração.",
                itemSet.getSetBonuses().get(1).getDescription());
    }

    /**
     * One of the three languages on https://dev.battle.net/io-docs.
     */
    @Test
    public void testSpanishDefaultItemSet() {
        Optional<WoWItemSet> itemSetOptional =
                adapter.getWoWItemSet(DEFAULT_ITEM_SET_ID, APIAdapter.APILanguage.SPANISH);
        //Already do this part in another test, but still seems wise to have it here, no?
        assertTrue(itemSetOptional.isPresent());
        WoWItemSet itemSet = itemSetOptional.get();
        assertEquals(DEFAULT_ITEM_SET_ID, itemSet.getId());
        assertEquals("Vestimentas del interior de la tierra", itemSet.getName());
        assertEquals("Reduce el costo de maná de Toque de sanación y Rejuvenecimiento un 5%.",
                itemSet.getSetBonuses().get(0).getDescription());
        assertEquals("Tus hechizos Rejuvenecimiento y Recrecimiento tienen un 10% de probabilidad de realizar un " +
                        "Salto temporal y durar el doble de lo normal.",
                itemSet.getSetBonuses().get(1).getDescription());
    }

    @Test
    public void badItemSetId() {
        Optional<WoWItemSet> itemSet = adapter.getWoWItemSet(0);
        assertFalse(itemSet.isPresent());
    }
}
