package com.github.ringoame196_s_mcPlugin

import org.bukkit.block.Container
import org.bukkit.inventory.ItemStack

data class GachaponData(
    val block: Container,
    var consumableItem: ItemStack?
)
