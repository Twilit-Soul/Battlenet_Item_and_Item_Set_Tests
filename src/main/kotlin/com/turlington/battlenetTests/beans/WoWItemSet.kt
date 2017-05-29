package com.turlington.battlenetTests.beans

data class WoWItemSet(var id: Int, var name: String,
                      var setBonuses: List<SetBonuses>, var items: List<Int>)

data class SetBonuses(var description: String, var threshold: Int)