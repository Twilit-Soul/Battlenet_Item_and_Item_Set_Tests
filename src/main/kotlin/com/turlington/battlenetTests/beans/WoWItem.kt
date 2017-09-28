package com.turlington.battlenetTests.beans

/**
 * Representation of an item in WoW.
 * Created by Valerie on 4/14/2016.
 */
data class WoWItem(var id: Int, var disenchantingSkillRank: Int, var stackable: Int, var itemBind: Int,
                   var buyPrice: Int, var itemClass: Int, var itemSubClass: Int, var containerSlots: Int,
                   var inventoryType: Int, var itemLevel: Int, var maxCount: Int, var maxDurability: Int,
                   var minFactionId: Int, var minReputation: Int, var quality: Int, var sellPrice: Int,
                   var requiredSkill: Int, var requiredLevel: Int, var requiredSkillRank: Int, var baseArmor: Int,
                   var armor: Int, var displayInfoId: Int, var description: String, var name: String,
                   var icon: String, var nameDescription: String, var nameDescriptionColor: String, var context: String,
                   var bonusStats: List<BonusStats>, var itemSpells: List<ItemSpells>, var bonusLists: List<Int>,
                   var allowableClasses: List<Int>, var weaponInfo: WeaponInfo, var equippable: Boolean,
                   var isHasSockets: Boolean, var isAuctionable: Boolean, var upgradable: Boolean,
                   var isHeroicTooltip: Boolean, var itemSource: ItemSource, var availableContexts: List<String>,
                   var bonusSummary: BonusSummary, var itemSet: WoWItemSet)

data class BonusStats(var stat: Int, var amount: Int)

data class WeaponInfo(var damage: Damage, var weaponSpeed: Double, var dps: Double)

data class Damage(var min: Int, var max: Int, var exactMin: Double, var exactMax: Double)

data class ItemSource(var sourceId: Int, var sourceType: String)

data class BonusSummary(var defaultBonusLists: List<Int>, var chanceBonusLists: List<Int>,
                        var bonusChances: List<BonusChances?>)

data class ItemSpells(var spellId: Int, var nCharges: Int, var categoryId: Int,
                      var isConsumable: Boolean, var trigger: String, var spell: Spell)

data class Spell(var id: Int, var name: String, var icon: String, var description: String, var castTime: String)

data class BonusChances(var chanceType: String, var upgrade: Upgrade, var stats: List<Stats>, var sockets: List<Sockets>)

data class Upgrade(var upgradeType: String, var name: String, var id: Int)

data class Stats(var statId: String, var delta: Int, var maxDelta: Int)

data class Sockets(var socketType: String)
