package com.github.ringoame196_s_mcPlugin

import org.bukkit.Location
import org.bukkit.block.Container
import org.bukkit.entity.Player

object GachaponManager {
    private const val MAX_TARGET_BLOCK_DISTANCE = 10
    private val gachaponList = mutableListOf<Location>()

    fun getTargetBlock(player: Player): Container? {
        val targetBlock = player.getTargetBlockExact(MAX_TARGET_BLOCK_DISTANCE) ?: return null
        return targetBlock.state as? Container
    }

    fun addGachapon(block: Container) {
        gachaponList.add(block.location)
    }

    fun removeGachapon(block: Container) {
        gachaponList.remove(block.location)
    }

    fun isGachapon(block: Container): Boolean {
        return gachaponList.contains(block.location)
    }
}
