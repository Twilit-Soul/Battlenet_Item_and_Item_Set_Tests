package com.turlington.beans;

import java.util.List;

/**
 * Representation of an item in WoW.
 * Created by Mitchell on 4/14/2016.
 */
@SuppressWarnings("unused")
public class WoWItem {
    private int id, disenchantingSkillRank, stackable, itemBind, buyPrice, itemClass, itemSubClass, containerSlots,
            inventoryType, itemLevel, maxCount, maxDurability, minFactionId, minReputation, quality, sellPrice,
            requiredSkill, requiredLevel, requiredSkillRank, baseArmor, armor, displayInfoId;
    private String description, name, icon, nameDescription, nameDescriptionColor, context;
    private List<BonusStats> bonusStats;
    private List<ItemSpells> itemSpells;
    private List<Integer> bonusLists, allowableClasses;
    private WeaponInfo weaponInfo;
    private boolean equippable, hasSockets, isAuctionable, upgradable, heroicTooltip;
    private ItemSource itemSource;
    private List<String> availableContexts;
    private BonusSummary bonusSummary;
    private WoWItemSet itemSet;

    /*
        I suppressed unused for the entire class because values are assigned automatically by gson.
        I suppressed WeakerAccess for inner classes because we DO need package-local to test them,
        but IntelliJ never sees us instantiating them anywhere.

        This also explains the incomplete code coverage report.
        All lines are actually tested at the time of this writing.

        ~~

        Not everyone is a fan of so many inner classes, but I felt it might be appropriate given that they are all
        always a part of WoWItem. If these tests were to expand to the rest of the API, and found they could be
        retrieved with their own API call, I would probably pull them out into the package level.
     */

    WoWItemSet getItemSet() {
        return itemSet;
    }

    /**
     * This is to initialize a dummy item.
     */
    WoWItem() {
        this.name = "";
    }

    int getId() {
        return id;
    }

    int getDisenchantingSkillRank() {
        return disenchantingSkillRank;
    }

    int getStackable() {
        return stackable;
    }

    int getItemBind() {
        return itemBind;
    }

    int getBuyPrice() {
        return buyPrice;
    }

    int getItemClass() {
        return itemClass;
    }

    int getItemSubClass() {
        return itemSubClass;
    }

    int getContainerSlots() {
        return containerSlots;
    }

    int getInventoryType() {
        return inventoryType;
    }

    int getItemLevel() {
        return itemLevel;
    }

    int getMaxCount() {
        return maxCount;
    }

    int getMaxDurability() {
        return maxDurability;
    }

    int getMinFactionId() {
        return minFactionId;
    }

    int getMinReputation() {
        return minReputation;
    }

    int getQuality() {
        return quality;
    }

    int getSellPrice() {
        return sellPrice;
    }

    int getRequiredSkill() {
        return requiredSkill;
    }

    int getRequiredLevel() {
        return requiredLevel;
    }

    int getRequiredSkillRank() {
        return requiredSkillRank;
    }

    int getBaseArmor() {
        return baseArmor;
    }

    int getArmor() {
        return armor;
    }

    int getDisplayInfoId() {
        return displayInfoId;
    }

    String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    String getIcon() {
        return icon;
    }

    String getNameDescription() {
        return nameDescription;
    }

    String getNameDescriptionColor() {
        return nameDescriptionColor;
    }

    String getContext() {
        return context;
    }

    List<BonusStats> getBonusStats() {
        return bonusStats;
    }

    public List<ItemSpells> getItemSpells() {
        return itemSpells;
    }

    List<Integer> getBonusLists() {
        return bonusLists;
    }

    WeaponInfo getWeaponInfo() {
        return weaponInfo;
    }

    boolean isEquippable() {
        return equippable;
    }

    boolean isHasSockets() {
        return hasSockets;
    }

    boolean isAuctionable() {
        return isAuctionable;
    }

    boolean isUpgradable() {
        return upgradable;
    }

    boolean isHeroicTooltip() {
        return heroicTooltip;
    }

    ItemSource getItemSource() {
        return itemSource;
    }

    List<String> getAvailableContexts() {
        return availableContexts;
    }

    public BonusSummary getBonusSummary() {
        return bonusSummary;
    }

    List<Integer> getAllowableClasses() {
        return allowableClasses;
    }

    @SuppressWarnings("WeakerAccess")
    class BonusStats {

        private BonusStats() {}

        private int stat, amount;

        int getStat() {
            return stat;
        }

        int getAmount() {
            return amount;
        }
    }


    @SuppressWarnings("WeakerAccess")
    class WeaponInfo {
        private Damage damage;
        private double weaponSpeed, dps;

        Damage getDamage() {
            return damage;
        }

        double getWeaponSpeed() {
            return weaponSpeed;
        }

        double getDps() {
            return dps;
        }

        class Damage {
            private int min, max;
            private double exactMin, exactMax;

            int getMin() {
                return min;
            }

            int getMax() {
                return max;
            }

            double getExactMin() {
                return exactMin;
            }

            double getExactMax() {
                return exactMax;
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    class ItemSource {
        private int sourceId;
        private String sourceType;

        int getSourceId() {
            return sourceId;
        }

        String getSourceType() {
            return sourceType;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public class BonusSummary {
        private List<Integer> defaultBonusLists, chanceBonusLists;
        private List<BonusChances> bonusChances;

        List<Integer> getDefaultBonusLists() {
            return defaultBonusLists;
        }

        List<Integer> getChanceBonusLists() {
            return chanceBonusLists;
        }

        public List<BonusChances> getBonusChances() {
            return bonusChances;
        }

        public class BonusChances {
            private String chanceType;
            private Upgrade upgrade;
            private List<Stats> stats;
            private List<Sockets> sockets;

            String getChanceType() {
                return chanceType;
            }

            public Upgrade getUpgrade() {
                return upgrade;
            }

            List<Stats> getStats() {
                return stats;
            }

            List<Sockets> getSockets() {
                return sockets;
            }

            public class Upgrade {
                private String upgradeType, name;
                private int id;

                String getUpgradeType() {
                    return upgradeType;
                }

                public String getName() {
                    return name;
                }

                int getId() {
                    return id;
                }
            }

            class Stats {
                private String statId; //Why is this one not an int???
                private int delta, maxDelta;

                String getStatId() {
                    return statId;
                }

                int getDelta() {
                    return delta;
                }

                int getMaxDelta() {
                    return maxDelta;
                }
            }

            class Sockets {
                private String socketType;

                String getSocketType() {
                    return socketType;
                }
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public class ItemSpells {
        private int spellId, nCharges, categoryId;
        private boolean consumable;
        private String trigger;
        private Spell spell;

        int getSpellId() {
            return spellId;
        }

        int getnCharges() {
            return nCharges;
        }

        int getCategoryId() {
            return categoryId;
        }

        boolean isConsumable() {
            return consumable;
        }

        String getTrigger() {
            return trigger;
        }

        public Spell getSpell() {
            return spell;
        }

        public class Spell {
            private int id;
            private String name, icon, description, castTime;

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getIcon() {
                return icon;
            }

            public String getDescription() {
                return description;
            }

            public String getCastTime() {
                return castTime;
            }
        }
    }

}
