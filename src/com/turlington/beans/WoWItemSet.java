package com.turlington.beans;

import java.util.List;

/**
 * Representation of an item set in WoW.
 * Created by Valerie on 4/17/2016.
 */
@SuppressWarnings("unused")
public class WoWItemSet {
    private int id;
    private String name;
    private List<SetBonuses> setBonuses;
    private List<Integer> items;

    int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SetBonuses> getSetBonuses() {
        return setBonuses;
    }

    List<Integer> getItems() {
        return items;
    }

    public class SetBonuses {
        private String description;
        private int threshold;

        public String getDescription() {
            return description;
        }

        int getThreshold() {
            return threshold;
        }
    }
}
