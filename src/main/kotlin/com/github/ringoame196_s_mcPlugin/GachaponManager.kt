package com.github.ringoame196_s_mcPlugin

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.Container
import org.bukkit.entity.Player

object GachaponManager {
    private const val MAX_TARGET_BLOCK_DISTANCE = 10
    private val gachaponList = mutableListOf<Location>()

    fun getTargetBlock(player: Player): Block? {
        val targetBlock = player.getTargetBlockExact(MAX_TARGET_BLOCK_DISTANCE) ?: return null
        return if (targetBlock.state is Container) targetBlock else null
    }

    fun addGachapon(block: Block) {
        gachaponList.add(block.location)
    }

    @Suppress("UnusedPrivateMember")
    fun removeGachapon(block: Block) {
        gachaponList.add(block.location)
    }

    @Suppress("UnusedPrivateMember")
    fun isGachapon(block: Block): Boolean {
        return gachaponList.contains(block.location)
    }
}
