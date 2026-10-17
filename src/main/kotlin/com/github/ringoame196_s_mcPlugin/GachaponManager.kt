package com.github.ringoame196_s_mcPlugin

import org.bukkit.Location
import org.bukkit.block.Container
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object GachaponManager {
    private const val MAX_TARGET_BLOCK_DISTANCE = 10
    private val gachaponMap = mutableMapOf<Location, GachaponData>()
    val gachaponGUI = mutableMapOf<Inventory, GachaponGUI>()

    fun getTargetBlock(player: Player): Container? {
        val targetBlock = player.getTargetBlockExact(MAX_TARGET_BLOCK_DISTANCE) ?: return null
        return targetBlock.state as? Container
    }

    fun setGachapon(block: Container, consumableItem: ItemStack?) {
        gachaponMap[block.location] = GachaponData(
            block,
            consumableItem
        )
    }

    fun removeGachapon(block: Container) {
        gachaponMap.remove(block.location)
    }

    fun isGachapon(block: Container): Boolean {
        return block.location in gachaponMap
    }

    fun getGachaponData(block: Container): GachaponData? {
        return gachaponMap[block.location]
    }
}
