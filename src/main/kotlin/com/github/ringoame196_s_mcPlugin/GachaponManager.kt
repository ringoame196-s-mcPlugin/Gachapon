package com.github.ringoame196_s_mcPlugin

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Container
import org.bukkit.entity.Player

object GachaponManager {
    private const val MAX_TARGET_BLOCK_DISTANCE = 10
    private val gachaponMap = mutableMapOf<Location, GachaponData>()
    private const val GUI_SIZE = 9
    private const val GUI_TITLE = "ガチャガチャ画面"

    fun getTargetBlock(player: Player): Container? {
        val targetBlock = player.getTargetBlockExact(MAX_TARGET_BLOCK_DISTANCE) ?: return null
        return targetBlock.state as? Container
    }

    fun addGachapon(block: Container) {
        gachaponMap[block.location] = GachaponData(
            block,
            null,
            null
        )
    }

    fun removeGachapon(block: Container) {
        gachaponMap.remove(block.location)
    }

    fun isGachapon(block: Container): Boolean {
        return gachaponMap.keys.contains(block.location)
    }

    fun getGachaponData(block: Container): GachaponData? {
        return gachaponMap[block.location]
    }

    fun openGachaponInventory(player: Player) {
        val gui = Bukkit.createInventory(null, GUI_SIZE, GUI_TITLE)
        player.openInventory(gui)
    }
}
